<?php

$con=new mysqli("localhost","root","","sistema");
$st=$con->prepare('SELECT id_item,observacao, DATE_FORMAT(dia, "%d/%m/%Y") FROM dados WHERE id_item=?');
$st->bind_param("s", $_GET["id_item"]);

$st->execute();
$rs=$st->get_result();
$arr=array();
while($row=$rs->fetch_assoc()){
    array_push($arr, $row);
}
echo json_encode($arr);