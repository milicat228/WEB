package priprema2016;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

public class HTMLGenerator {
	public int boje = 0; //0 - zelene, 1 - narandzaste
	public int filter = 0; //0 - prikazi sve, 1 - prikazi registorvane iz Chroma, 2 - Firefox
	public HashMap<String, Integer > gradovi;
	
	//konstruktor puni mapu gradovima i njihovim postanskim brojevima
	public HTMLGenerator(){
		gradovi = new HashMap<String, Integer>();
		gradovi.put("Novi Sad", 21000);
		gradovi.put("Milići", 75446);
		gradovi.put("Ruma", 22400);
		gradovi.put("Bačka Palanka", 21400);
		gradovi.put("Vrbas", 21460);
		gradovi.put("Ruski Krstur", 25233);
		gradovi.put("Melenci", 23270);
		gradovi.put("Inđija", 22320);
		gradovi.put("Margita", 26364);
		
	}
	
	//metoda koja generise head sekciju html-a
	public void headHTML(PrintStream ps){
		//izberi potrebne boje
		String tamnaBoja = "#009900";
		String svetlaBoja = "#e6ffe6";
		if( boje == 1 ){
			tamnaBoja = "#ff9900";
			svetlaBoja = "#ffebcc";
		}
		
		//generisi zaglavlje
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		ps.println("<title>Priprema 2016</title>");
		ps.println("<style>");
		ps.println("body{background-color:" + svetlaBoja + "; }");		
		ps.println("*.tekst{ color:" + tamnaBoja + "; background-color:" + svetlaBoja + ";}");
		ps.println("</style>");
		ps.println("</head>");
	}
	
	//metoda koja generise formu na vrhu stranice
	public void generisiFormu(Korisnik k, PrintStream ps){
		ps.println("<form action=\"http://localhost:8080/aplikacija\" method=\"get\">");
		ps.println("<table border = \"1\" >");
		//skriveni stari mejl (za sacuvaj izmene i brisi)		
		ps.println("<input style = \"display: none\" type=\"text\" name=\"skriveniEmail\" value=\"" + k.getEmail() + "\"> ");
		//ime
		redUFormi("Ime", k.getIme(), ps);
		//prezime
		redUFormi("Prezime", k.getPrezime(), ps);
		//email
		redUFormi("Email", k.getEmail(), ps);
		//grad
		ps.println("<tr>");
		ps.println("<td class = \"tekst\" >" + "Grad" + "</td>");
		ps.println("<td>");
		ps.println("<select name=\"grad\">");
		for(String grad: gradovi.keySet()){
			if( k.getGrad().equals(grad) == false )
				ps.println(" <option value = \"" + grad + "\">" + grad + "</option>");
			else
				ps.println(" <option selected value = \"" + grad + "\">" + grad + "</option>");
		}
		ps.println("</select>");
		ps.println("</td>");
		ps.println("</tr>");
		//kredit
		redUFormi("Kredit", Double.toString(k.getKredit()), ps);
		//dugmici
		ps.println("<tr>");
		ps.println("<td colspan = \"2\">");		
		ps.println("<input type=\"submit\" name=\"dodaj\" value=\"Dodaj\"> ");
		ps.println("<input type=\"submit\" name=\"izmeni\" value=\"Snimi izmenu\"> ");
		ps.println("<input type=\"submit\" name=\"obrisiIzabranog\" value=\"Obriši\"> ");
		ps.println("<input type=\"submit\" name=\"odustani\" value=\"Odustani\"> ");		
		ps.println("</td>");
		ps.println("</tr>");
		ps.println("</table> <br/><br/>");
		ps.println("</form>");
	}
	
	//generiše obični text red input u formi
	private void redUFormi(String naziv, String popuni, PrintStream ps){
		ps.println("<tr>");
		ps.println("<td class = \"tekst\" >" + naziv + "</td>");
		ps.println("<td> <input style = \"width: 100%\" type=\"text\" name=\"" + naziv + "\" value=\"" + popuni + "\" /> </td>");
		ps.println("</tr>");
	}
	
	//metoda koja generise tabelu za prosledejene korisnike
	public void tabelaHTML(List<Korisnik>korisnici, PrintStream ps){
		//ako su prikazani svi korisnici da li će se prikazati filtracija zavisi od toga da li ima
		//koirsnika, a ako je uključen filter, uvek se prikazuje meni za filtraciju
		boolean prikaziFilter = false;
		boolean tableNeeded = true;
		if( filter == 1 || filter == 2 )
			prikaziFilter = true;
		
		//naslov
		ps.println("<h1 class = \"tekst\"> Trenutno registrovani korisnici: </h1> ");			
		
		if( korisnici.isEmpty() == true ){			
			ps.println("<p class = \"tekst\"> Trenutno nema registrovanih korisnika </p>");			
		}
		else{			
			ps.println("<table border = \"1\">");
			for(int i = 0; i < korisnici.size(); ++i){
				Korisnik k = korisnici.get(i);
				ps.println("<tr>");
				ps.println("<td>" + k.getIme() + "</td>");
				ps.println("<td>" + k.getPrezime() + "</td>");
				ps.println("<td>" + k.getEmail() + "</td>");
				ps.println("<td>" + gradovi.get(k.getGrad()) + " "+ k.getGrad() + "</td>");
				ps.println("<td>" + k.getKredit() + "</td>");
				ps.println("<td valign=\"center\">");
				ps.println("<form action=\"http://localhost:8080/aplikacija\" method=\"get\"> ");
				//skriveno polje sa mejlom da bi znali kog korisnika hoće da menjaju
				ps.println("<input style = \"display: none\" type=\"text\" name=\"skriveniEmail\" value=\"" + k.getEmail() + "\"> ");
				ps.println("<input type=\"submit\" name=\"izmeniIzabranog\" value=\"Izmeni\"> ");
				ps.println("<input type=\"submit\" name=\"obrisiIzabranog\" value=\"Obriši\"> ");	
				ps.println("</form>");
				ps.println("</td>");
				ps.println("</tr>");				
			}			
			prikaziFilter = true;	
			tableNeeded = false;
		}		
		
		if( prikaziFilter == true ){
			if( tableNeeded == true )
				ps.println("<table border = \"1\">");
			filterHTML(ps);
		}
		
		ps.println("</table>");
	}
	
	//generiše meni za filtraciju
	public void filterHTML(PrintStream ps){
		//generisanje pretrage ispod tabele
		ps.println("<tr>");
		ps.println("<td colspan = \"7\">");
		ps.println("<form action=\"http://localhost:8080/aplikacija\" method=\"get\"> ");
		ps.println("<p style = \"text-align:center\" class = \"tekst\"> Filtriraj po:  <br/>");	
		ps.println("<select style = \"width: 90%\" name=\"izabraniFilter\" >");
		if( filter == 0 )
			ps.println(" <option selected value = \"Prikaži sve korisnike\"> Prikaži sve korisnike </option>");
		else
			ps.println(" <option value = \"Prikaži sve korisnike\"> Prikaži sve korisnike </option>");
		if( filter == 2 )
			ps.println(" <option selected value = \"Firefox\"> Firefox </option>");
		else
			ps.println(" <option value = \"Firefox\"> Firefox </option>");
		if( filter == 1 )
			ps.println(" <option selected value = \"Chrome\"> Chrome </option>");
		else
			ps.println(" <option value = \"Chrome\"> Chrome </option>");
		ps.println("</select><br/>");
		ps.println("<input type=\"submit\" name=\"filter\" value=\"Filteriraj\"> ");
		ps.println("</form>");
		ps.println("</p>");
		ps.println("</td>");
		ps.println("</tr>");				
	}
	
	//generise odgovor sa odredjenom porukom i dugmetom za povratak nazad na aplikaciju
	public void poruka(PrintStream ps, String poruka){
		// ispisemo zaglavlje HTTP odgovora
		ps.print("HTTP/1.0 200 OK\r\n\r\n");
		ps.println("<html>");
		headHTML(ps);
		ps.println("<body>");
		ps.println("<h1 class = \"tekst\">" + poruka + " </h1> ");	
		ps.println("<form action=\"http://localhost:8080/aplikacija\">");
		ps.println("<input type=\"submit\" name=\"pokreni\" value=\"Nazad na glavnu stranu\"> ");
		ps.println("</form>");
		ps.println("</body>");
		ps.println("</html>");
	}
	
	//generise osnovnu stranu aplikacije
	//Korisnik k je korisnik koga treba prikazati u formi na vrhu -> ako je null, znaci nikoga
	public void generisiHTMLAplikacije(PrintStream ps, Korisnici korisnici, Korisnik k){
		// ispisemo zaglavlje HTTP odgovora
		ps.print("HTTP/1.0 200 OK\r\n\r\n");
		ps.println("<html>");
		headHTML(ps);
		ps.println("<body>");	
		ps.println("<table align=\"center\" style=\"border:1px solid black; padding:20px\">");
		ps.println("<tr>");
		ps.println("<td>");
		if( k == null ){
			k = new Korisnik("", "", "", "Novi Sad", 0, "");
		}
		generisiFormu(k, ps);
		ps.println("</td>");
		ps.println("</tr>");
		ps.println("<tr>");
		ps.println("<td>");		
		tabelaHTML(korisnici.filterKorisnika(filter), ps);
		ps.println("</td>");
		ps.println("</tr>");
		ps.println("</table>");
		ps.println("</body>");
		ps.println("</html>");
	}
}
