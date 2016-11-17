<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./style.css">
<title>Cars</title>
</head>
<body>
	<div id="projet">
		<div id="right">
			<h2>Panier</h2>
			<table id="basket">
			</table>
			<button id="removeAll" type="button">Vider</button>
			<button id="purchaseBasket" type="button">Acheter le panier</button>
		</div>

		<div id="left">
			<form id="converter">
				EUR TO <input type="text" id="newCurrency">
				<button type="button" id="currencyConvert">Convertir</button>
			</form>
			<h2>Voiture à vendre</h2>
			<table id="tosell">
				<tr>
					<th>Marque</th>
					<th>Modèle</th>
					<th>Plaque d'immatriculation</th>
					<th>Date de premiere circulation</th>
					<th>Prix</th>
					<th>Action</th>
				</tr>
			</table>
		</div>
	</div>
	<script language="JavaScript" type="text/javascript"
		src="http://code.jquery.com/jquery.js"></script>
</body>

<script>
		$.ajax({ url: "MLVServlet",
			type:"get",
			data: "action=list",
	        context: document.body,
	        success: function(msg){
	           $("#tosell").html(msg);
	        }});
	
	$(document).ready()
	{
		$(document).on("click", ".addtobasket", function() {
			var id = this.id;
			$.ajax({
				type : "post",
				url : "MLVServlet", //this is my servlet
				data : "licensePlate=" + id,
				success : function(msg) {
					$("#basket").html(msg);

				}
			})
		});

		$(document).on("click", ".removecar", function() {
			var id = this.id;

			$.ajax({
				type : "get",
				url : "MLVServlet", //this is my servlet
				data : "action=" + id,
				success : function(msg) {
					$("#basket").html(msg);
				}
			})
		});

		$("#removeAll").on("click", function() {
			var id = this.id;
			$.ajax({
				type : "get",
				url : "MLVServlet", //this is my servlet
				data : "action=" + id,
				success : function(msg) {
					$("#basket").html(msg);
				}
			})
		});

		$("#purchaseBasket").on("click",function() {
					var id = this.id;
					var cars = document.getElementsByClassName("panier");
					var output = "";
					for (var i = 0; i < cars.length - 1; i++) {
						output = output
								.concat(((cars[i].innerHTML).concat(","))
										.replace(/\s/g, ''));
					}
					output = output.concat((cars[i].innerHTML).replace(/\s/g,''));
					$.ajax({
						type : "get",
						url : "MLVServlet", //this is my servlet
						data : "action=" + id + "&cars=" + output,
						success : function(msg) {
							if(msg === "false"){
								alert("Vous n'avez pas assez de fond");
								$("#basket").empty();
							}else{
								$("#tosell").html(msg);
								$("#basket").empty();
							}
						}
					})
				});
		
		$("#currencyConvert").on("click",function(){
			var id = this.id;
			$.ajax({
				type : "get",
				url : "MLVServlet",
				data : "action=currencyConvert&newCurrency="+ document.getElementById("newCurrency").value,
				success : function(msg) {
					var montants = document.getElementsByClassName("montant");
					for(var i=0; i<montants.length; i++){
						document.getElementsByClassName("montant")[i].innerHTML = Math.round((montants[i].innerHTML*msg) * 100) / 100;
					}
				}
			})
		});
	}
</script>
</html>