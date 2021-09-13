<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*, javax.sql.DataSource"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<link href="./style/informazioni.css" rel="stylesheet">
<%@ include file="headlink.jsp" %>

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
		Integer addNmbrErr = (Integer) request.getAttribute("errore-add-number");
		Integer updNmbrErr = (Integer) request.getAttribute("errore-update-number");
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
					<div class="edit-info">
						<form class="form" id="edit-info-form" action="<%=response.encodeURL("./UpdateInfo") %>" method="post">
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
					<div class="edit-info">
						<form class="form" id="add-addr-form" action="<%=response.encodeURL("./UpdateInfo") %>" method="post">
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
							    
							    <input type="hidden" name="action" value="add-addr">
							    
							    <button type="submit" class="modifica-btn">Aggiungi</button>
							    <div class="modifica-btn" onclick="$('#saved-addr').animate({height:'toggle'}); $('#add-addr-collapse').animate({height:'toggle'});">Annulla</div>
							</div>
						</form>
					</div>
				</div>
				<div id="edit-addr-collapse">
					<div class="edit-info">
						<form class="form" id="edit-addr-form" action="<%=response.encodeURL("./UpdateInfo") %>" method="post">
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
								<label id="edit-provincia-label" for="edit-provincia"><b>Provincia</b></label>
							    <input id="edit-provincia" type="text" placeholder="Inserisci Provincia" name="provincia">
								
							    <label id="edit-comune-label" for="edit-comune"><b>Comune</b></label>
							    <input id="edit-comune" type="text" placeholder="Inserisci Comune" name="comune">
							
							    <label id="edit-via-label" for="edit-via"><b>Via</b></label>
							    <input id="edit-via" type="text" placeholder="Inserisci Via" name="via">
							
							    <label id="edit-civico-label" for="edit-civico"><b>Civico</b></label>
							    <input id="edit-civico" type="number" name="civico" min="0">
							
							    <label id="edit-cap-label" for="edit-cap"><b>CAP</b></label>
							    <input id="edit-cap" type="text" placeholder="Inserisci CAP" name="cap">
							    
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
		
		<div class="row justify-content-center">
			<div class="numeri">
				<div class="titolo">
					I Tuoi Numeri di Telefono
				</div>
				<div class="saved-nmbr" id="saved-nmbr">
					<ol>
					<%for(String numero : user.getNumeriTel()){ %>
							<li>
								<div class="numero-row">
									<%=numero %>
									<a onclick="$('#saved-nmbr').animate({height:'toggle'}); $('#edit-nmbr-collapse').animate({height:'toggle'}); $('#numero-value').val('<%=numero%>')">
										<span id="editIcon">
											<img src="./icon/edit.svg" alt="edit"/>
										</span>
									</a>
								</div>
							</li>
					<%} %>
					</ol>
					<button class="modifica-btn" onclick="$('#saved-nmbr').animate({height:'toggle'}); $('#add-nmbr-collapse').animate({height:'toggle'});">Aggiungi Numero</button>
				</div>
				<div id="add-nmbr-collapse">
					<div class="edit-info">
						<form class="form" id="add-nmbr-form" action="<%=response.encodeURL("./UpdateInfo") %>" method="post">
							<div class="row info-row">
								<%
									if(addNmbrErr != null && addNmbrErr == 1){ 
								%>
										<label class="errore">Inserisci un numero di telefono valido</label>
								<%
									}
								%>
								<%
									if(addNmbrErr != null && addNmbrErr == 2){ 
								%>
										<label class="errore">Hai già inserito questo numero</label>
								<%
									}
								%>
								
								<label id="phone-label" for="phone"><b>Numero di Telefono</b></label>
				    			<input id="phone" type="text" placeholder="Inserisci Numero Di Telefono" name="phone">
							    
							    <input type="hidden" name="action" value="add-nmbr">
							    
							    <button type="submit" class="modifica-btn">Aggiungi</button>
							    <div class="modifica-btn" onclick="$('#saved-nmbr').animate({height:'toggle'}); $('#add-nmbr-collapse').animate({height:'toggle'});">Annulla</div>
							</div>
						</form>
					</div>
				</div>
				<div id="edit-nmbr-collapse">
					<div class="edit-info">
						<form class="form" id="edit-nmbr-form" action="<%=response.encodeURL("./UpdateInfo") %>" method="post">
							<div class="row info-row">
								<%
									if(updNmbrErr != null && updNmbrErr == 1){ 
								%>
										<label class="errore">Inserisci un numero di telefono valido</label>
								<%
									}
								%>
								<%
									if(updNmbrErr != null && updNmbrErr == 2){ 
								%>
										<label class="errore">Hai già inserito questo numero</label>
								<%
									}
								%>
							
								<label id="edit-phone-label" for="edit-phone"><b>Numero di Telefono</b></label>
				    			<input id="edit-phone" type="text" placeholder="Inserisci Numero Di Telefono" name="phone">
								
							    <input type="hidden" name="action" value="update-nmbr">
							    <input id="numero-value" type="hidden" name="numero" value="">
							    
							    <button type="submit" class="modifica-btn">Modifica</button>
							    <div class="modifica-btn" onclick="$('#saved-nmbr').animate({height:'toggle'}); $('#edit-nmbr-collapse').animate({height:'toggle'});">Annulla</div>
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
	const addAddress = document.getElementById('add-addr-form');
	addAddress.addEventListener('submit', function(event){
		var provincia = $("#provincia").val();
		var comune = $("#comune").val();
		var via = $("#via").val();
		var civico = $("#civico").val();
		var cap = $("#cap").val();
		if (provincia==null || provincia === ""){
			$("#provincia").addClass("error");
		  	$("#provincia-label").html("<b>Provincia</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#provincia").removeClass("error");
		  	$("#provincia-label").html("<b>Provincia</b>");
		}
		if (comune==null || comune === ""){
			$("#comune").addClass("error");
		  	$("#comune-label").html("<b>Comune</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#comune").removeClass("error");
		  	$("#comune-label").html("<b>Comune</b>");
		}
		if (via==null || via === ""){
			$("#via").addClass("error");
		  	$("#via-label").html("<b>Via</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#via").removeClass("error");
		  	$("#via-label").html("<b>Via</b>");
		}
		if (civico==null || civico === ""){
			$("#civico").addClass("error");
		  	$("#civico-label").html("<b>Civico</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#civico").removeClass("error");
		  	$("#civico-label").html("<b>Via</b>");
		}
		if (cap==null || cap === ""){
			$("#cap").addClass("error");
		  	$("#cap-label").html("<b>CAP</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#cap").removeClass("error");
		  	$("#cap-label").html("<b>CAP</b>");
		}
	});
	const editAddress = document.getElementById('edit-addr-form');
	editAddress.addEventListener('submit', function(event){
		var provincia = $("#edit-provincia").val();
		var comune = $("#edit-comune").val();
		var via = $("#edit-via").val();
		var civico = $("#edit-civico").val();
		var cap = $("#edit-cap").val();
		if (provincia==null || provincia === ""){
			$("#edit-provincia").addClass("error");
		  	$("#edit-provincia-label").html("<b>Provincia</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#edit-provincia").removeClass("error");
		  	$("#edit-provincia-label").html("<b>Provincia</b>");
		}
		if (comune==null || comune === ""){
			$("#edit-comune").addClass("error");
		  	$("#edit-comune-label").html("<b>Comune</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#edit-comune").removeClass("error");
		  	$("#edit-comune-label").html("<b>Comune</b>");
		}
		if (via==null || via === ""){
			$("#edit-via").addClass("error");
		  	$("#edit-via-label").html("<b>Via</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#edit-via").removeClass("error");
		  	$("#edit-via-label").html("<b>Via</b>");
		}
		if (civico==null || civico === ""){
			$("#edit-civico").addClass("error");
		  	$("#edit-civico-label").html("<b>Civico</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#edit-civico").removeClass("error");
		  	$("#edit-civico-label").html("<b>Via</b>");
		}
		if (cap==null || cap === ""){
			$("#edit-cap").addClass("error");
		  	$("#edit-cap-label").html("<b>CAP</b> - non può essere vuoto");
		  	event.preventDefault();
		}
		else{
			$("#edit-cap").removeClass("error");
		  	$("#edit-cap-label").html("<b>CAP</b>");
		}
	});
});
</script>
</body>
</html>