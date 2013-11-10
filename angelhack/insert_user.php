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
$username = isset($_POST['username']) ? $_POST['username'] : '';
$email = isset($_POST['email']) ? $_POST['email'] : '';
$mobile = isset($_POST['mobile']) ? $_POST['mobile'] : '';
$query = mysqli_query($connect, "insert into user values('$username','$email' ,'$mobile') ");

mysqli_close($connect);
?>
