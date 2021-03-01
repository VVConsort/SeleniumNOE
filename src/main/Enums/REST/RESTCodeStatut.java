package Enums.REST;

public enum RESTCodeStatut {
    OK(200);

    // Code
    private int code;

    RESTCodeStatut(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
