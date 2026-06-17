<?php
require_once("database.php");
$username = $_POST['username'];
$password = $_POST['password'];

$sql = "select id,image,name,mobile_no,email_id,age,address,username, password from register_student where username = '$username' and password = '$password'";

$result = mysqli_query($con,$sql);
if ($row = mysqli_fetch_array($result)) {
	$response['success'] = 1;
	$response['id'] = $row['id'];
	$response['image'] = $row['image'];
	$response['name'] = $row['name'];
	$response['mobile_no'] = $row['mobile_no'];
	$response['email_id'] = $row['email_id'];
	$response['age'] = $row['age'];
	$response['address'] = $row['address'];
	$response['username'] = $row['username'];
	$response['password'] = $row['password'];

}
else {
	$response[success] = 0;
}
echo json_encode($response);
?>