<?php
require_once("database.php");
$complaint_id=$_POST['complaint_id'];
$progress=$_POST['progress'];
$worker_username=$_POST['worker_username'];

$sql="update complainttbl set status='$progress',assign_worker='$worker_username' where id='$complaint_id'";
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