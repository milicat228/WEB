package priprema2016;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Jednostavan web server
 */
public class httpd {
	
	private static Korisnici korisnici= new Korisnici();
	private static HTMLGenerator generator = new HTMLGenerator();
	private static String trenutniPretrazivac = "null";
	
	public static void main(String args[]) throws IOException {
		int port = 8080;

		//pokretanje servera
		@SuppressWarnings("resource")
		ServerSocket srvr = new ServerSocket(port);
		System.out.println("Server pokrenut na portu: " + port);	
		Socket skt = null;

		while (true) {
			try {
				//prihavatenje konekcije
				skt = srvr.accept();
				
				//primanje zahteva
				String resource = getResource(skt.getInputStream());

				if (resource.equals(""))
					resource = "aplikacija";			
				
				//slanje odgovora
				sendResponse(resource, skt.getOutputStream());				
				
				//zatvaranje konekcije
				skt.close();
				skt = null;
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

		String[] tokens = s.split(" ");

		// prva linija HTTP zahteva: METOD /resurs HTTP/verzija
		// obradjujemo samo GET metodu
		String method = tokens[0];
		if (!method.equals("GET")) {
			return "";
		}

		String rsrc = tokens[1];

		// izbacimo znak '/' sa pocetka
		rsrc = rsrc.substring(1);
		
		// ignorisemo ostatak zaglavlja
		String s1;
		while (!(s1 = dis.readLine()).equals("")){
			if( s1.contains("User-Agent")){
				if( s1.contains("Firefox") || s1.contains("firefoxversion") ){
					generator.boje = 0;
					trenutniPretrazivac = "Firefox";
				}
				else if( s1.contains("Chrome")){
					generator.boje = 0;
					trenutniPretrazivac = "Chrome";
				}
				else{
					generator.boje = 0;
					trenutniPretrazivac = "";
				}
			}				
		}			
		
		return rsrc;
	}

	static void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os, true);		
		String tokens[]  = resource.split("\\?");
		
		if( tokens[0].equals("aplikacija") ){
			//svi zahtevi koje obrađujemo
			HashMap<String, String> podaci = getParameter(resource);
			//treba saznati koji zahtev se obrađuje -> koje dugme je pritisnuto			
			if( podaci.containsKey("dodaj") ){
				//dodaje se novi korisnik
				try{
					String ime = URLDecoder.decode(podaci.get("Ime"));
					String prezime = URLDecoder.decode(podaci.get("Prezime"));
					String email = URLDecoder.decode(podaci.get("Email"));
					//mejl mora biti popunjen
					if( email.isEmpty() == true ){
						generator.poruka(ps, "Niste uneli sve potrebne podatke.");
						return;
					}
					String grad = URLDecoder.decode(podaci.get("grad"));
					double kredit = Double.parseDouble(podaci.get("Kredit"));
					Korisnik k = new Korisnik(ime, prezime, email, grad, kredit, trenutniPretrazivac);
					if( korisnici.dodajKorisnika(k) ){
						generator.generisiHTMLAplikacije(ps, korisnici, null);
					}
					else{
						//dodavanje nije uspesno, jer korisnik vec postoji
						generator.poruka(ps, "Korisnik već postoji.");
					}				
				}
				catch( Exception e){
					//hvatanje null excption-a, ako korisnik nije undeo neki od podataka
					generator.poruka(ps, "Niste uneli sve potrebne podatke.");
				}				
			}
			else if( podaci.containsKey("obrisiIzabranog")){
				//brisanje korisnika iz tabele, dugmetom pored ispisa u tabeli
				String email = URLDecoder.decode(podaci.get("skriveniEmail"));
				//ova glupost je da bi mogli samo uneti mejl u formu gore i kliknuti brisi
				if( email.isEmpty() ){
					if( podaci.containsKey("Email") ){
						email = URLDecoder.decode(podaci.get("Email"));
					}
				}
				if( korisnici.obrisiKorisnika(email)){				
					generator.generisiHTMLAplikacije(ps, korisnici, null);
				}
				else{
					generator.poruka(ps, "Neuspešno brisanje.");
				}				
			}
			else if( podaci.containsKey("izmeniIzabranog")){
				String email = URLDecoder.decode(podaci.get("skriveniEmail"));
				Korisnik k = korisnici.nadjiKorisnika(email);
				generator.generisiHTMLAplikacije(ps, korisnici, k);
			}
			else if( podaci.containsKey("izmeni")){
				try{
					String ime = URLDecoder.decode(podaci.get("Ime"));
					String prezime = URLDecoder.decode(podaci.get("Prezime"));
					String email = URLDecoder.decode(podaci.get("Email"));
					String stariEmail = URLDecoder.decode(podaci.get("skriveniEmail"));
					String grad = URLDecoder.decode(podaci.get("grad"));
					double kredit = Double.parseDouble(podaci.get("Kredit"));						
					Korisnik kStari = korisnici.nadjiKorisnika(stariEmail);
					Korisnik k = new Korisnik(ime, prezime, email, grad, kredit, kStari.getPretrazivac());				
					if( korisnici.urediKorisnika(stariEmail, k) ){						
						generator.generisiHTMLAplikacije(ps, korisnici, null);
					}
					else{
						//dodavanje nije uspesno, jer korisnik vec postoji
						generator.poruka(ps, "Izmene nisu moguće. Proverite unete podatke.");
					}				
				}
				catch( Exception e){
					//hvatanje null excption-a, ako korisnik nije undeo neki od podataka
					generator.poruka(ps, "Niste uneli sve potrebne podatke.");
				}
				
			}
			else if(podaci.containsKey("filter")){
				String value = URLDecoder.decode(podaci.get("izabraniFilter"));
				if( value.equals("Prikaži sve korisnike")){					
					generator.filter = 0;	
				}
				else if( value.equals("Firefox")){					
					generator.filter = 2;	
				}
				else{		
					generator.filter = 1;					
				}
				generator.generisiHTMLAplikacije(ps, korisnici, null);
			}
			else{
				//prikazi samo glavnu stranu aplikacije
				generator.generisiHTMLAplikacije(ps, korisnici, null);
			}
		}		
		else{
			//zahtevi koje ne obrađujemo
			ps.print("HTTP/1.0 404 Not found\r\n\r\n");			
		}
		
		ps.close();
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
