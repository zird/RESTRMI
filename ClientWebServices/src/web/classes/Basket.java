package web.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
			List<Car> cars = new ArrayList<>();
			System.out.println(basket);
			for(String str : basket){
				System.out.println(str);
				cars.add(carsService.getCarByLicensePlate(str));
			}
			carsService.purchase(cars);
			StringBuilder sb = new StringBuilder();
			for (Car car : carsService.getSellableCars()) {
				sb.append("<tr><th>" + car.getBrand() + "</th><th>" + car.getModel() + "</th><th>" + car.getLicensePlate() + "</th><th>" + car.getYearOfCirculation() + "</th><th>" + car.getPrice() + "</th><th> <button class=\"addtobasket\" type=\"button\" id=\""
							+ car.getLicensePlate() + "\">Ajouter au panier</button></th></tr>");
				};
			response.getWriter().append(sb.toString());
			request.setAttribute("action","removeAll");
			doGet(request,response);
			return;
		default :
			basket.remove(actionOrId);
			break;
		}

	StringBuilder sb = new StringBuilder();for(
	String str:basket)
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
			sb.append("<tr><th>"+str+"<button class=\"removecar\" type=\"button\" id=\""+ str +"\">-</button></th></tr>");
		}
		response.getWriter().append(sb.toString());
	}

}
