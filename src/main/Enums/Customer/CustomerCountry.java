package Enums.Customer;

public enum CustomerCountry {

    FRANCE("FR", "France"),UK("UK","United Kingdom");
    // Valeur RCU
    private String rcuValue;
    // Valeur OB
    private String obValue;

    CustomerCountry(String rcuValue, String obValue) {
        this.rcuValue = rcuValue;
        this.obValue = obValue;
    }

    public String getRCUValue() {
        return rcuValue;
    }

    public String getOBValue() {
        return obValue;
    }
}
