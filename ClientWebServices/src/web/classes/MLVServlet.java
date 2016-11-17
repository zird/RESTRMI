package web.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.rmi.RMISecurityManager;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.tempuri.ConverterLocator;
import org.tempuri.ConverterSoap;

/**
 * Servlet implementation class Basket
 */
@SuppressWarnings("deprecation")
@WebServlet("/MLVServlet")
public class MLVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Set<String> basket = new HashSet<>();
	private double moneyAvailable;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MLVServlet() {
		super();
		
		String codebase = "file:" + " "; // ADD SERVER PATH HERE
		
		System.setProperty("java.rmi.server.codebase", codebase);
		System.setProperty("java.security.policy","grant.policy");
		System.setSecurityManager(new RMISecurityManager());
		moneyAvailable = 30000.00;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionOrId = request.getParameter("action");
		switch(actionOrId){
		case "list":
			sellableCars(response);
			return;
		case "removeAll":
			basket.clear();
			break;
		case "purchaseBasket":
			purchaseBasket(request, response);
		    return;
		case "currencyConvert":
			try {
				requestCurrency(request,response);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		default :
			basket.remove(actionOrId);
			break;
		}

		StringBuilder sb = new StringBuilder();
		for(String str:basket){
			sb.append("<tr><th>" + str + "</th><th><button class=\"removecar\" type=\"button\" id=\"" + str
			+ "\">-</button></th></tr>");
		}
		response.getWriter().append(sb.toString());
	}

	private void requestCurrency(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
		ConverterSoap converter;
		converter = new ConverterLocator().getConverterSoap();
		response.getWriter().append(converter.getCurrencyRate(request.getParameter("newCurrency"), Calendar.getInstance(Locale.FRANCE)).toString());
	}

	private void purchaseBasket(HttpServletRequest request, HttpServletResponse response)
			throws MalformedURLException, IOException, ProtocolException {
		if(basket.isEmpty()){
			return;
		}
		String strCar = request.getParameter("cars");
		URL urlPurchase = new URL("http://localhost:8080/ServerCarRenting/services/MLVCarsService?method=purchaseBasket&strLicensePlates="+ strCar +"&amount="+ this.moneyAvailable);
		HttpURLConnection connectionPurchase = (HttpURLConnection)urlPurchase.openConnection();
		connectionPurchase.setRequestMethod("GET");
		connectionPurchase.setDoOutput(true);
		connectionPurchase.connect();
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(connectionPurchase.getInputStream()));
		StringBuilder result = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
		     result.append(line);
		}
		rd.close();
		if(result.toString().contains(">true<")){
			sellableCars(response);
		}else{
			basket.clear();
			response.getWriter().append("false");
		}
	}

	private void sellableCars(HttpServletResponse response)
			throws MalformedURLException, IOException, ProtocolException {
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
		try{
			String res = result.toString();
			res = res.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;","\"");
			int start = res.toString().indexOf("<tr>");
			int last = res.toString().lastIndexOf("</tr>");
			res = res.substring(start,last);

			basket.clear();
			response.getWriter().append(res);
		 }catch(Exception e){
			  e.printStackTrace();
		  }
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
