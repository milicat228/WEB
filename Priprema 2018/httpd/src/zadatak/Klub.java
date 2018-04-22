package zadatak;

public class Klub {
	private String naziv = null;
	private String grad = null;
	private boolean aktivan = false;
	private int bodovi = 0;
	
	public Klub(String naziv, String grad, boolean aktivan){
		this.naziv = naziv;
		this.grad = grad;
		this.aktivan = aktivan;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public boolean isAktivan() {
		return aktivan;
	}
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	public int getBodovi() {
		return bodovi;
	}
	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}
	public void dodajBodove(int bodovi){
		this.bodovi += this.bodovi + bodovi;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		Klub k = (Klub) arg0;
		if ( naziv.equals(k.naziv) && grad.equals(k.grad))
			return true;
		else
			return false;
	}
	
	
}
