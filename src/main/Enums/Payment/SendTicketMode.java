package Enums.Payment;

public enum SendTicketMode {
    MAIL_ONLY("Email seulement");

    // Label du mode d'envoi
    private String label;

    SendTicketMode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
