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

import beans.Product;
import beans.ShoppingCart;
import dao.ProductDAO;

/**
 * Servlet implementation class AddItemToCartServlet
 */
@WebServlet("/AddItemToCartServlet")
public class AddItemToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItemToCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addItemToCart(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addItemToCart(request, response);
	}
	
	private void addItemToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session= request.getSession(false);
		if( session.getAttribute("user") != null ){	
			
			if( session.getAttribute("cart") == null ){
				ShoppingCart cart = new ShoppingCart();
				session.setAttribute("cart", cart);
			}
			
			if( request.getParameter("id") != null ){
				String productId = (String) request.getParameter("id");	
				ServletContext context= getServletContext();
				ProductDAO products = (ProductDAO) context.getAttribute("products");
				Product p = products.findProduct(productId);				
				ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
				int count = Integer.parseInt(request.getParameter("amount"));
				cart.addItem(p, count);
			}		
			
			//na korpu
			RequestDispatcher disp= request.getRequestDispatcher("/JSP/cart.jsp");
			disp.forward(request, response);		
		}
		else{
			response.sendRedirect("LoginServlet");
		}	
	}

}
