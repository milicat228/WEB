package kolokvijum;

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
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Jednostavan web server
 */
public class httpd {
	private static Ponude ponude = new Ponude();
	private static HTMLGenerator generator = new HTMLGenerator();
	
	public static void main(String args[]) throws IOException {
		int port = 90;

		@SuppressWarnings("resource")
		ServerSocket srvr = new ServerSocket(port);

		System.out.println("httpd running on port: " + port);		

		Socket skt = null;

		while (true) {
			try {
				skt = srvr.accept();				

				String resource = getResource(skt.getInputStream());			
				sendResponse(resource, skt.getOutputStream());
				
				skt.close();
				skt = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	static String getResource(InputStream is) throws IOException {
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		String s = "";
		
		try{
			s = dis.readLine();
		}
		catch( java.net.SocketException e){
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
			return null;
		}

		String rsrc = tokens[1];

		// izbacimo znak '/' sa pocetka
		rsrc = rsrc.substring(1);
		
		// ignorisemo ostatak zaglavlja
		String s1;
		while (!(s1 = dis.readLine()).equals("")){
			//System.out.println(s1);
		}			

		return rsrc;
	}

	static void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os, true);
		HashMap<String, String> parametri = getParameter(resource);
		
		if( parametri.containsKey("request") ){
			String zahtev = parametri.get("request");
			
			if( zahtev.equals("dodaj") ){
				int id = Integer.parseInt(parametri.get("id"));
				String destinacija = URLDecoder.decode(parametri.get("destinacija"));
				boolean taksa = false;
				if( parametri.containsKey("taksa") )
					taksa = true;
				boolean sezona = false;
				if( parametri.containsKey("sezona"))
					sezona = true;
				
				Ponuda p = new Ponuda(id, destinacija, taksa, sezona);
				
				if( ponude.dodajPonudu(p) == true ){
					generator.glavnaStranaHTML(ponude.getPonude(), ps);
				}
				else{
					generator.porukaHTML(ps, "Ponuda sa id[" + id + "] vec postoji." );					
				}					
			}
			else if(zahtev.equals("filter")){
				boolean taksa = false;
				if( parametri.containsKey("taksa") )
					taksa = true;
				boolean sezona = false;
				if( parametri.containsKey("sezona"))
					sezona = true;
				generator.rezultatFiltera(ponude, ps, taksa, sezona);
			}
			else if(zahtev.equals("")){
				generator.glavnaStranaHTML(ponude.getPonude(), ps);			
			}
			else{
				ps.print("HTTP/1.0 404 File not found\r\n");
				ps.print("Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Not found.");
			}
			
		}
		else{
			ps.print("HTTP/1.0 404 File not found\r\n");
			ps.print("Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Not found.");
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
