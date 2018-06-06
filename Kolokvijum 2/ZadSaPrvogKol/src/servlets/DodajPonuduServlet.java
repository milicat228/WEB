package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Ponuda;
import dao.PonudeDAO;

/**
 * Servlet implementation class DodajPonuduServlet
 */
@WebServlet("/DodajPonuduServlet")
public class DodajPonuduServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
    public void init() throws ServletException { 
    	super.init();
    	ServletContext context = getServletContext();    	
    	context.setAttribute("ponude", new PonudeDAO());
    }

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DodajPonuduServlet() {
		super();		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Ponuda ponuda = kreirajPonudu(request);
		if( ponuda != null ){
			ServletContext context = getServletContext();
			PonudeDAO ponude =  (PonudeDAO) context.getAttribute("ponude");			
			if( ponude.dodajPonudu(ponuda) == false ){
				request.setAttribute("err", "Id more biti jedinstven.");				
			}
			context.setAttribute("ponude", ponude);
			context.removeAttribute("filter");
		}
		
		
		RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
		disp.forward(request, response);		
	}
	
	private Ponuda kreirajPonudu(HttpServletRequest request){
		//preuzmi id
		String id = request.getParameter("id");
		if(id == null){
			request.setAttribute("err", "Morate uneti id.");
			return null;
		}
		if(id.trim().isEmpty()){
			request.setAttribute("err", "Morate uneti id.");
			return null;
		}
		
		
		//preuzmi destinaciju
		String destinacija = request.getParameter("destinacija");
		if(destinacija == null){
			request.setAttribute("err", "Morate uneti destinaciju.");
			return null;
		}
		if(destinacija.trim().isEmpty()){
			request.setAttribute("err", "Morate uneti destinaciju.");
			return null;
		}
		
		//preuzmi placa li se taksa
		boolean taksa = false;
		if(request.getParameter("taksa") != null)
			taksa = true;
		
		//preuzmi da li je u sezoni
		boolean sezona = false;
		if(request.getParameter("sezona") != null)
			sezona = true;
		
		return new Ponuda(id, destinacija, taksa, sezona);
	}

}
