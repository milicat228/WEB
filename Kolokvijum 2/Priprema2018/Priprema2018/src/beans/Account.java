package beans;

public class Account {
	private String accountNumber;
	private String accountType;
	private double availableMoney;
	private double reservedMoney;
	private Boolean online;
	private Boolean active = true;

	public Account(String accountNumber, String accountType, double availableMoney, double reservedMoney,
			Boolean online) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.availableMoney = availableMoney;
		this.reservedMoney = reservedMoney;
		this.online = online;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(double availableMoney) {
		this.availableMoney = availableMoney;
	}

	public double getReservedMoney() {
		return reservedMoney;
	}

	public void setReservedMoney(double reservedMoney) {
		this.reservedMoney = reservedMoney;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
