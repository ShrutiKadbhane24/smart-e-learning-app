package com.mountreachsolution.e_learningapp.Admin.AddQuestionandAnswerModelPaperActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Admin.AddNoticeBoardActivity.AddNoticeBoardActivity;
import com.mountreachsolution.e_learningapp.Admin.AddQuestionandAnswerModelPaperActivity.AddQuestionandAnswerModelPaperActivity;
import com.mountreachsolution.e_learningapp.Admin.AddStudyMaterial.AddStudyMaterialActivity;
import com.mountreachsolution.e_learningapp.Comman.CommonMethods;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.QuestionandAnswerModelPaper.QuestionandAnswerModelPaperActivity;
import com.mountreachsolution.e_learningapp.QuestionandAnswerModelPaper.SearchQuestionandAnswerPaperActivity;
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

public class AddQuestionandAnswerModelPaperActivity extends AppCompatActivity {

    Spinner spinner_add_branch, spinner_add_semester, spinner_add_exam;
    TextInputEditText tie_question_and_answer_paper_title;
    TextView tv_select_pdf;
    AppCompatButton btn_add_question_and_answer_paper;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    private int PICK_FILE_REQUEST = 2;
    private String selectedFilePath;

    private Uri filePath = null;

    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questionand_answer_model_paper);

        preferences = PreferenceManager.getDefaultSharedPreferences(AddQuestionandAnswerModelPaperActivity.this);
        editor = preferences.edit();

        setTitle("" + preferences.getString("class_name", "") + "Add Q and A Paper");

        spinner_add_branch = findViewById(R.id.spinner_add_branch);
        spinner_add_semester = findViewById(R.id.spinner_add_semester);
        spinner_add_exam = findViewById(R.id.spinner_add_exam);
        tie_question_and_answer_paper_title = findViewById(R.id.tie_add_question_and_answer_paper_title);
        tv_select_pdf = findViewById(R.id.tv_add_question_and_answer_paper);
        btn_add_question_and_answer_paper = findViewById(R.id.btn_add_question_and_answer_paper_search);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.parse("package:" + getPackageName()));
//            finish();
//            startActivity(intent);
//            return;
//        }

        tv_select_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        btn_add_question_and_answer_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_add_branch.getSelectedItem().toString().equals("Select Your Branch")) {
                    ((TextView) spinner_add_branch.getSelectedView()).setError("Please Select Your Branch");
                } else if (spinner_add_semester.getSelectedItem().toString().equals("Select Your Sem")) {
                    ((TextView) spinner_add_semester.getSelectedView()).setError("Please Select Your Sem");
                } else if (spinner_add_exam.getSelectedItem().toString().equals("Select Your Exam")) {
                    ((TextView) spinner_add_exam.getSelectedView()).setError("Please Select Your Exam");
                }
                if (tie_question_and_answer_paper_title.getText().toString().isEmpty()) {
                    tie_question_and_answer_paper_title.setError("Enter Your Question and Answer Paper Title");
                } else {
                    progressDialog = new ProgressDialog(AddQuestionandAnswerModelPaperActivity.this);
                    progressDialog.setTitle("Adding Notice Board");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    addQuestionandAnswerPaper();
                }

            }
        });
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(AddQuestionandAnswerModelPaperActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(AddQuestionandAnswerModelPaperActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(AddQuestionandAnswerModelPaperActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
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

    private void addQuestionandAnswerPaper() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("class_id", preferences.getString("class_id", ""));
        params.put("branch", spinner_add_branch.getSelectedItem().toString());
        params.put("sem", spinner_add_semester.getSelectedItem().toString());
        params.put("exam", spinner_add_exam.getSelectedItem().toString());
        params.put("title", tie_question_and_answer_paper_title.getText().toString());
        
        client.post(Urls.urlAddQuestionandAnswerPaper, params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String aa = response.getString("success");
                    if (aa.equals("1")) {
                        uploadMultipart();
                    } else {
                        Toast.makeText(AddQuestionandAnswerModelPaperActivity.this, "Please Select all fields", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddQuestionandAnswerModelPaperActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
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
        File path = new File(FileUtil.from(AddQuestionandAnswerModelPaperActivity.this, filePath));

        if (path == null) {
            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, Urls.urlAddQuestionandAnswerPaperDoc)
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