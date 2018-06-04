<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<title>Prijava</title>
</head>

<body>
	<h2>Prijava</h2>
	
	<form action="LoginServlet" method="POST">
	<table>
		<tr>
			<td>Korisničko ime</td>
			<td><input type="text" name="txt_usename" width="95%"></td>
		</tr>
		<tr>
			<td>Lozinka</td>
			<td><input type="password" name="txt_password"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="btn_login" value="Prijava"></td>
		</tr>
	</table>
	</form>
	
	<!-- Prikaži grešku, ako je bilo -->
	<c:if test="${not empty err}">
		<p style="color: red">${err}</p>
	</c:if>
	
</body>

</html>