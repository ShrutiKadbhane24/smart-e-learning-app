package com.mountreachsolution.e_learningapp.Admin.AddStudyMaterial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Admin.AddClass.AddClassActivity;
import com.mountreachsolution.e_learningapp.Comman.CommonMethods;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class AddStudyMaterialActivity extends AppCompatActivity {

    EditText et_study_material_title, et_study_material_description;

    TextView tv_select_pdf;
    Button btn_add_study_material;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int PICK_FILE_REQUEST = 2;
    private String selectedFilePath;

    private Uri filePath = null;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study_material);

        preferences = PreferenceManager.getDefaultSharedPreferences(AddStudyMaterialActivity.this);
        editor = preferences.edit();

        et_study_material_title = findViewById(R.id.et_study_material_title);
        et_study_material_description = findViewById(R.id.et_study_material_description);
        tv_select_pdf = findViewById(R.id.tv_study_material_select_pdf);
        btn_add_study_material = findViewById(R.id.btn_add_study_material);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.parse("package:" + getPackageName()));
//            finish();
//            startActivity(intent);
//            return;
//        }

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        tv_select_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btn_add_study_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_study_material_title.getText().toString().isEmpty()) {
                    et_study_material_title.setError("Enter Your Study Material Title");
                } else if (et_study_material_description.getText().toString().isEmpty()) {
                    et_study_material_description.setError("Enter Your Study Material Description");
                } else {
                    progressDialog = new ProgressDialog(AddStudyMaterialActivity.this);
                    progressDialog.setTitle("Adding Study Material");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    addStudyMaterial();
                }
            }
        });
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(AddStudyMaterialActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(AddStudyMaterialActivity.this,
                    new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(AddStudyMaterialActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select pdf"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.d("", "File Uri: " + filePath.toString());
            // Get the path
            try {
                selectedFilePath = CommonMethods.getPath(this, filePath);
                tv_select_pdf.setText("File attached");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }


    private void addStudyMaterial() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("class_id", preferences.getString("class_id", ""));
        params.put("title", et_study_material_title.getText().toString());
        params.put("description", et_study_material_description.getText().toString());


        client.post(Urls.urlAddStudyMaterial, params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String aa = response.getString("success");
                    if (aa.equals("1")) {
                        uploadMultipart();
                    } else {
                        Toast.makeText(AddStudyMaterialActivity.this, "Please Select all fields", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddStudyMaterialActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadMultipart() throws IOException {
        //getting name for the pdf


        Random random = new Random();
        String name = String.format("%04d", random.nextInt(1000000));

        if (filePath == null) {
            onBackPressed();
            return;
        }
        //getting the actual path of the pdf
        File path = new File(FileUtil.from(AddStudyMaterialActivity.this, filePath));

        if (path == null) {
            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, Urls.urlAddStudyMaterialDoc)
                        .addFileToUpload(String.valueOf(path), "pdf") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload
                onBackPressed();
            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static class FileUtil {
        private static final int EOF = -1;
        private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

        private FileUtil() {

        }

        public static String from(Context context, Uri uri) throws IOException {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            String fileName = getFileName(context, uri);
            String[] splitName = splitFileName(fileName);
            File tempFile = File.createTempFile(splitName[0], splitName[1]);
            tempFile = rename(tempFile, fileName);
            tempFile.deleteOnExit();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(tempFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream != null) {
                copy(inputStream, out);
                inputStream.close();
            }

            if (out != null) {
                out.close();
            }
            return tempFile.toString();
        }

        private static String[] splitFileName(String fileName) {
            String name = fileName;
            String extension = "";
            int i = fileName.lastIndexOf(".");
            if (i != -1) {
                name = fileName.substring(0, i);
                extension = fileName.substring(i);
            }

            return new String[]{name, extension};
        }

        private static String getFileName(Context context, Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf(File.separator);
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
            return result;
        }

        private static File rename(File file, String newName) {
            File newFile = new File(file.getParent(), newName);
            if (!newFile.equals(file)) {
                if (newFile.exists() && newFile.delete()) {
                    Log.d("FileUtil", "Delete old " + newName + " file");
                }
                if (file.renameTo(newFile)) {
                    Log.d("FileUtil", "Rename file to " + newName);
                }
            }
            return newFile;
        }

        private static long copy(InputStream input, OutputStream output) throws IOException {
            long count = 0;
            int n;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            while (EOF != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
            return count;
        }
    }
}
