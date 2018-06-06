package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PonudeDAO;

/**
 * Servlet implementation class FilterServlet
 */
@WebServlet("/FilterServlet")
public class FilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = getServletContext();	
		if( request.getParameter("kojeDugme") == null ){			
			if(context.getAttribute("filter") != null ){
				context.removeAttribute("filter");				
			}
			if(context.getAttribute("filterPonuda") != null){
				context.removeAttribute("filterPonuda");
			}
		}
		else{		
			Boolean sezona = false;
			if(request.getParameter("sezona") != null){
				sezona = true;
			}
			Boolean taksa = false;
			if(request.getParameter("taksa") != null){
				taksa = true;
			}
			
			PonudeDAO ponude =  (PonudeDAO) context.getAttribute("ponude");	
			context.setAttribute("filterPonuda", ponude.filtriraj(sezona, taksa));
			context.setAttribute("filter", true);
		}
		
		RequestDispatcher disp = request.getRequestDispatcher("/JSP/page.jsp");
		disp.forward(request, response);	
	}

}
