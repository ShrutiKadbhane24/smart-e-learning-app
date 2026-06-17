<?php
require_once("database.php");
$class_name=$_POST['class_name'];
$subject_name=$_POST['subject_name'];

$sql="insert into class(class_name,subject_name) values('$class_name','$subject_name')";
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