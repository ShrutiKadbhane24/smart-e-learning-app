<?php
require_once("database.php");

$class_id = $_POST['class_id'];
$sql="SELECT date,title,description,url FROM tbl_recording where class_id = '$class_id' order by id desc";

$result=array();

$data=mysqli_query($con,$sql);

while($row=mysqli_fetch_array($data))
{
	array_push($result,array('recording_date'=>$row[0],'recording_title'=>$row[1],'recording_description'=>$row[2],'recording_url'=>$row[3]));
}
echo json_encode(array('getMyRecording'=>$result));


mysqli_close($con);


?>
