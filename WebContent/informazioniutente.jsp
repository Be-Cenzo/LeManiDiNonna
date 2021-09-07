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
		Integer updErr = (Integer) request.getAttribute("errore-update-address");
		Integer addErr = (Integer) request.getAttribute("errore-add-address");
		System.out.println("indirizzi: " + user.getIndirizzi());
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
							    
							    <input type="hidden" name="action" value="edit">
							    
							    <button type="submit" class="modifica-btn">Applica</button>
							    <div class="modifica-btn" onclick="$('#saved-info').animate({height:'toggle'}); $('#edit-collapse').animate({height:'toggle'});">Annulla</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row justify-content-center">
			<div class="indirizzi">
				<div class="titolo">
					I Tuoi Indirizzi
				</div>
				<div class="saved-addr" id="saved-addr">
					<%for(Indirizzo ind : user.getIndirizzi()){ %>
						<div class="indirizzo-row">
							Via <%=ind.getVia() %>, <%=ind.getCivico() %>, <%=ind.getComune() %>, <%=ind.getCAP() %>, <%=ind.getProvincia() %>
							<a onclick="$('#saved-addr').animate({height:'toggle'}); $('#edit-addr-collapse').animate({height:'toggle'}); $('#id-value').val('<%=ind.getID()%>')">
								<span id="editIcon">
									<img src="./icon/edit.svg" alt="edit"/>
								</span>
							</a>
						</div>
					<%} %>
					<button class="modifica-btn" onclick="$('#saved-addr').animate({height:'toggle'}); $('#add-addr-collapse').animate({height:'toggle'});">Aggiungi Indirizzo</button>
				</div>
				<div id="add-addr-collapse">
					<div class="edit-info" id="edit-info">
						<form class="edit-info-form" id="add-info-form" action="<%=response.encodeURL("./UpdateInfo?action=add-addr") %>" method="post">
							<div class="row info-row">
								<%
									if(addErr != null && addErr == 1){ 
								%>
										<label class="errore">La Provincia non può essere vuota</label>
								<%
									}
								%><%
									if(addErr != null && addErr == 2){ 
								%>
										<label class="errore">Il Comune non può essere vuoto</label>
								<%
									}
								%><%
									if(addErr != null && addErr == 3){ 
								%>
										<label class="errore">La Via non può essere vuota</label>
								<%
									}
								%><%
									if(addErr != null && addErr == 4){ 
								%>
										<label class="errore">Il Civico non può essere vuoto</label>
								<%
									}
								%><%
									if(addErr != null && addErr == 5){ 
								%>
										<label class="errore">Il CAP non può essere vuoto</label>
								<%
									}
								%>
								<label id="provincia-label" for="provincia"><b>Provincia</b></label>
							    <input id="provincia" type="text" placeholder="Inserisci Provincia" name="provincia">
								
							    <label id="comune-label" for="comune"><b>Comune</b></label>
							    <input id="comune" type="text" placeholder="Inserisci Comune" name="comune">
							
							    <label id="via-label" for="via"><b>Via</b></label>
							    <input id="via" type="text" placeholder="Inserisci Via" name="via">
							
							    <label id="civico-label" for="civico"><b>Civico</b></label>
							    <input id="civico" type="number" name="civico" min="0">
							
							    <label id="cap-label" for="cap"><b>CAP</b></label>
							    <input id="cap" type="text" placeholder="Inserisci CAP" name="cap">
							    
							    <input type="hidden" name="action" value="edit">
							    
							    <button type="submit" class="modifica-btn">Aggiungi</button>
							    <div class="modifica-btn" onclick="$('#saved-addr').animate({height:'toggle'}); $('#add-addr-collapse').animate({height:'toggle'});">Annulla</div>
							</div>
						</form>
					</div>
				</div>
				<div id="edit-addr-collapse">
					<div class="edit-info" id="edit-info">
						<form class="edit-info-form" id="edit-info-form" action="<%=response.encodeURL("./UpdateInfo") %>" method="post">
							<div class="row info-row">
								<%
									if(updErr != null && updErr == 1){ 
								%>
										<label class="errore">La Provincia non può essere vuota</label>
								<%
									}
								%><%
									if(updErr != null && updErr == 2){ 
								%>
										<label class="errore">Il Comune non può essere vuoto</label>
								<%
									}
								%><%
									if(updErr != null && updErr == 3){ 
								%>
										<label class="errore">La Via non può essere vuota</label>
								<%
									}
								%><%
									if(updErr != null && updErr == 4){ 
								%>
										<label class="errore">Il Civico non può essere vuoto</label>
								<%
									}
								%><%
									if(updErr != null && updErr == 5){ 
								%>
										<label class="errore">Il CAP non può essere vuoto</label>
								<%
									}
								%>
								<label id="provincia-label" for="provincia"><b>Provincia</b></label>
							    <input id="provincia" type="text" placeholder="Inserisci Provincia" name="provincia">
								
							    <label id="comune-label" for="comune"><b>Comune</b></label>
							    <input id="comune" type="text" placeholder="Inserisci Comune" name="comune">
							
							    <label id="via-label" for="via"><b>Via</b></label>
							    <input id="via" type="text" placeholder="Inserisci Via" name="via">
							
							    <label id="civico-label" for="civico"><b>Civico</b></label>
							    <input id="civico" type="number" name="civico" min="0">
							
							    <label id="cap-label" for="cap"><b>CAP</b></label>
							    <input id="cap" type="text" placeholder="Inserisci CAP" name="cap">
							    
							    <input type="hidden" name="action" value="update-addr">
							    <input id="id-value" type="hidden" name="id" value="">
							    
							    <button type="submit" class="modifica-btn">Modifica</button>
							    <div class="modifica-btn" onclick="$('#saved-addr').animate({height:'toggle'}); $('#edit-addr-collapse').animate({height:'toggle'});">Annulla</div>
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
$(document).ready(function(){
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
});
</script>
</body>
</html>