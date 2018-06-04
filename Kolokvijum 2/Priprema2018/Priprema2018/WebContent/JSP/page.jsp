<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="beans.User"%>
<%@page import="beans.Account"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Računi</title>
	<script>
		function validation(form){
			
			var error = 0;
			
			var accountNumber = form.elements["txt_broj"];	
			if (!(accountNumber.value)) {
				// Postavi boju inputa na crvenu
				accountNumber.style.borderColor = 'red';
				error = 1;
			}
			
			var availableMoney = form.elements["txt_raspolozivo"];	
			if (!(availableMoney.value)) {
				// Postavi boju inputa na crvenu
				availableMoney.style.borderColor = 'red';
				error = 1;
			}
			else if( availableMoney.value < 0 ){
				availableMoney.style.borderColor = 'red';
				error = 1;
			}
			
			var reservedMoney = form.elements["txt_rezervisano"];	
			if (!(reservedMoney.value)) {
				// Postavi boju inputa na crvenu
				reservedMoney.style.borderColor = 'red';
				error = 1;
			}
			else if( reservedMoney.value < 0 ){
				reservedMoney.style.borderColor = 'red';
				error = 1;
			}	
			
			return error === 0;
		}
		
	function validation2(form){
			
			var error = 0;
			
			var accountNumber = form.elements["txt_broj"];	
			if (!(accountNumber.value)) {
				// Postavi boju inputa na crvenu
				accountNumber.style.borderColor = 'red';
				error = 1;
			}
			
			var amount = form.elements["txt_iznos"];	
			if (!(amount.value)) {
				// Postavi boju inputa na crvenu
				amount.style.borderColor = 'red';
				error = 1;
			}
			else if( amount.value < 0 ){
				amount.style.borderColor = 'red';
				error = 1;
			}			
			
			return error === 0;
		}
	</script>
</head>
<body>

	<h2>Računi</h2>
	
	<form action="AddAccountServlet" method="POST" onsubmit="return validation(this)">
	<table>
		<tr>
			<td>Broj računa</td>
			<td><input type="text" name="txt_broj"></td>
		</tr>
		<tr>
			<td>Tip računa</td>
			<td>
				<select name="tip">
					<option>Dinarski</option>
					<option>Devizni</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Raspoloživo stanje</td>
			<td><input type="text" name="txt_raspolozivo"></td>
		</tr>
		<tr>
			<td>Rezervisano stanje</td>
			<td><input type="text" name="txt_rezervisano"></td>
		</tr>
		<tr>
			<td>Online</td>
			<td><input type="checkbox" name="cb_online"></td>
		</tr>	
		<tr>
			<td colspan="2"><input type="submit" name="btn_uplati" value="Dodaj račun"></td>
		</tr>
	</table>
	</form>
	
	<c:if test="${not empty err}">
		<p style="color: red">${err}</p>
	</c:if>
	
	<br><br>
	
	<table border="1">
		<thead>
			<tr><th>Broj računa</th><th>Tip računa</th><th>Rezervisano</th><th>Raspoloživo</th><th>Ukupno</th><th>Online</th><th>Aktivan</th></tr>
		</thead>
		<tbody>
			<c:forEach var="enrty" items="${user.accounts}">
				<tr>
					<td> ${enrty.value.accountNumber} </td>
					<td> ${enrty.value.accountType} </td>
					<td> ${enrty.value.reservedMoney} </td>
					<td> ${enrty.value.availableMoney} </td>					
					<td> ${enrty.value.reservedMoney + account.availableMoney} </td>
					<td>
						${enrty.value.online ? "Da" : "Ne"}
					</td>
					<td>
						${enrty.value.active ? "Da" : "Ne"}
					</td>	
					<td>
						<a href="DeleteAccountServlet?accountNumber=${enrty.value.accountNumber}"> Obriši </a>
					</td>	
					<td>
						<a href="ActivateDeactivateAccountServlet?accountNumber=${enrty.value.accountNumber}"> ${enrty.value.active ? "Deaktiviraj" : "Aktiviraj"} </a>
					</td>			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h2>Uplata</h2>
	<form action="AddMoneyToAccount" method="POST" onsubmit="return validation2(this)">
		<table>
			<tr>
				<td>Račun</td>
				<td>
					<select name="txt_broj">
						<c:forEach var="enrty" items="${user.accounts}">
							<c:if test="${enrty.value.active}">
								<option>${enrty.value.accountNumber}</option>
							</c:if>						
						</c:forEach>						
					</select>
				</td>
			</tr>
			<tr>
				<td>Iznos</td>
				<td><input type="text" name="txt_iznos"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="btn_uplati" value="Uplati"></td>
			</tr>
		</table>
	</form>
	
	<c:if test="${not empty err2}">
		<p style="color: red">${err2}</p>
	</c:if>
	
	<hr>
	<a href="LogoutServlet">Odjava</a>
	
</body>
</html>