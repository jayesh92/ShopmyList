<?php
$host="localhost"; //replace with database hostname 
$username="root"; //replace with database username 
$password="Itsanew1"; //replace with database password 
$db_name="angelhack"; //replace with database name

$con=mysql_connect("$host", "$username", "$password")or die("cannot connect"); 
mysql_select_db("$db_name")or die("cannot select DB");

$friend1 = isset($_POST['friend1']) ? $_POST['friend1'] : ''; 

$sql = "select friend2 from friendship where friend1='$friend1'"; 
$result = mysql_query($sql);
$json = array();

if(mysql_num_rows($result)){
	while($row=mysql_fetch_assoc($result)){
		$json['friend'][]=$row;
	}
}
mysql_close($con);
echo json_encode($json); 
?> 
