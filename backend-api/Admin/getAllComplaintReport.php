<?php
require_once("database.php");

$sql = "select * from complainttbl order by id desc";

$result = mysqli_query($con,$sql);

$data = array();

while ($row = mysqli_fetch_array($result)) {
	array_push($data, array('id'=>$row[0],'user_id'=>$row[1],'collection_type'=>$row[2],'waste_type'=>$row[3],'collection_title'=>$row[4],'collection_description'=>$row[5],'complaint_photo'=>$row[6],'latitude'=>$row[7],'longitude'=>$row[8],'address'=>$row[9],'status'=>$row[10],'date_time'=>$row[11]));
}

echo json_encode(array('getMyComplaintReport'=>$data));
?>
