<?php
    $con=new mysqli("localhost","root","","sistema");
    $st=$con->prepare("UPDATE dados SET observacao = (?) WHERE id_item = (?)");
    $st->bind_param("ss", $_GET["observacao"],$_GET["id_item"]);
    $st->execute();
?>