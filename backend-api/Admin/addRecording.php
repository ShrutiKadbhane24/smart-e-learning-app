<?php
require_once("database.php");
$class_id=$_POST['class_id'];
$title=$_POST['title'];
$description=$_POST['description'];
$url=$_POST['url'];

$sql="insert into tbl_recording(class_id,title,description,url,date) values('$class_id','$title','$description','$url',CURDATE())";
$result=mysqli_query($con,$sql);
if($result>0)
{
$response['success']=1;
}
else
{
	$response['success']=0;
}
echo json_encode($response);

?>