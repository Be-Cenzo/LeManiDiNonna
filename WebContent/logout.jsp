<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="./style/login.css" rel="stylesheet">

</head>
<body>

<!-- The Modal -->
<div id="login" class="modal">

  <!-- Modal Content -->
  <form name="myform" class="modal-content animate" action="logout" method="get">

    <div class="container">
      <button type="button">Area Utente</button>
      <button type="button">Logout</button>
    </div>
    
    <div class="container" style="background-color:#f1f1f1">
      <button type="button" onclick="document.getElementById('login').style.display='none'" class="cancelbtn">Cancel</button>
    </div>
    
  </form>
</div> 

</body>
</html>