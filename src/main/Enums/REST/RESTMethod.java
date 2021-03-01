package Enums.REST;

public enum RESTMethod {
    POST("POST"), PUT("PUT"), GET("GET");

    // Label
    private String label;

    RESTMethod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
