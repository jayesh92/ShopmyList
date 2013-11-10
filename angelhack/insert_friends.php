<?php

$connect = mysqli_connect("localhost","root","Itsanew1","angelhack");

if(mysqli_connect_errno($connect))
{
	    echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
else
{
	    echo "success";
}
$friend1 = isset($_POST['friend1']) ? $_POST['friend1'] : '';
$friend2 = isset($_POST['friend2']) ? $_POST['friend2'] : '';
$query = mysqli_query($connect, "insert into friendship values('$friend1','$friend2') ");

mysqli_close($connect);
?>
