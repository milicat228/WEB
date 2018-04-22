package priprema2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class Korisnici {
	private Map<String, Korisnik> korisnici; //ključ je email, jer je jedinstven
	
	public Korisnici(){
		korisnici = new HashMap<String,Korisnik>();
	}
	
	//dodaje korisnika ukoliko email nije već zauzet
	public boolean dodajKorisnika(Korisnik k){
		if( korisnici.containsKey(k.getEmail()) == false ){
			korisnici.put(k.getEmail(), k);
			return true;
		}
		return false;
	}
	
	//briše korisnika sa izabranim email-om, ako postoji
	public boolean obrisiKorisnika(String email){
		if( korisnici.containsKey(email) ){
			korisnici.remove(email);
			return true;
		}
		return false;
	}
	
	//uređuje postojećeg korisnika
	public boolean urediKorisnika(String stariEmail, Korisnik noviKorisnik){
		//preuzmi starog korisnika - verzija pre uređivanja
		Korisnik stariKorisnik = korisnici.get(stariEmail);		
		korisnici.remove(stariEmail); //mora se izvaditi za slučaj da mejl nije menjan
		
		//pokušaj da dodaš novu verziju korinika
		if( korisnici.containsKey(noviKorisnik.getEmail()) == false ){
			korisnici.put(noviKorisnik.getEmail(), noviKorisnik);
			return true;
		}
		else{
			//ako nova verzija nije moguća, vrati staru
			korisnici.put(stariEmail, stariKorisnik);
			return false;
		}		
	}
	
	//vraća korisnika sa datim mejlom
	public Korisnik nadjiKorisnika(String email){
		return korisnici.get(email);	
	}
	
	//lista sa svim korisnicima
	public List<Korisnik> sviKorisnici(){
		List<Korisnik> ret = new ArrayList<Korisnik>();
		for(Korisnik k:korisnici.values()){
			ret.add(k);
		}
		return ret;
	}
	
	//generise korisnike sa odredjenim filterom 0-svi, 1-Chrome, 2-Firefox
	public List<Korisnik> filterKorisnika(int f){
		List<Korisnik> ret = new ArrayList<Korisnik>();
		for(Korisnik k:korisnici.values()){
			if( f == 0)
				ret.add(k);
			else if( f == 1 && k.getPretrazivac().equals("Chrome") )
				ret.add(k);
			else if( f == 2 && k.getPretrazivac().equals("Firefox"))
				ret.add(k);
		}
		
		//sortiraj liste po filteru
		if( f == 1 ){
			ret.sort(Comparator.comparing(Korisnik::getKredit));
		}
		
		if( f == 2 ){
			ret.sort(Comparator.comparing(Korisnik::getKredit));
			Collections.reverse(ret);
		}
		
		return ret;
	}
	
}
