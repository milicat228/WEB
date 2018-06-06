<%@page import="java.util.HashMap"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="beans.Ponuda"%>
<%@page import="dao.PonudeDAO"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Ponude</title>
	
	<style type="text/css">
		tr.sezona{
		background: green;
		}
		tr.vanSezone{		
		}
	</style>
	
	<script type="text/javascript">
	
	function validation(form){
		var error = 0;
		
		var id = form.elements["id"];
		if (!( id.value)) {
			// Postavi boju inputa na crvenu
			 id.style.borderColor = 'red';
			error = 1;
		}
		
		var destinacija = form.elements["destinacija"];
		if (!( destinacija.value)) {
			// Postavi boju inputa na crvenu
			 destinacija.style.borderColor = 'red';
			error = 1;
		}
	}
	
	</script>
	
</head>
<body>

	<form action="DodajPonuduServlet" method="post" onsubmit="return validation(this)">
		<table>
		
			<tr>
				<td> Id: </td>
				<td> <input type="text" name="id" /> </td>
			</tr>
			
			<tr>
				<td> Destinacija: </td>
				<td> <input type="text" name="destinacija" /> </td>
			</tr>
			
			<tr>
				<td> Taksa: </td>
				<td> <input type="checkbox" name="taksa" /> </td>
			</tr>
			
			<tr>
				<td> Sezona: </td>
				<td> <input type="checkbox" name="sezona" /> </td>
			</tr>
			
			<tr>
				<td colspan = "2" align = "right">
					<input type="submit" value="Dodaj" />
				</td>
			</tr>
			
		</table>
	</form> 
	
	<c:if test="${not empty err}">
		<p style="color: red">${err}</p>
	</c:if>
	<br/> <br/>
	
	<%! HashMap<String,Ponuda> pon; %>
	
	<c:choose>
		<c:when test="${not empty filter}"> 
			<c:set var="pon" value="${filterPonuda}"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="pon" value="${ponude.ponude}"></c:set>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${not empty filter}">
		<p>Aktivna filtracija.</p>
	</c:if>
	<br/>
	
	<c:choose>
		<c:when test="${not empty pon}">
				<table border="1">
				<thead>
					<tr>
						<th colspan="3"> Ponude </th>
						</tr>
						<tr>
						<th> Id: </th>
						<th> Destinacija: </th>
						<th> Taksa: </th>
					</tr>
				</thead>
				<tbody>	
					<c:forEach var="ponuda" items="${pon}">			
						<tr class = "${ponuda.value.sezona ? 'sezona':'vanSezone'}">	
							<td> ${ ponuda.value.id } </td>
							<td> ${ ponuda.value.destinacija } </td>
							<td> ${ ponuda.value.taksa ? "DA" : "NE" } </td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
		</c:when>
		<c:otherwise>
			<p> ${not empty filter ? "Nema ponuda koje zadovoljavaju pretragu." : "Nema ponuda." } </p>		
		</c:otherwise>	
	</c:choose>
	
	
	
	<br/> <br/>
	
	
	<table>
		<tr>
			<td>
				<form action="FilterServlet" method="post">	
					<table>
						<tr>					
						<td> <input type="checkbox" name="sezona" /> U sezoni </td>
						<td> <input type="checkbox" name="taksa" />  Plaća se boravišna taksa </td>
						<td> <input type="text" name="kojeDugme" value="Filtriraj" style="display: none"/> </td>
						<td> <input type="submit" name= "ukljuciFilter" value="Filtriraj"/> </td>
						</tr>
					</table>
				</form>
			</td>			
			<td>  
				<form action="FilterServlet" method="post">	
						<table>
							<tr>								
							<td> <input type="submit" value="Poništi" /> </td>
							</tr>
						</table>
				</form>
			</td>
		</tr>
	</table>
	

</body>
</html>