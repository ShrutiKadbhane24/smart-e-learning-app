<?php
require_once("database.php");
$class_id=$_POST['class_id'];
$homework_title=$_POST['homework_title'];
$homework_description=$_POST['homework_description'];

$sql="insert into tbl_homework(class_id,title,description,date) values('$class_id','$homework_title','$homework_description',CURDATE())";
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