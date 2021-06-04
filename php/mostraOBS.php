<?php

$con=new mysqli("localhost","root","","sistema");
$st_check=$con->prepare('SELECT observacao FROM dados WHERE id_item (?)');
$st->bind_param("s",$_GET["id_item"]);

$st_check->execute();
$rs=$st_check->get_result();
$arr=array();
while($row=$rs->fetch_assoc()){
    array_push($arr, $row);
}
echo json_encode($arr);