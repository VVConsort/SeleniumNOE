package Enums.Customer;

public enum CustomerTitle {

    MR("1", "MONSIEUR"), MRS("2", "MADAME"), COMPAGNY("4", "SOCIETE");

    // Valeur RCU
    private String rcuValue;

    // Valeur OB
    private String obValue;

    CustomerTitle(String rcuValue, String obValue) {
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
