<?php
require_once("database.php");
define('UPLOAD_PATH', 'upload/');

if(isset($_FILES['pic']['name']) && isset($_POST['id'])){
 
 //uploading file and storing it to database as well 
 try{
 move_uploaded_file($_FILES['pic']['tmp_name'], UPLOAD_PATH . $_FILES['pic']['name']);

 $filename=$_FILES['pic']['name'];
 
 $sql="update register_student set image='".$filename."' where id=".$_POST['id']."";
 $result=mysqli_query($con,$sql);
 
 if(mysqli_affected_rows($con) >0 )
 {
 $response['error'] = false;
 $response['message'] = 'File uploaded successfully';
 }else{
 throw new Exception("Could not upload file");
 }
 }catch(Exception $e){
 $response['error'] = true;
 $response['message'] = 'Could not upload file';
 }
 
 }else{
 $response['error'] = true;
 $response['message'] = "Required params not available";
 }

 echo json_encode($response);

?>