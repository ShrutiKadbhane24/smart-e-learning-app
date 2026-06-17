<?php
require_once("database.php");
$username=$_POST['username'];
$password=$_POST['password'];
$sql="select id,userrole,username,password from login_admin where username='$username' and password='$password'";
$result=mysqli_query($con,$sql);
if($row = mysqli_fetch_array($result))
{
	$response["success"]=1;
	$response["id"] = $row['id'];
	$response["userrole"] = $row['userrole'];
}
else
{
	$response["success"]=0;
}
echo json_encode($response);

?>