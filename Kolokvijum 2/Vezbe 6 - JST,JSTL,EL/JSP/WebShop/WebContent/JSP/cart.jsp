<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="beans.ShoppingCart"%>
<%@page import="beans.ShoppingCartItem"%>

<jsp:useBean id="user" class="beans.User" scope="session"/>
<jsp:useBean id="cart" class="beans.ShoppingCart" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Vaša korpa</title>
</head>

<body>
	<p><a href="ProductServlet">Nazad na proizvode</a></p>
	
	<table border="1">
		<thead>
			<th>Naziv</th>			
			<th>Količina</th>
			<th>Cena jednog</th>
			<th>Ukupno</th>
		</thead>
		<tbody>
		<!-- TODO 3: Izlistavanje proizvoda -->
			<% for(ShoppingCartItem item: cart.getItems()) {%>
				<tr>
					<td> <%= item.getProduct().getName() %> </td>
					<td> <%= item.getCount() %> </td>
					<td> <%= item.getProduct().getPrice() %> </td>
					<td> <%= item.getTotal() %> </td>
				</tr>
			<%} %>
			
			<tr>
				<td colspan = "3" style="text-align:right"> <b>Suma: </b> </td>
				<td> <%= cart.getTotal() %> </td>
			</tr>
			
		</tbody>
	</table>	

</body>

</html>