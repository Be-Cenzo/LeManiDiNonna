<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/informazioni.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Le Tue Informazioni</title>
</head>
<body>
<div class="wrapper">
	<% 
		String role1 = (String)session.getAttribute("role");
		if(role1 == null || role1.equals("guest")){
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
			return;
		}
		
		Account user = (Account) request.getSession().getAttribute("user");
		Integer errore = (Integer) request.getAttribute("errore-modifica");
	%> 
	
	<%@ include file="header.jsp" %>
	<div class="contenuto">
		<div class="row justify-content-center">
			<div class="informazioni">
				<div class="titolo">
					Le Tue Informazioni
				</div>
				<div class="saved-info" id="saved-info">
					<h6><b>Nome:</b> <%=user.getNome() %></h6>
					<h6><b>Cognome:</b> <%=user.getCognome() %></h6>
					<h6><b>Data di Nascita:</b> <%=user.getDataNascita() %></h6>
					<h6><b>Email:</b> <%=user.getEmail() %></h6>
					<h6><b>IG:</b> <%=user.getNomeIG() %></h6>
					<button class="modifica-btn" onclick="$('#saved-info').animate({height:'toggle'}); $('#edit-collapse').animate({height:'toggle'});">Modifica</button>
				</div>
				<div id="edit-collapse">
					<div class="edit-info" id="edit-info">
						<form class="edit-info-form" id="edit-info-form" action="<%=response.encodeURL("./UpdateInfo") %>" method="post">
							<div class="row info-row">
								<%
									if(errore != null && errore == 1){ 
								%>
										<label class="errore">Il Nome non può essere vuoto</label>
								<%
									}
									else
										System.out.println("errore: " + errore);
								%><%
									if(errore != null && errore == 2){ 
								%>
										<label class="errore">Il Cognome non può essere vuoto</label>
								<%
									}
								%><%
									if(errore != null && errore == 3){ 
								%>
										<label class="errore">La Data di Nascita non può essere vuota</label>
								<%
									}
								%>
								<label id="nome-label" for="name"><b>Nome</b></label>
							    <input id="nome" type="text" placeholder="Inserisci Nome" name="name" value="<%=user.getNome()%>">
							
							    <label id="surname-label" for="surname"><b>Cognome</b></label>
							    <input id="surname" type="text" placeholder="Inserisci Cognome" name="surname" value="<%=user.getCognome()%>">
							
							    <label id="birth-label" for="birth"><b>Data di nascita</b></label>
							    <input id="birth" type="date" name="birth" value="<%=user.getDataNascita()%>">
							    
							    <label id="ig-label" for="ig"><b>Nome Instagram</b></label>
							    <input id="ig" type="text" placeholder="Inserisci Nome Instagram" name="ig" value="<%=user.getNomeIG()%>">
							    
							    <button type="submit" class="modifica-btn">Applica</button>
							    <div class="modifica-btn" onclick="$('#saved-info').animate({height:'toggle'}); $('#edit-collapse').animate({height:'toggle'});">Annulla</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	
	<%@ include file="footer.jsp" %>
</div>

<script>
/*$(document).ready(function(){
	const edit = document.getElementById('edit-info-form');
	edit.addEventListener('submit', function(event){
		var name = $("#nome").val();
		var surname = $("#surname").val();
		var birth = $("#birth").val();
		if(name == null || name === ""){
			$("#nome").addClass("error");
		  	$("#nome-label").html("<b>Nome</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#nome").removeClass("error");
		  	$("#nome-label").html("<b>Nome</b>");
		}
		if(surname == null || surname === ""){
			$("#surname").addClass("error");
		  	$("#surname-label").html("<b>Cognome</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#surname").removeClass("error");
		  	$("#surname-label").html("<b>Cognome</b>");
		}
		if(birth == null || birth === ""){
			$("#birth").addClass("error");
		  	$("#birth-label").html("<b>Data di Nascita</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#birth").removeClass("error");
		  	$("#birth-label").html("<b>Data di Nascita</b>");
		}
	});
});*/
</script>
</body>
</html>