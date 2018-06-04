<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="beans.Product"%>
<%@page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="products" class="dao.ProductDAO" scope="application"/>
<!-- Koristi objekat registrovan pod ključem "user" iz sesije -->
<jsp:useBean id="user" class="beans.User" scope="session"/>
<html>
<head>
</head>
<body>
	<p>Dobrodošli, ${user.firstName} <a href="LogoutServlet">Logout</a></p>
	<p><a href="AddItemToCartServlet">Vaša korpa </a></p>
	<table border="1">
		<thead>
			<th>Naziv</th>
			<th>Cena</th>
			<th colspan="2">Količina</th>
		</thead>
		<tbody>
		<!-- TODO 3: Izlistavanje proizvoda -->
			<c:forEach var="item" items="${products.findAll()}">
				<tr>					
					<td>${item.name}</td>
					<td>${item.price}</td>
					<form action="AddItemToCartServlet" method="POST">					
					<td>						
						<input type="number" name ="amount"/>
						<input style = "display: none" type="number" name ="id" value = "${item.id}" >
						<input type="submit" value="Dodaj u korpu">										
					</td>	
					</form>					
				</tr>
			</c:forEach>	
		</tbody>
	</table>
</body>
</html>