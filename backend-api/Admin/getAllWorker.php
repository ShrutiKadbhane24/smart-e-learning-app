<?php
require_once("database.php");

  
$sql = "select * from register_worker order by id desc";

$result = mysqli_query($con,$sql);
$data = array();  
while($row = mysqli_fetch_array($result)) {
	array_push($data,array('id'=>$row[0],'profile_pic'=>$row[1],'name'=> $row[2],'mobile_no'=>$row[3],'username' => $row[4],'password' => $row[5]));
}

	echo json_encode(array('getAllWorker'=> $data));
?>


