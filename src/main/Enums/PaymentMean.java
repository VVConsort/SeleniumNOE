package Enums;

/**
 * Moyen de paiements
 */
public enum PaymentMean {
    CREDIT_CARD("Carte de crédit"), CASH("Espèces"), CREDIT_NOTE("Avoir");

    // Label du mode de paiement
    private String label;

    private PaymentMean(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
