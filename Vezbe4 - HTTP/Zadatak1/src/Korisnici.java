import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Korisnici {
	private List<String> korisnici = null;
	
	public Korisnici(){
		korisnici = new ArrayList<String>();
	}
	
	//dodvanje korisnika ukoliko ne postoji
	public boolean dodajKorisnika(String ime){
		if( korisnici.contains(ime) == false ){
			korisnici.add(ime);
			return true;
		}
		
		return false;
	}
	
	//brisanje korisnika, ako postoji
	public boolean brisiKorisnika(String ime){
		if( korisnici.contains(ime) == true ){
			korisnici.remove(ime);
			return true;
		}
		
		return false;
	}
	
	public boolean pretraziKorisnika(String ime){
		return korisnici.contains(ime);
	}
	
	//na OutputStream salje html tabelu koja sadrzi sve korisnike
	public void izlistajKorisnike(OutputStream os, String poruka, String select){
		PrintStream ps = new PrintStream(os);
		
		//html zaglavlje
		ps.print("HTTP/1.1 200 OK\r\n\r\n");
		ps.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		ps.println("<html>");
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		ps.println("<title>Lista korisnika</title>");
		ps.println("</head>");
		ps.println("<body>");
		
		//ispis specijlne poruke: npr. Pronadjen je korisnik i Dodan je korisnik
		if( poruka != null ){
			specijalnaPoruka(ps, poruka);
		}
		
		//forma za dodavanje novog korisnika
		dodajNovogKorinikaForma(ps);
		
		//ispis forme za pretragu
		pretragaForma(ps);
		
		//ispis tabele sa korisnicima
		tabelaKorisnika(ps, select);
		
		//kraj html dokumenta
		ps.println("</body>");
		ps.println("</html>");
		
		ps.close();
	}
	
	public void specijalnaPoruka(PrintStream ps, String poruka){
		ps.println("<hr>");
		ps.println("<h1>" + poruka + "</h1>");
		ps.println("</hr>");
	}
	
	public void dodajNovogKorinikaForma(PrintStream ps){
		ps.println("<hr>");
		ps.println("<h1> Dodaj novog korisnika: </h1>");
		ps.println("<form action=\"http://localhost:8080/dodaj\">");
		ps.println("<input type=\"text\" name=\"ime\" />");
		ps.println("<input type=\"submit\" value=\"dodaj\" />");		
		ps.println("</form>");	
		ps.println("<hr>");	
	}
	
	public void pretragaForma(PrintStream ps){
		//forma za pretragu
		ps.println("<hr>");
		ps.println("<h1> Pretraži: </h1>");
		ps.println("<form action=\"http://localhost:8080/pretrazi\">");
		ps.println("<input type=\"text\" name=\"ime\" />");
		ps.println("<input type=\"submit\" value=\"pretraži\" />");		
		ps.println("</form>");	
		ps.println("<hr>");	
	}
	
	public void tabelaKorisnika(PrintStream ps, String select){
		//tabela sa korisnicima i brisi dugmicima		
		ps.println("<hr>");
		ps.println("<h1> Trenutni korisnici: </h1>");	
		if( korisnici.size() != 0 ){
			ps.println("<table border = \"1\" width = \"80%\">");
			for(String korisnik: korisnici){
				ps.println("<tr");	
				if( korisnik.equals(select) ){
					ps.print("style=\"background-color:azure\"");
				}
				ps.print(">");				
				ps.println("<td style=\"text-align:left\" >" + korisnik + "</td>");	
				ps.println("<td style=\"text-align:center\">");	
				ps.println("<form action= \" http://localhost:8080/obrisi \"> ");
				ps.println("<input type=\"submit\" value = \"obriši\" name = \"" + korisnik + "\" >");			
				ps.println("</form>");
				ps.println("</tr>");
			}		
			ps.println("</table>");
		}
		else{
			ps.println("<p> Trenutno nema korisnika. </p>");
		}
		ps.println("<hr>");	
	}
	
	
}
