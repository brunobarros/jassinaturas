package sdk.jassinaturas.clients.attributes;

public class VencimentoBoleto {
    private String day;
    private String month;
    private String year;

    public VencimentoBoleto(String day, String month, String year) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "VencimentoBoleto [day=" + day + ", month=" + month + ", year=" + year + "]";
    }

}
