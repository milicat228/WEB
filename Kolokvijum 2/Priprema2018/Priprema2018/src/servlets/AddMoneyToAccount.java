package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

/**
 * Servlet implementation class AddMoneyToAccount
 */
@WebServlet("/AddMoneyToAccount")
public class AddMoneyToAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddMoneyToAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(checkParams(request) == true){
				String accountNumber = request.getParameter("txt_broj");
				String amountString = request.getParameter("txt_iznos");
				double amount = Double.parseDouble(amountString);
				
				if(user.getAccounts().containsKey(accountNumber)){
					user.getAccounts().get(accountNumber).setAvailableMoney(user.getAccounts().get(accountNumber).getAvailableMoney() + amount);
				}				
				
			}
			RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
			disp.forward(request, response);
		}
		else {
			response.sendRedirect("LoginServlet");
		}
	}
	
	private boolean checkParams(HttpServletRequest request){
		
		String accountNumber = request.getParameter("txt_broj");
		if (isValid(accountNumber) == false) {
			request.setAttribute("err2","Niste uneli broj računa.");
			return false;
		}
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		if(user.getAccounts().containsKey(accountNumber) == false ){
			request.setAttribute("err2","Neispravan broj računa.");
			return false;
		}
		if(user.getAccounts().get(accountNumber).getActive() == false){
			request.setAttribute("err2","Neaktivan račun.");
			return false;
		}
		
		String amountString = request.getParameter("txt_iznos");
		double amount = 0;
		try{
			amount = Double.parseDouble(amountString);
		}
		catch(NumberFormatException e){
			request.setAttribute("err2","Niste ispravno željeni iznos.");
			return false;
		}
		if(amount < 0){
			request.setAttribute("err2","Željeni iznos mora biti pozitivan broj.");
			return false;
		}
		
		return true;
	}
	
	private boolean isValid(String text) {
		if (text == null)
			return false;

		if (text.trim().isEmpty())
			return false;

		return true;
	}
}
