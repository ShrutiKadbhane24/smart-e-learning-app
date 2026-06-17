<?php
require_once("database.php");

$class_id = $_POST['class_id'];
$sql="SELECT id, title,description,doc FROM notice_board where class_id='$class_id'";

$result=array();

$data=mysqli_query($con,$sql);

while($row=mysqli_fetch_array($data))
{
	array_push($result,array('id'=>$row[0],'title'=>$row[1],'description'=>$row[2],'doc'=>$row[3]));
}
echo json_encode(array('getNoticeBoard'=>$result));

mysqli_close($con);

?>
