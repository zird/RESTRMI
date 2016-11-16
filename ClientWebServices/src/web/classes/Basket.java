package web.classes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Basket
 */
@WebServlet("/Basket")
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Set<String> basket = new HashSet<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Basket() {
		super();
		basket.add("AB 234 DE");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionOrId = request.getParameter("action");
		CarsService carsService =(CarsService)request.getAttribute("carsService");
		switch(actionOrId){
		case "removeAll":
			basket.clear();
			break;
		case "purchaseBasket":
			if(basket.isEmpty()){
				return;
			}
			String strCar = request.getParameter("cars");
			System.out.println("http://localhost:8080/ServerCarRenting/services/MLVCarsService?method=purchaseBasket&strLicensePlates="+ strCar);
			URL urlPurchase = new URL("http://localhost:8080/ServerCarRenting/services/MLVCarsService?method=purchaseBasket&strLicensePlates="+ strCar);
			HttpURLConnection connectionPurchase = (HttpURLConnection)urlPurchase.openConnection();
			connectionPurchase.setRequestMethod("GET");
			connectionPurchase.setDoOutput(true);
			connectionPurchase.connect();
			connectionPurchase.disconnect();
			
			URL url = new URL("http://localhost:8080/ServerCarRenting/services/MLVCarsService?method=list");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.connect();
			
			StringBuilder result = new StringBuilder();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		         result.append(line);
		    }
		    rd.close();
		    connection.disconnect();
		    try{
		    	String res = result.toString();
		    	res = res.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		    	int start = res.toString().indexOf("<tr>");
		    	int last = res.toString().lastIndexOf("</tr>");
		    	res = res.substring(start,last);
		    	
				System.out.println(result);
				System.out.println("Answer: " + res);
				response.getWriter().append(res);
				basket.clear();
				return;
		     }catch(Exception e){
		    	  e.printStackTrace();
		      }
		default :
			basket.remove(actionOrId);
			break;
		}

	StringBuilder sb = new StringBuilder();
	for(String str:basket)
	{
		sb.append("<tr><th>" + str + "</th><th><button class=\"removecar\" type=\"button\" id=\"" + str
				+ "\">-</button></th></tr>");
	}response.getWriter().append(sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		basket.add(request.getParameter("licensePlate"));
		StringBuilder sb = new StringBuilder();
		for(String str : basket){
			sb.append("<tr><th class=\"panier\">"+str+"</th><th><button class=\"removecar\" type=\"button\" id=\""+ str +"\">-</button></th></tr>");
		}
		response.getWriter().append(sb.toString());
	}

}
