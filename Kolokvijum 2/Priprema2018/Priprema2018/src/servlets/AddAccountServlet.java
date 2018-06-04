package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Account;
import beans.User;

/**
 * Servlet implementation class AddAccountServlet
 */
@WebServlet("/AddAccountServlet")
public class AddAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAccountServlet() {
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
			
			Account account = createAccount(request);
			if(account == null){
				RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
				disp.forward(request, response);
				return;
			}
			
			User user = (User) session.getAttribute("user");
			user.getAccounts().put(account.getAccountNumber(),account);
			RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
			disp.forward(request, response);
		} else {
			response.sendRedirect("LoginServlet");
		}

	}
	
	//ovo vrsi i sve provere
	private Account createAccount(HttpServletRequest request){
		
		String accountNumber = request.getParameter("txt_broj");
		if (isValid(accountNumber) == false) {
			request.setAttribute("err","Niste uneli broj računa.");
			return null;
		}
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		if(user.getAccounts().containsKey(accountNumber)){
			request.setAttribute("err2","Račun već postoji.");
			return null;
		}
		
		
		String accountType = request.getParameter("tip");
		if (isValid(accountType) == false) {
			request.setAttribute("err","Niste izabrali ispravan tip računa.");
			return null;
		}
		
		
		String availableMoneyString = request.getParameter("txt_raspolozivo");
		double availableMoney = 0;
		try{
			availableMoney = Double.parseDouble(availableMoneyString);
		}
		catch(NumberFormatException e){
			request.setAttribute("err","Niste ispravno uneli raspoloživo stanje.");
			return null;
		}
		if(availableMoney < 0){
			request.setAttribute("err","Raspoloživo stanje mora biti pozitivan broj.");
			return null;
		}
		
		
		String reservedMoneyString = request.getParameter("txt_rezervisano");
		double reservedMoney = 0;
		try{
			reservedMoney = Double.parseDouble(reservedMoneyString);
		}
		catch(NumberFormatException e){
			request.setAttribute("err","Niste ispravno uneli rezervisano stanje.");
			return null;
		}
		if(reservedMoney < 0){
			request.setAttribute("err","Rezervisano stanje mora biti pozitivan broj.");
			return null;
		}
		
		
		Boolean online = false;
		if( request.getParameter("cb_online") != null){
			online = true;
		}
		
		
		Account ret = new Account(accountNumber, accountType, availableMoney, reservedMoney, online);
		return ret;		
	}

	private boolean isValid(String text) {
		if (text == null)
			return false;

		if (text.trim().isEmpty())
			return false;

		return true;
	}
	
}
