package zadatak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Jednostavan web server
 */

@SuppressWarnings("deprecation")
public class httpd {
	private static Klubovi klubovi= new Klubovi();
	
	public static void main(String args[]) throws IOException {
		
		//pokretanje servera
		int port = 8080;		
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server pokrenut.");
		Socket socket = null;

		while (true) {
			try {
				//otvaranje konekcije
				socket = server.accept();				
			
				//citanje zahteva
				String resource = getResource(socket.getInputStream());
								
				if( resource == null ){
					continue;
				}
				else if( resource.equals("IEne")){
					PrintStream ps = new PrintStream(socket.getOutputStream(), true);
					isisiPoruku(ps, "Aplikacije ne podrzava vas trenutni preglednik");
					ps.close();
				}
				else if (resource.equals("")){
					resource = "index.html";
				}									
				else{
					sendResponse(resource, socket.getOutputStream());
				}
				
				//zatvaranje konekcije
				socket.close();
				socket = null;
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}
	}

	static String getResource(InputStream is) throws IOException {
		
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		String s = null;
		
		try{ //resi onaj exception
			s = dis.readLine();
		}
		catch(java.net.SocketException e){
			return "";
		}
		
		if (s == null)
			s = "";

		System.out.println(s);
		//oblik ce biti kao: GET /dodaj?naziv=FK+Partizan&grad=Novi+Sad&aktivan=on HTTP/1.1
		String[] tokens = s.split(" ");
		
		String method = tokens[0];
		if (!method.equals("GET")) {
			return null;
		}

		String rsrc = tokens[1];
		// izbacimo znak '/' sa pocetka
		rsrc = rsrc.substring(1);
		
		// ignorisemo ostatak zaglavlja
		String s1;
		while (!(s1 = dis.readLine()).equals("")){
			String[] parts = s1.split(":");
			if( parts[0].equals("User-Agent")){
				if( parts[0].contains("Trident")){
					return "IEne";
				}
			}
		}

		return rsrc;
	}

	static void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os, true); //true znaci da ce se flushovati posle svakog ispisa
		
		String[] tokens = resource.split("\\?"); //upitnk je specijalni znak, kao i svi ostali regex
		
		if( tokens[0].equals("dodaj")){
			dodajKlubRequest(ps, tokens[1]);
		}
		else if( tokens[0].equals("prikaziVodeci") ){
			klubovi.HTMLVodeci(ps);
		}
		else if( tokens[0].equals("unesiBodove")){
			dodajBodove(ps, tokens[1]);
		}
		else if( tokens[0].equals("uredi")){
			klubovi.izmeniKlubHTML(ps, Integer.parseInt(tokens[1]));
		}		
		else{
			//ovde ce uci urediOk/0 i urediOk/indeks generalno
			String[] parts = tokens[0].split("\\/");
			if( parts[0].equals("urediOk")){
				int indeks = Integer.parseInt(parts[1]);
				urediKlub(ps, indeks, tokens[1]);
			}
			else{
				//ovde ce npr da zavrsi Chromov favicon
				isisiPoruku(ps, "Stranica nije pronađena");
			}
		}
		
		
		ps.close();
	}
	
	//uredjuje klub
	static void urediKlub(PrintStream ps, int indeks, String parameters){
		//parsiranje parametara
		HashMap<String,String> parsedParemeters = getParameter(parameters);
		String naziv = URLDecoder.decode((String)parsedParemeters.get("naziv"));
		String grad = URLDecoder.decode((String)parsedParemeters.get("grad"));		
		boolean aktivan = false;
		if( parsedParemeters.containsKey("aktivan") ){
			aktivan = true;
		}		
		
		klubovi.setKlub(indeks, naziv, grad, aktivan);		
		//generisanje odgovora
		klubovi.HTML(ps);
	}
	
	//obradjuje dodelu bodova klubu
	static void dodajBodove(PrintStream ps, String parameters){
		HashMap<String,String> parsedParemeters = getParameter(parameters);
		int index = Integer.parseInt((String)parsedParemeters.get("klub"));		
		int bodovi = Integer.parseInt((String)parsedParemeters.get("bodovi"));
		klubovi.setBodove(index, bodovi);
		//generisanje odgovora
		klubovi.HTML(ps);
	}
	
	
	//obradjuje dodavanje novog kluba
	static void dodajKlubRequest(PrintStream ps, String parameters){
		//parsiranje parametara
		HashMap<String, String> parsedParemeters = getParameter(parameters);	
		String naziv = URLDecoder.decode((String)parsedParemeters.get("naziv"));
		String grad = URLDecoder.decode((String)parsedParemeters.get("grad"));		
		boolean aktivan = false;
		if( parsedParemeters.containsKey("aktivan") ){
			aktivan = true;
		}		
		
		Klub k = new Klub(naziv, grad, aktivan);
		if( klubovi.dodajKlub(k) ){
			//generisanje odgovora
			klubovi.HTML(ps);	
		}
		else{
			isisiPoruku(ps, "Ne mozete dodati ponovo isti klub.");
		}
		
			
	}
	
	static void isisiPoruku(PrintStream ps, String poruka){
		//ispis zaglavlja
		ps.print("HTTP/1.1 200 OK\r\n\r\n");
		ps.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		ps.println("<html>");
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		ps.println("<title>Pregled klubova</title>");
		ps.println("</head>");
		ps.println("<body>");
		//poruka						
		ps.println("<p style = \"border:1px solid black; padding:10px color:red\">");		
		ps.println("<b>" + poruka +  "</b>");
		ps.println("</p>");					
		//kraj HTML dokumenta
		ps.println("</body>");
		ps.println("</html>");
	}
		
	
	/**
	 * Funkcija koja prima string zahtev oblika: <br/>
	 *  {@literal "resurs?parametar1=vrednost1&parametar2=vrednost2&...&parametarN=vrednostN"} <br/>
	 * I vraća HashMap objekat sa Key - Value parovima:
	 * <pre>
	 * 	{ 
	 * 		request : resurs,
	 * 		parametar1 : vrednost1,
	 * 		parametar2 : vrednost2,
	 * 		...
	 * 		parametarN : vrednostN
	 *        } 
	 * </pre>
	 * @param requestLine - String oblika {@literal "resurs?parametar1=vrednost1&parametar2=vrednost2&...&parametarN=vrednostN"}
	 * @return - HashMap&lt;String, String&gt; {parametar: vrednost}
	 */
	static HashMap<String, String> getParameter(String requestLine) {
		HashMap<String, String> retVal = new HashMap<String, String>();

		String request = requestLine.split("\\?")[0];
		retVal.put("request", request);
		String parameters = requestLine.substring(requestLine.indexOf("?") + 1);
		StringTokenizer st = new StringTokenizer(parameters, "&");
		while (st.hasMoreTokens()) {
			String key = "";
			String value = "";
			StringTokenizer pst = new StringTokenizer(st.nextToken(), "=");
			key = pst.nextToken();
			if (pst.hasMoreTokens())
				value = pst.nextToken();

			retVal.put(key, value);
		}

		return retVal;
	}
}
