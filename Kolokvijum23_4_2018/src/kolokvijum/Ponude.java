package kolokvijum;

import java.util.ArrayList;
import java.util.List;

public class Ponude {
	private List<Ponuda> ponude;
	
	public List<Ponuda> getPonude() {
		return ponude;
	}

	public Ponude(){
		ponude = new ArrayList<Ponuda>();
	}
	
	//dodavanje nove ponude ukoliko vec ne postoji
	public boolean dodajPonudu(Ponuda p){
		if( ponude.contains(p) == false ){
			ponude.add(p);
			return true;
		}
		return false;
	}
	
	//vraca samo onde ponude koje odgovaraju filteru
	public List<Ponuda> filtrirajPonude(boolean taksa, boolean sezona){
		List<Ponuda> ret = new ArrayList<>();
		for(Ponuda p : ponude){
			if( p.isSezona() == sezona && p.isTaksa() == taksa ){
				ret.add(p);
			}
		}
		return ret;
	}
}
