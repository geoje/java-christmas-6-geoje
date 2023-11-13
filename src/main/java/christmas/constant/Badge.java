package christmas.constant;

public enum Badge {
    NOTHING("없음"),
    STAR("별"),
    TREE("트리"),
    SANTA("산타");

    private final String message;

    Badge(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
