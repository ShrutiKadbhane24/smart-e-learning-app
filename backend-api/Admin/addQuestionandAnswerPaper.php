<?php
require_once("database.php");
mysqli_set_charset($con,"utf8");
$branch=$_POST['branch'];
$sem=$_POST['sem'];
$exam=$_POST['exam'];
$title=$_POST['title'];


$sql="insert into question_and_answer_paper(branch,semester,exam,title) values('$branch','$sem','$exam','$title')";
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