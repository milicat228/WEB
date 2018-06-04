<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<c:forEach var="item" items="${cart.items}">
				<tr>
					<td> ${item.product.name} </td>
					<td> ${item.count} </td>
					<td> ${item.product.price} </td>
					<td> ${item.product.price * item.count} </td>
				</tr>
				
			</c:forEach>
			
			<tr>
				<td colspan = "3" style="text-align:right"> <b>Suma: </b> </td>
				<td> ${cart.total} </td>
			</tr>
			
		</tbody>
	</table>	

</body>

</html>