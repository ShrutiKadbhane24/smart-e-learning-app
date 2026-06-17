<?php
require_once("database.php");

$user_id = $_POST['user_id'];  //Nikdroid23@

// select columenname from tablename where columename='valuename';

$sql = "select id,image,name,mobile_no,email_id,age,address,username,password from register_student where id='$user_id'";

$result = mysqli_query($con,$sql);
$data = array();  
while($row = mysqli_fetch_array($result)) {
	array_push($data,array('id'=>$row[0],'profile_pic'=> $row[1],'name'=>$row[2],'mobile_no'=>$row[3],'email_id' => $row[4],'age' => $row[5],'address' => $row[6],'username'=>$row[7],'password'=>$row[8]));
}

	echo json_encode(array('getMyDetails'=> $data));
?>


