<?php
header('Location: /');
$name = $_POST['name'];
$email =$_POST['email'];
$subject = $_POST['subject'];
$comment = $_POST['comment'];
$formcontent=
"
Заявка с сайта www.mix.msk.ru: \n
Email: $email \n
Имя: $name \n
Комментарий: $comment
";
$recipient = "info@mix.msk.ru";
$mailheader = "От: $name \r\n";
mail($recipient, $subject, $formcontent) or die("Ошибка!"); 
?>
