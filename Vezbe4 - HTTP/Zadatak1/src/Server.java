import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.time.LocalDateTime;

public class Server {
	private static Korisnici korisnici = new Korisnici();

	public static void main(String[] args) {
		//aktiviranje servera
		int port = 8080;
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("Server pokrenut: " + LocalDateTime.now());
		} catch (IOException e) {			
			e.printStackTrace();
		}
		Socket socket = null;
		
		
		//cekanje zahteva
		while( true ){
			try {
				//prihvatanje konekcije
				socket = server.accept();
				
				//obrada zahteva
				processRequest(socket.getInputStream(), socket.getOutputStream());
				
				//zatvaranje konekcije
				socket.close();
				socket = null;
			} catch (IOException e) {				
				e.printStackTrace();
			}			
			
		}	

	}
	
	
	public static void processRequest(InputStream is, OutputStream os){
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
				
		try {
			//sluzi da resi onaj excpetion Socket Connection TimeOut
			if( br.ready() == false )
				return;
			
			//request ima oblik kao: GET /dodaj?ime=milica+todorovic HTTP/1.1		
			String request = br.readLine();
			String[] parts = request.split(" ");
			
			parts[1] = parts[1].replace("/", "");
			//tokens[0] - sta treba da se uradi, tokens[1] - parametri
			System.out.println(parts[1]);
			String[] tokens = parts[1].split("\\?"); //uzvicnik je specijalni karakter
			
			if( tokens[0].equals("dodaj") ){
				addUserRequest(os, tokens[1]);
			}
			else if( tokens[0].equals("obrisi") ){
				deleteUserRequest(os, tokens[1]);
			}
			else if( tokens[0].equals("pretrazi") ){
				searchUserRequest(os, tokens[1]);
			}
			else if( tokens[0].equals("trenutni") ){
				korisnici.izlistajKorisnike(os, null, null);
			}
			else{
				PrintStream ps = new PrintStream(os);
				ps.print("HTTP/1.0 404 File not found\r\n"
						+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Ne postoji trazena stranica." + "</b>");
			}
				
			
		} catch (IOException e) {			
			e.printStackTrace();
		}			
	}
	
	
	public static void addUserRequest(OutputStream os, String parameters){
		//parameters ima oblik kao npr: ime=milica+todorovic
		String[] token = parameters.split("=");		
		String ime = URLDecoder.decode(token[1]);			
		if( korisnici.dodajKorisnika(ime) == true ){
			korisnici.izlistajKorisnike(os, "Dodan korisnik " + ime +".", ime);
		}
		else{
			korisnici.izlistajKorisnike(os, "Korisnik " + ime + " vec postoji.", null);
		}		
	}
	
	public static void deleteUserRequest(OutputStream os, String parameters){
		//parameters ima oblik kao npr: Sonja+Savic=obrisi
		String[] token = parameters.split("=");		
		String ime = URLDecoder.decode(token[0]);
		if( korisnici.brisiKorisnika(ime) == true ){
			korisnici.izlistajKorisnike(os, "Izbrisan korisnik " + ime + ".", null);
		}
		else{
			korisnici.izlistajKorisnike(os, "Korisnik " + ime + " ne postoji. Brisanje nemoguće.", null);
		}
		
	}
	
	public static void searchUserRequest(OutputStream os, String parameters){
		//parameters ima oblik kao npr: ime=Jelena+Surlan
		String[] token = parameters.split("=");	
		String ime = URLDecoder.decode(token[1]);	
		if( korisnici.pretraziKorisnika(ime) == true ){
			korisnici.izlistajKorisnike(os, "Pronađen korisnik " + ime + ".", ime);
		}
		else{
			korisnici.izlistajKorisnike(os, "Korisnik " + ime + " ne postoji.", null);
		}
	}
}
