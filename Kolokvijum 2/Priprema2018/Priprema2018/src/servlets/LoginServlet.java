package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		context.setAttribute("users", new UserDAO());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher("/JSP/login.jsp");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("txt_usename");
    	String password = request.getParameter("txt_password");
    	
    	if(username == null || password == null){
    		request.setAttribute("err", "Pogrešno korisničko ime ili lozinka");
    		doGet(request, response);
    		return;
    	}
    	
    	ServletContext context= getServletContext();
    	UserDAO users= (UserDAO) context.getAttribute("users");
    	User user= users.find(username, password);
    	if(user == null) {
    		request.setAttribute("err", "Pogrešno korisničko ime ili lozinka");
    		doGet(request, response);
    		return;
    	}
    	
    	user.setLoggedIn(true);
    	HttpSession session= request.getSession();
    	session.setAttribute("user", user);    
    	RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
		disp.forward(request, response);
	}

}
