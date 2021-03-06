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
 * Servlet implementation class ActivateDeactivateAccountServlet
 */
@WebServlet("/ActivateDeactivateAccountServlet")
public class ActivateDeactivateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivateDeactivateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			String accountNumber = request.getParameter("accountNumber");
			
			if( user.getAccounts().containsKey(accountNumber)){
				user.getAccounts().get(accountNumber).setActive(!user.getAccounts().get(accountNumber).getActive());
			}
			
			RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
			disp.forward(request, response);			
		}
		else {
			response.sendRedirect("LoginServlet");
		}
	}

}
