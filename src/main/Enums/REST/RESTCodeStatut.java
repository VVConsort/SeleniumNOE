package Enums.REST;

public enum RESTCodeStatut {
    OK(200),OK_NO_CONTENT(204);

    // Code
    private int code;

    RESTCodeStatut(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
