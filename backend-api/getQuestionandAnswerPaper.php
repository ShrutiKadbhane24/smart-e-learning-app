<?php
require_once("database.php");

$branch = $_POST['branch'];
$semester = $_POST['semester'];
$exam = $_POST['exam'];


$sql="SELECT id, title,doc FROM question_and_answer_paper where branch='$branch' AND  semester='$semester' AND exam='$exam'";

$result=array();

$data=mysqli_query($con,$sql);

while($row=mysqli_fetch_array($data))
{
	array_push($result,array('id'=>$row[0],'title'=>$row[1],'doc'=>$row[2]));
}
echo json_encode(array('getQuestionandAnswerPaper'=>$result));

mysqli_close($con);

?>
