<?php
$response=array();
$upload_path = 'doc/';

if($_SERVER['REQUEST_METHOD']=='POST'){
	
    if(isset($_POST['name']) and isset($_FILES['pdf']['name'])){

        $filename = $_POST['name'];
        $fileinfo = pathinfo($_FILES['pdf']['name']);
        $extension = $fileinfo['extension'];
        $file_path = $upload_path . $filename.'.'. $extension;
        $filename1 = $filename.'.'. $extension;
        // $filename1=$_FILES['pdf']['name'];
        move_uploaded_file($_FILES['pdf']['tmp_name'],$file_path);
    }
  
}
require_once("database.php");
$sql1="SELECT id FROM question_and_answer_paper order by id desc limit 1";
$data1=mysqli_query($con,$sql1);
mysqli_set_charset($con,"utf8");
while($row1=mysqli_fetch_array($data1))
{
	$id=$row1[0];
}
 
 $sql="update question_and_answer_paper set doc='".$filename1."' where id=".$id."";
 $result=mysqli_query($con,$sql);

 if(mysqli_affected_rows($con) >0 )
 {
 $response['error'] = false;
 $response['message'] = 'File uploaded successfully';
 }else{
 $response['error'] = true;
 $response['message'] = "Required params not available";
 }

 echo json_encode($response);

?>