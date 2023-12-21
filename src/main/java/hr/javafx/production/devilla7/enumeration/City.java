package hr.javafx.production.devilla7.enumeration;

public enum City {
    ZAGREB("Zagreb","10000"),
    SPLIT("Split","21000"),
    RIJEKA("Rijeka","51000"),
    OSIJEK("Osijek","31000"),
    KOPRIVNICA("Koprivnica","48000"),
    ANY_CITY("Any city", "");

    private final String name;
    private final String postalCode;

    City(String name, String postalCode) {
        this.name = name;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
