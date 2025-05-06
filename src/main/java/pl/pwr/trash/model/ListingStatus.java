package pl.pwr.trash.model;

public enum ListingStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    SOLD("sold"),;

    private final String status;
    ListingStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
