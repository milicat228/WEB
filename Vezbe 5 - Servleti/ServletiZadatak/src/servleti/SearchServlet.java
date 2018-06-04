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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// preuzmi listu korisnika
		ServletContext ctx = getServletContext();
		List<String> korisnici = null;
		if (ctx.getAttribute("korisnici") == null) {
			korisnici = new ArrayList<String>();
		} else {
			korisnici = (List<String>) ctx.getAttribute("korisnici");
		}

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pout = response.getWriter();
		pout.println("<html>");
		pout.println("<head>");
		pout.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		pout.println("</head>");
		pout.println("<body>");

		// preuzmi trazenog korisnika
		String username = request.getParameter("username");

		if (korisnici.contains(username)) {
			
			pout.println("<h1>Korisnik " + username + " postoji.</h1>");

		} else {

			pout.println("<h1>Korisnik " + username + " nije pronađen.</h1>");

		}

		pout.println("<br/><br/><a href=\"ListUsersServlet\"> Nazad na listu korisnika </a>");
		pout.println("</body>");
		pout.println("</html>");
		pout.close();

	}

}
