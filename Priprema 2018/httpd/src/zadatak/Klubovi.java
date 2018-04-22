package zadatak;

import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Klubovi {
	private List<Klub> klubovi = null;
	private String[] gradovi = {"Novi Sad", "Ruma", "Ruski Krstur", "Vrbas", "Bačka Palanka", "Melenci"};
	
	public Klubovi(){
		klubovi = new ArrayList<Klub>();
	}
	
	//dodaje novi klub ako vec nije dodan
	public boolean dodajKlub(Klub k){
		if( klubovi.contains(k) == false ){
			klubovi.add(k);
			return true;
		}
		return false;
	}
	
	//podesi bodove kluba
	public void setBodove(int i, int bodovi){
		klubovi.get(i).setBodovi(bodovi);
	}
	
	//podesi informacije o klubu
	public void setKlub(int indeks, String naziv, String grad, boolean aktivan){
		Klub k = klubovi.get(indeks);
		k.setNaziv(naziv);
		k.setGrad(grad);
		k.setAktivan(aktivan);
	}
	
	//pronalazi vodeci klub - sa najvise bodova
	public Klub vodeciKlub(){
		Klub vodeci = klubovi.get(0);
		for(int i = 1; i < klubovi.size(); ++i){
			if( vodeci.getBodovi() < klubovi.get(i).getBodovi() ){
				vodeci = klubovi.get(i);
			}
		}
		return vodeci;
	}
	
	//generise HTML stranicu za prikaz vodeceg kluba
	public void HTMLVodeci(PrintStream ps){
		//ispis zaglavlja
		ps.print("HTTP/1.1 200 OK\r\n\r\n");
		ps.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		ps.println("<html>");
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		ps.println("<title>Pregled klubova</title>");
		ps.println("</head>");
		ps.println("<body>");
		
		if( klubovi.size() > 0 ){
			Klub k = vodeciKlub();			
			ps.println("<p style = \"border:1px solid black; padding:10px\">");
			ps.println("Trenutno vodeći klub:");
			ps.println("<b>" + k.getNaziv() + " " + k.getGrad() +  "</b>");
			ps.println("</p>");
		}
		else{
			ps.println("<h1>Ne postoji vodeći klub.</h1>");
		}
		
		//kraj HTML dokumenta
		ps.println("</body>");
		ps.println("</html>");
	}
	
	//Generise HTML stranicu za prikaz svih klubova i opcija
	public void HTML(PrintStream ps){
		//ispis zaglavlja
		ps.print("HTTP/1.1 200 OK\r\n\r\n");
		ps.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		ps.println("<html>");
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		ps.println("<title>Pregled klubova</title>");
		ps.println("</head>");
		ps.println("<body>");
		
		//generisanje tabele
		tabelaHTML(ps);
		
		//generisanje linkova
		ps.println("<p>");	
		Path path = Paths.get("");
		String dodajPath = "file:///" + path.toAbsolutePath() + "\\dodaj.html";
		dodajPath = dodajPath.replace("\\", "/");
		ps.println("<a href = \"" + dodajPath + "\" > Dodaj novi klub </a>" );
		ps.println("<br/>");		
		ps.println("<a href = \"prikaziVodeci\" > Prikaži vodeći klub </a>" );		
		ps.println("</p>");
		
		//generisanje forme za dodavanje bodova
		upisBodovaForma(ps);
		
		//kraj HTML dokumenta
		ps.println("</body>");
		ps.println("</html>");
	}
	
	//generise tabelu sa klubovima
	public void tabelaHTML(PrintStream ps){
		//naslov tabela
		ps.println("<h1 style = \"color:blue\"> Tabela </h1>");		
		
		//tabela
		ps.println("<table border = \"1\" style = \"text-align:center\">");	
		
		//naslovi u tabeli
		ps.println("<tr>");
		ps.println("<td>#</td>");
		ps.println("<th>Klub</th>");
		ps.println("<td>Bodovi</td>");
		ps.println("<td>Akcije</td>");
		ps.println("</tr>");
		
		for(int i = 0; i < klubovi.size(); ++i){
			Klub k = klubovi.get(i);
			ps.println("<tr>");
			ps.println("<td>" + (i+1) + "</td>");
			ps.println("<td>" + k.getNaziv() + " " + k.getGrad() + "</td>");
			ps.println("<td>" + k.getBodovi() + "</td>");
			ps.println("<td>");
			ps.println("<a href = \"http://localhost:8080/uredi?" + i + "\">" + "Izmena podataka" + "</a>");
			ps.println("</td>");
			ps.println("</tr>");
		}
		
		ps.println("</table>");
	}
	
	//generise formu za dodavanje bodova nekom klubu
	public void upisBodovaForma(PrintStream ps){
		//naslov
		ps.println("<h1 style = \"color:blue\"> Upis bodova </h1>");	
		//forma
		ps.println("<form action = \"http://localhost:8080/unesiBodove\">");
		ps.println("<table style = \"text-align:center\">");		
		
		//klub
		ps.println("<tr>");
		ps.println("<td>Klub</td>");
		//generisanje combobox-a
		ps.println("<td>");
		ps.println("<select name=\"klub\">");
		for(int i = 0; i < klubovi.size(); ++i){
			Klub k = klubovi.get(i);
			ps.println(" <option value = \"" + i + "\">" + k.getNaziv() + " " + k.getGrad() + "</option>");			
		}
		ps.println("</select>");
		ps.println("</td>");
		ps.println("</tr>");
		
		//broj bodova
		ps.println("<tr>");
		ps.println("<td>Bodovi</td>");
		ps.println("<td> <input type=\"number\" name=\"bodovi\" /> </td>");
		ps.println("</tr>");
		
		//dugme
		ps.println("<tr>");		
		ps.println("<td colspan = \"2\"> <input type = \"submit\" value = \"Unesi\" /> </td>");
		ps.println("</tr>");
		
		ps.println("</table>");
		ps.println("</form>");
	}
	
	
	//generise html za izmenu podataka o nekom klubu
	public void izmeniKlubHTML(PrintStream ps, int i){
		Klub k = klubovi.get(i);
		//ispis zaglavlja
		ps.print("HTTP/1.1 200 OK\r\n\r\n");
		ps.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		ps.println("<html>");
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		ps.println("<title>Pregled klubova</title>");
		ps.println("</head>");
		ps.println("<body>");
		
		//tabela 
		ps.println("<form action = \"http://localhost:8080/urediOk/" + i + "\">");
		ps.println("<table style = \"border:1px solid black; padding:10px\">");
		//naslov
		ps.println("<tr><td colspan = \"2\" style = \"text-align:center\"><h1 style = \"color:green\"> Izmena podataka </h1></td></tr>");
		//naziv kluba
		ps.println("<tr>");
		ps.println("<td>Naziv</td>");
		ps.println("<td> <input type=\"text\" name=\"naziv\" value=\"" + k.getNaziv() + "\"/> </td>");
		ps.println("</tr>");
		//grad
		ps.println("<tr>");
		ps.println("<td>Grad</td>");
		ps.println();
		ps.print("<td>");
		ps.println("<select name=\"grad\">");
		for(int j = 0; j < gradovi.length; ++j){
			String s = gradovi[j];
			if( s.equals(k.getGrad()) == false )
				ps.println(" <option value = \"" + s + "\">" + s +"</option>");	
			else
				ps.println(" <option value = \"" + s + "\" selected>" + s +"</option>");	
				
		}
		ps.println("</select>");
		ps.print("</td>");
		ps.println("</tr>");
		//aktivan
		ps.println("<tr>");
		ps.println("<td>Aktivan</td>");			
		if( k.isAktivan() == false )
			ps.println("<td> <input type = \"checkbox\" name = \"aktivan\" /> </td>");
		else
			ps.println("<td> <input type = \"checkbox\" name = \"aktivan\" checked/> </td>");
		ps.println("</tr>");
		//dugme
		ps.println("<td></td>");
		ps.println("<td> <input type = \"submit\" value = \"Izmeni\" /> </td>");
		
		ps.println("</table>");
		ps.println("</form>");
		//kraj HTML dokumenta
		ps.println("</body>");
		ps.println("</html>");
	}
}
