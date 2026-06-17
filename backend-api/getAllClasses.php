<?php
require_once("database.php");

$sql="select id,class_name,subject_name from class";

$result=array();

$data=mysqli_query($con,$sql);

while($row=mysqli_fetch_array($data))
{
  array_push($result,array('id'=>$row[0],'class_name'=>$row[1],'subject_name' => $row[2]));
}
echo json_encode(array('getAllClass'=>$result));

mysqli_close($con);


?>
