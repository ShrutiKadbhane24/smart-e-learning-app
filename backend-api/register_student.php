<?php
require_once("database.php");
$name=$_POST['name'];
$mobile_number=$_POST['mobile_no'];
$email_id=$_POST['email_id'];
$address=$_POST['address'];
$age=$_POST['age'];
$username=$_POST['username'];
$password=$_POST['password'];


$sql="insert into register_student(name,mobile_no,email_id,address,age,username,password) values('$name','$mobile_number','$email_id','$address','$age','$username','$password')";
$result=mysqli_query($con,$sql);
if($result>0)
{
$response['success']=1;
$response['lastinsertedid']= mysqli_insert_id($con);
}
else
{
	$response['success']=0;
}
echo json_encode($response);

?>