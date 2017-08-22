package sdk.jassinaturas.clients.attributes;

public class VencimentoBoleto {
    private int day;
    private int month;
    private int year;

    public VencimentoBoleto(int day, int month, int year) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "VencimentoBoleto [day=" + day + ", month=" + month + ", year=" + year + "]";
    }

}
