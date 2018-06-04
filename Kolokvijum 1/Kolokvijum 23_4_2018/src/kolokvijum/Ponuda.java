package kolokvijum;

public class Ponuda {
	private int id;
	private String destinacija;
	private boolean taksa;
	private boolean sezona;
	
	
	public Ponuda(int id, String destinacija, boolean taksa, boolean sezona) {
		super();
		this.id = id;
		this.destinacija = destinacija;
		this.taksa = taksa;
		this.sezona = sezona;
	}
	
	//VAZNO - Generisati equals sa poljem id, jer mora biti jedinstveno
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
		Ponuda other = (Ponuda) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	public int getId() {
		return id;
	}	

	public void setId(int id) {
		this.id = id;
	}
	public String getDestinacija() {
		return destinacija;
	}
	public void setDestinacija(String destinacija) {
		this.destinacija = destinacija;
	}
	public boolean isTaksa() {
		return taksa;
	}
	public void setTaksa(boolean taksa) {
		this.taksa = taksa;
	}
	public boolean isSezona() {
		return sezona;
	}
	public void setSezona(boolean sezona) {
		this.sezona = sezona;
	}
	
	
}
