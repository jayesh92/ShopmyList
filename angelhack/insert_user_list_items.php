<?php

$connect = mysql_connect("localhost","root","Itsanew1");
mysql_select_db("angelhack",$connect);

$store = isset($_POST['store']) ? $_POST['store'] : '';
$name = isset($_POST['name']) ? $_POST['name'] : '';
$current_user = isset($_POST['current_user']) ? $_POST['current_user'] : '';

/*$store="dominos";
$name="pizza";
$current_user="krans4u";*/

$query = "select list_no from user_list where username='$current_user'";
$run=mysql_query($query);
$last_entry_id=0;
while($row = mysql_fetch_array($run)) {
	echo $row[0];
	$query = "select listno from listno_details where store='$store' and name='$name' and listno=$row[0]";
	echo $query;
	$run1=mysql_query($query);
	$flag=1;
	while($row1 = mysql_fetch_array($run1)) 
	{
		echo "Enter <br/>";
		echo $row1[0];
		$last_entry_id=$row1[0];
		$flag=0;
		break;
	}
	if($flag==0)
		break;
}
$last_insert=$last_entry_id;

$order=isset($_POST['order']) ? $_POST['order'] : '';
/*$order="behenchod,madrchood";*/

$my_array = explode(",",$order);
foreach($my_array as $value)
{
	$query = "insert into listno_item values('$last_insert','$value')";
	$run=mysql_query($query);
}
mysql_close();
?>
