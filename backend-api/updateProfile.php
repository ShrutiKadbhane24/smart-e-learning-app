<?php
require_once("database.php");
$user_id=$_POST['user_id'];
$name=$_POST['name'];
$address=$_POST['address'];
$mobile_no=$_POST['mobile_no'];
$email=$_POST['email_id'];
$username=$_POST['username'];

$sql="update register_student set name='$name',address='$address',mobile_no='$mobile_no',email_id='$email',username='$username' where id='$user_id'";
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