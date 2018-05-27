package servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("http://localhost:8080/Servleti/ListUsersServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//preuzmi listu korisnika
		ServletContext ctx = getServletContext();
		List<String> korisnici = null;
		if(ctx.getAttribute("korisnici") == null){
			korisnici = new ArrayList<String>();
		}
		else{
			 korisnici = (List<String>) ctx.getAttribute("korisnici");
		}
		
		//izbrisi korisnika
		String username = request.getParameter("username");			
		korisnici.remove(username);
		ctx.setAttribute("korisnici", korisnici);
		
		response.sendRedirect("ListUsersServlet");		
	}

}
