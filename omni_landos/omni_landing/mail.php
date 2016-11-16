<?php
if($_POST)
{
    $to = "welcome@omni.com.ru"; //куда отправлять письмо 
    $name = $_POST['name'];
    $company =$_POST['company'];
    $phone = $_POST['phone'];
    $comment = $_POST['comment'];

    $subject = "Заявка с сайта"; //тема
    $formcontent=
    "
    Заявка с сайта. \n
    Компания: $company \n
    Имя: $name \n
    Телефон: $phone \n
    Комментарий: $comment
    ";
    $headers = "Content-type: text/html; charset=UTF-8 \r\n";
    $result = mail($to, $subject, $formcontent, $headers);

    if ($result){
    	echo "Спасибо! Мы перезвоним вам в течение дня.";
    }else{
    	echo "Произошла ошибка, пожалуйста, повторите попытку или позвоните нам по телефону, указанному ниже.";
    }
}
?>