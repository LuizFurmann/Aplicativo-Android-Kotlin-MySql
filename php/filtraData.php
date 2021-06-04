<?php

$con=new mysqli("localhost","root","","sistema");
$st=$con->prepare('SELECT id_item,observacao, DATE_FORMAT(dia, "%d/%m/%Y") FROM dados WHERE DATE_FORMAT(dia, "%d/%m/%Y")=?');
$st->bind_param("s", $_GET["dia"]);

$st->execute();
$rs=$st->get_result();
$arr=array();
while($row=$rs->fetch_assoc()){
    array_push($arr, $row);
}
echo json_encode($arr);