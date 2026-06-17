<?php
require_once("database.php");

$sql="SELECT id,question,option1,option2,option3,option4,correct_answer FROM tbl_quiz";

$result=array();

$data=mysqli_query($con,$sql);

while($row=mysqli_fetch_array($data))
{
	array_push($result,array('id'=>$row[0],'question'=>$row[1],'option1'=>$row[2],'option2'=>$row[3],'option3'=>$row[4],'option4'=>$row[5],'correct_answer'=>$row[6]));
}
echo json_encode(array('getQuiz'=>$result));

mysqli_close($con);

?>
