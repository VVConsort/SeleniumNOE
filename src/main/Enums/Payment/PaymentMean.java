package Enums.Payment;


/**
 * Moyen de paiements
 */
public enum PaymentMean {
    CREDIT_CARD("Carte de crédit"), CASH("Espèces"), CREDIT_NOTE("Avoir"),VOUCHER("Bon d'achat"),
    MONTHLY_MAINTENANCE("Entretien mensualisé");

    // Label du mode de paiement
    private String label;

    PaymentMean(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
