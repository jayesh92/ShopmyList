<?php
$connect = mysql_connect("localhost","root","Itsanew1");
mysql_select_db("angelhack",$connect);

$receiver = isset($_POST['receiver']) ? $_POST['receiver'] : '';
$sender = isset($_POST['sender']) ? $_POST['sender'] : '';
$store = isset($_POST['store']) ? $_POST['store'] : '';
$name = isset($_POST['name']) ? $_POST['name'] : '';
$current_user = isset($_POST['current_user']) ? $_POST['current_user'] : '';

$query = "insert into listno_details values('','$receiver','$sender', '$store', '$name')";
$run=mysql_query($query);
$query = "select * from listno_details order by listno desc limit 1"; 
$run=mysql_query($query);
$last_insert=0;
while($row = mysql_fetch_row($run)) {
	    $last_insert=$row[0];
}
$query = "insert into user_list values('','$current_user','$last_insert')";
$run=mysql_query($query);
mysql_close();
?>
