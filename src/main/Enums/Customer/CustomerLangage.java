package Enums.Customer;

public enum CustomerLangage {

    FRENCH("fr-FR", "French (France)"),
    UK_ENGLISH("en-GB", "English (United Kingdom)");
    // Valeur RCU
    private String rcuValue;
    // Valeur OB
    private String obValue;

    CustomerLangage(String rcuValue, String obValue) {
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
