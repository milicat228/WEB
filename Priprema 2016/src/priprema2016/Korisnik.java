package priprema2016;

public class Korisnik {
	private String ime = "";
	private String prezime = "";
	private String email = "";
	private String grad = "";
	private double kredit = 0;
	private String pretrazivac = "";
	
	//SVE ISPOD JE GENERISANO!
	public Korisnik(String ime, String prezime, String email, String grad, double kredit, String pretrazivac) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.grad = grad;
		this.kredit = kredit;
		this.pretrazivac = pretrazivac;
	}
	
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public double getKredit() {
		return kredit;
	}

	public void setKredit(double kredit) {
		this.kredit = kredit;
	}

	//korisnici su isti ako su im isti mejlovi - tako ce mejlovi morati biti jedinstveni
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Korisnik other = (Korisnik) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		return true;
	}

	public String getPretrazivac() {
		return pretrazivac;
	}

	public void setPretrazivac(String pretrazivac) {
		this.pretrazivac = pretrazivac;
	}
}
