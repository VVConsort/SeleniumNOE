package Enums.Customer;

public enum CustomerType {

    PERSON("PERSON", "PERSONNE"), BUSINESS("BUSINESS", "ENTREPRISE"), FLEET("FLEET", "FLOTTE");

    // Valeur RCU
    private String rcuValue;

    // Valeur OB
    private String obValue;

    CustomerType(String rcuValue, String obValue) {
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
