package mareczek100.musiccontests.domain.enums;

public enum Degree {
    FIRST("Pierwszy"),
    SECOND("Drugi");
    private final String degree;

    Degree(String degree) {
        this.degree = degree;
    }
    public String getDegree() {
        return degree;
    }
}
