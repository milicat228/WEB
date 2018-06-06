package dao;

import java.util.HashMap;

import beans.Ponuda;

public class PonudeDAO {
	
	private HashMap<String, Ponuda> ponude = new HashMap<>();

	public HashMap<String, Ponuda> getPonude() {
		return ponude;
	}
	
	public boolean dodajPonudu(Ponuda p){
		if( ponude.containsKey(p.getId()) == false ){
			ponude.put(p.getId(), p);
			return true;
		}
		return false;
	}

	public boolean ukloniPonudu(int id){
		if( ponude.containsKey(id) ==  true){
			ponude.remove(id);
			return true;
		}
		return false;
	}
	
	public HashMap<String, Ponuda> filtriraj(Boolean sezona, Boolean taksa){
		HashMap<String, Ponuda> ret = new HashMap<String, Ponuda>();
		for(Ponuda p : ponude.values()){
			if( p.getSezona() == sezona && p.getTaksa() == taksa ){
				ret.put(p.getId(), p);
			}
		}
		return ret;
	}
}
