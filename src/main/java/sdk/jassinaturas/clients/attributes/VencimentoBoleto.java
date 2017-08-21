package sdk.jassinaturas.clients.attributes;

public class VencimentoBoleto {
    private String day;
    private String month;
    private int year;

    public String getDay() {
        return day;
    }

    public String getMonth() {
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
