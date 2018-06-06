package beans;

public class Ponuda {
	private String id;
	private String destinacija;
	private Boolean taksa;
	private Boolean sezona;
	
	public Ponuda(String id, String destinacija, Boolean taksa, Boolean sezona) {
		super();
		this.id = id;
		this.destinacija = destinacija;
		this.taksa = taksa;
		this.sezona = sezona;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDestinacija() {
		return destinacija;
	}
	public void setDestinacija(String destinacija) {
		this.destinacija = destinacija;
	}
	public Boolean getTaksa() {
		return taksa;
	}
	public void setTaksa(Boolean taksa) {
		this.taksa = taksa;
	}
	public Boolean getSezona() {
		return sezona;
	}
	public void setSezona(Boolean sezona) {
		this.sezona = sezona;
	}
	
	
	
}
