<?php
require_once("database.php");
mysqli_set_charset($con,"utf8");
$class_id=$_POST['class_id'];
$title=$_POST['title'];
$description=$_POST['description'];


$sql="insert into study_material(class_id,title,description,date) values('$class_id','$title','$description',CURDATE())";
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