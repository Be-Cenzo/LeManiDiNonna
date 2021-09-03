<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="./style/login.css" rel="stylesheet">
 
 <script type="text/javascript">  
function validateform(){  
var name=$("#name").val();  
var password=$("#psw").val();
  console.log("pisello");
if (name==null || name==""){  
  alert("Name can't be blank");  
  return false;  
}else if(password.length<6){  
  alert("Password must be at least 6 characters long.");  
  return false;  
  }  
}  

function ValidateEmail(inputText)
{
var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
if(inputText.value.match(mailformat))
{
document.form1.text1.focus();
return true;
}
else
{
alert("You have entered an invalid email address!");
document.form1.text1.focus();
return false;
}
}
</script>  

</head>
<body>

<!-- The Modal -->
<div id="log" class="modal">
  

  <!-- Modal Content -->
  <form name="myform" class="modal-content animate" action="login" method="post">


    <div class="container">
      <label for="uname"><b>Username</b></label>
      <input type="text" placeholder="Enter Username" id="name" name="uname">

      <label for="psw"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" id="psw" name="psw">

      <button type="submit" onclick="validateform()">Login</button>
    </div>
    
    <div class="container" style="background-color:#f1f1f1">
      <button type="button" onclick="document.getElementById('log').style.display='none'" class="cancelbtn">Cancel</button>
    </div>
  </form>
</div> 

</body>
</html>