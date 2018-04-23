package kolokvijum;

import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HTMLGenerator {
	
	//generise zaglavlje HTML strane
	private void head ( PrintStream ps ){
		ps.print("HTTP/1.0 200 OK\r\n\r\n");
		
		//zaglavlje
		ps.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		ps.println("<html>");
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		ps.println("<title>Ponude</title>");
		ps.println("</head>");
		
	}

	//generise HTML stranu sa ispisanom porukom
	public void porukaHTML( PrintStream ps, String poruka ){
		head(ps);
		ps.println("<body>");
		ps.println("<h1>" + poruka + "</h1>");		
		ps.println("</body>");
		ps.println("</html>");
	}
	
	//generise tabelu - u zeleno se boje vrste samo glavne tabele, filtrirane se ne boje
	private void tabelaHTML(List<Ponuda> ponude, PrintStream ps, boolean bojiZeleno, String naslovTable){
		ps.println("<table border = \"1\">");
		//glavni naslov
		ps.println("<tr>");
		ps.println("<td colspan = \"3\" align = \"center\">" + naslovTable + "</td>");
		ps.println("</tr>");
		//naslovi kolona
		ps.println("<tr>");
		ps.println("<th align = \"center\"> Id: </th>");
		ps.println("<th align = \"center\"> Destinacija </th>");
		ps.println("<th align = \"center\"> Taksa </th>");
		ps.println("</tr>");
		
		//redovi
		for(Ponuda p:ponude){
			if( bojiZeleno == true && p.isSezona() == true )
				ps.println("<tr style = \"background-color:green\">");
			else
				ps.println("<tr>");
			
			ps.println("<td align = \"center\">" + p.getId() + "</td>");
			ps.println("<td align = \"center\">" + p.getDestinacija() + "</td>");
			
			if( p.isTaksa() == true )
				ps.println("<td align = \"center\"> DA </td>");
			else
				ps.println("<td align = \"center\"> NE </td>");
			
			ps.println("</tr>");
		}
		
		ps.println("</table>");
	}
	
	//generise filter za ispod glavne tabele
	private void filterHTML( PrintStream ps ){
		ps.println("<form action=\"http://localhost:90/filter\">");
		ps.println("<table>");
		ps.println("<tr>");
		ps.println("<td> <input type=\"checkbox\" name=\"sezona\" /> </td>");	
		ps.println("<td> U sezoni </td>");
		ps.println("<td> <input type=\"checkbox\" name=\"taksa\" /> </td>");
		ps.println("<td> Placa se boravisna taksa </td>");
		ps.println("<td> <input type=\"submit\" value=\"Filtriraj\" /> </td>");
		ps.println("</tr>");
		ps.println("</table>");
		ps.println("</form>");
	}
	
	//generise osnovni oblik strane
	public void glavnaStranaHTML(List<Ponuda> ponude,PrintStream ps){
		head(ps);
		tabelaHTML(ponude, ps, true, "Ponude");
		filterHTML(ps);
		//link ka stranici za dodavanje (pisalo je bas link ka statickom html)
		Path putanja = Paths.get("");
		String dodaj = putanja.toAbsolutePath() + "/dodaj.html";
		ps.println("<a href=\"" + dodaj + "\"> Dodaj novu ponudu </a>");
		ps.println("</body>");
		ps.println("</html>");
	}
	
	//ponude su sve ponude, ovde cemo filtrirati tek
	public void rezultatFiltera(Ponude ponude, PrintStream ps, boolean taksa, boolean sezona){
		List<Ponuda> filtrirane = ponude.filtrirajPonude(taksa, sezona);
		
		if( filtrirane.isEmpty() == true ){
			//da, trazila se razlicita ponuda za svaku
			if( taksa == true && sezona == true){
				porukaHTML(ps, "<b> Ne postoje ponude u sezoni sa boravisnom taksom. </b>");				
			}
			else if( taksa == true && sezona == false ){
				porukaHTML(ps, "<b> Ne postoje ponude van sezone sa boravisnom taksom. </b>");				
			}
			else if( taksa == false && sezona == true ){
				porukaHTML(ps, "<b> Ne postoje ponude u sezoni bez boravisne takse. </b>");				
			}
			else{
				porukaHTML(ps, "<b> Ne postoje ponude van sezone bez boravisne takse. </b>");				
			}
		}
		else{
			//da i naslov je morao da bude specijalan u tabeli 
			String naslov = "";
			if( taksa == true && sezona == true){
				naslov = "Ponude u sezoni sa boravisnom taksom";				
			}
			else if( taksa == true && sezona == false ){
				naslov = "Ponude van sezone sa boravisnom taksom";			
			}
			else if( taksa == false && sezona == true ){
				naslov = "Ponude u sezoni bez boravisne takse";			
			}
			else{
				naslov = "Ponude van sezone bez boravisne takse";				
			}
			
			//generisanje tabele
			tabelaHTML(filtrirane, ps, false, naslov);
		}
		
		ps.println("</body>");
		ps.println("</html>");
		
	}
	
}
