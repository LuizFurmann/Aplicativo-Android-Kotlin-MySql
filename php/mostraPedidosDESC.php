<?php

$con=new mysqli("localhost","root","","sistema");
$st_check=$con->prepare('SELECT id_item,observacao, DATE_FORMAT(dia, "%d/%m/%Y") FROM dados ORDER BY dia DESC');

$st_check->execute();
$rs=$st_check->get_result();
$arr=array();
while($row=$rs->fetch_assoc()){
    array_push($arr, $row);
}
echo json_encode($arr);