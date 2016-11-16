<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.net.MalformedURLException"%>
<%@ page import="java.rmi.Naming"%>
<%@ page import="java.rmi.RMISecurityManager"%>
<%@ page import="java.rmi.RemoteException"%>
<%@ page import="java.util.*"%>
<%@ page import="web.classes.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cars</title>
</head>
<body>
	<%
		String codebase = "file:/Users/simrene/git/RESTRMI/ServerAndClient/ServerCarRenting/bin/";
		System.setProperty("java.rmi.server.codebase", codebase);

		System.setProperty("java.security.policy",
				"file:/Users/simrene/git/RESTRMI/ServerAndClient/ServerCarRenting/bin/grant.policy");

		System.setSecurityManager(new RMISecurityManager());
		CarsService carsServices = (CarsService) Naming.lookup("rmi://localhost:1099/CarsService");
		request.setAttribute("carsServices", carsServices);
	%>

	<h2>Panier</h2>
	<table id="basket">

	</table>
	<button id="removeAll" type="button">Vider</button>
	<button id="purchaseBasket">Acheter le panier</button>
	<h2>Voiture à vendre</h2>
	<table id="tosell">
	<%
		for (Car car : carsServices.getSellableCars()) {
			out.println("<tr><th>" + car.getBrand() + "</th><th>" + car.getModel() + "</th><th>" + car.getLicensePlate() + "</th><th>" + car.getYearOfCirculation() + "</th><th>" + car.getPrice() + "</th><th> <button class=\"addtobasket\" type=\"button\" id=\""
					+ car.getLicensePlate() + "\">Ajouter au panier</button></th></tr>");
		}
	%>
	</table>
	<script language="JavaScript" type="text/javascript"
		src="http://code.jquery.com/jquery.js"></script>
</body>

<script>
	$(document).ready()
	{
		$(".addtobasket").on("click",function() {
			var id = this.id;
			$.ajax({
				type : "post",
				url : "Basket", //this is my servlet
				data : "licensePlate=" + id,
				success : function(msg) {
					$("#basket").html(msg);

				}
			})
		});
		
		$(document).on("click",".removecar",function() {
			var id = this.id;
			
			$.ajax({
				type : "get",
				url : "Basket", //this is my servlet
				data : "action=" + id,
				success : function(msg) {
					$("#basket").html(msg);
				}
			})
		});

		$("#removeAll").click(function() {
			var id = this.id;
			$.ajax({
				type : "get",
				url : "Basket", //this is my servlet
				data : "action=" + id,
				success : function(msg) {
					$("#panier").html(msg);
				}
			})
		});
		
		$("#purchaseBasket").click(function(){
			var id = this.id;
			$.ajax({
				type : "get",
				url : "Basket", //this is my servlet
				data : "action=" + id,
				success : function(msg) {
					$("#tosell").html();
				}
			})
		});
	}
</script>
</html>