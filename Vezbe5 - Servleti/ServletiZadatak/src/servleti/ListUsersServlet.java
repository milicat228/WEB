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
 * Servlet implementation class ListUsersServlet
 */
@WebServlet("/ListUsersServlet")
public class ListUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//preuzmi listu korisnika
		ServletContext ctx = getServletContext();
		List<String> korisnici = null;
		if(ctx.getAttribute("korisnici") == null){
			korisnici = new ArrayList<String>();
		}
		else{
			 //ako lista ne postoji, napravi se
			 korisnici = (List<String>) ctx.getAttribute("korisnici");
			 ctx.setAttribute("korisnici", korisnici);
		}
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		pout.println("</head>");
		pout.println("<body>");
		
		if(korisnici.size() == 0){
			pout.println("Nema korisnika");
		}
		else{
			pout.println("<table border=\"1\">");
			for(String korisnik:korisnici){
				pout.println("<tr>");
				pout.println("<td>" + korisnik + "</td>");
				pout.println("<form action=\"DeleteUserServlet\" method=\"POST\">");
				pout.println("<td>");
				pout.println("<input type=\"text\" style=\"display: none\" name=\"username\" value = \"" + korisnik +"\"/>");
				pout.println("<input type=\"submit\" value=\"Obriši\" />");
				pout.println("</td>");
				pout.println("</form>");
				pout.println("</tr>");
			}
			pout.println("</table>");
		}
		
		pout.println("<br/><br/><a href=\"register.html\"> Registruj novog korisnika </a>");		
		
		pout.println("</body>");
		pout.println("</html>");
		pout.close();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
