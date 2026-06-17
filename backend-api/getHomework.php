<?php
require_once("database.php");

$class_id = $_POST['class_id'];
$sql="SELECT date,title,description FROM tbl_homework where class_id = '$class_id' order by id desc";

$result=array();

$data=mysqli_query($con,$sql);

while($row=mysqli_fetch_array($data))
{
	array_push($result,array('howework_date'=>$row[0],'howework_title'=>$row[1],'howework_description'=>$row[2]));
}
echo json_encode(array('getMyHomework'=>$result));


mysqli_close($con);


?>
