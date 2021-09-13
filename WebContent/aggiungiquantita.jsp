<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.sql.DataSource, java.util.ArrayList, model.*"%>
    <%
	  
  	ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) request.getAttribute("prodotti");
	
	if(prodotti == null) {
		response.sendRedirect(response.encodeRedirectURL("./ProdottiControl?from=add"));
		return;
	}
    %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
<%@ include file="headlink.jsp" %>
<link href="./style/aggiungiquantita.css" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta charset="UTF-8">
<title>Aggiungi Quantità</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="header.jsp" %>
	<% 
		String role1 = (String)session.getAttribute("role");
		if(role1 == null || role1.equals("guest")){
			response.sendRedirect(response.encodeURL("./accessdenied.jsp"));
		}
	%> 
	<div class="contenuto">
		<div class="add-container">
			<div class="seleziona-prodotto" id="seleziona">
				<div class="titolo">Seleziona un Prodotto:</div>
				<%for(Prodotto prod : prodotti){
					%>
						
					<div class="row product-row" onclick="select(<%=prod.getCodice()%>)">
						<div class="product-img">
							<img src="./getPicture?id=<%=prod.getCodice()%>" alt="esempio">
						</div>
						<div class="product-container">
							<div class="product-description">
								<%= prod.getDescrizione() %>
							</div>
						</div>
					</div>
					<% } %>
			</div>
			<div class="update-prodotto" id="update">
				<form id="add-form" class="add-form" action="<%= response.encodeURL("UploadProdotto")%>" enctype="multipart/form-data" method="post">
					<div class="titolo">Aggiungi la Quantità:</div>
					<input type="hidden" id="prod" name="prod">
					<div class="radio-container">   
						<label for="taglia"><b>Taglia: </b></label>
						<div class="radio">
							<div class="radio-element">
								<input class="radio-button" type="radio" id="s" name="taglia" value="S" checked>
								<label for="s">S</label>
							</div>
							<div class="radio-element">
								<input class="radio-button" type="radio" id="m" name="taglia" value="M">
								<label for="m">M</label>
							</div>
							<div class="radio-element">
								<input class="radio-button" type="radio" id="l" name="taglia" value="L">
								<label for="l">L</label>
							</div>
						</div>
					</div>
					<label id="quant-label" for="quant"><b>Quantità</b></label>
					<input id="quant" type="number" name="quant" min="1" value="1">
							    
					<div class="deposito"  id="depositi">
						<div class="titolo">Seleziona un Deposito</div>
						
					</div>
					<input class="add-btn" type="submit" value="Upload">
					<div class="add-btn" onclick="$('#update').animate({'height': 'toggle'}); $('#seleziona').animate({'height': 'toggle'});">Indietro</div>
				</form>
			</div>	
		</div>
	</div>
	
	
	<%@ include file="footer.jsp" %>
</div>

<script>
$(document).ready(function(){
	$.ajax({
		url: "GetDepositi",
		method: "GET"})
	.done(function (json){
		var checked = "checked";
		for(var i = 0; i<json.length; i++){
			if(i > 0)
				checked = "";
			$("#depositi").append("<div class='radio-element'><input type='radio' name='deposito' id='"+ json[i].ID +"' value='"+ json[i].ID +"'" + checked + "><label for='" + json[i].ID +"'>" + json[i].luogo + "</label></div>");
		};
	});
	const add = document.getElementById('add-form');
	add.addEventListener('submit', function(event){
		var quantità = $("#quant").val();
		if(quantità<1){
			$("#quant").addClass("error");
		  	$("#quant-label").html("<b>Quantità</b> - non può essere vuota");
		  	event.preventDefault();
		}
		else{
			$("#quant").removeClass("error");
		  	$("#quant-label").html("<b>Quantità</b>");
		}
	});
});
});

function select(id){
	$('#seleziona').animate({'height': 'toggle'});
	$('#update').animate({'height': 'toggle'});
	$('#prod').val(id);
}
</script>
</body>
</html>