package servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		String username = request.getParameter("username");	
		
		//preuzmi listu korisnika
		ServletContext ctx = getServletContext();
		List<String> korisnici = null;
		if(ctx.getAttribute("korisnici") == null){
			korisnici = new ArrayList<String>();
		}
		else{
			 korisnici = (List<String>) ctx.getAttribute("korisnici");
		}
		
		//dodaj novog korisnika u listu
		if( korisnici.contains(username) == false ){
			korisnici.add(username);
			//vrati listu korisnika
			ctx.setAttribute("korisnici", korisnici);
			//preusmeri na listu korisnika
			response.sendRedirect("ListUsersServlet");
		}
		else{
			//korisnik vec postoji			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter pout = response.getWriter();
			pout.println("<html>");
			pout.println("<head>");
			pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			pout.println("</head>");
			pout.println("<body>");
			pout.println("<h1>Korisnik " + username + " već postoji.</h1>");
			pout.println("<br/><br/><a href=\"register.html\"> Registruj novog korisnika </a>");	
			pout.println("</body>");
			pout.println("</html>");
			pout.close();
		}
		
	
	}

}
