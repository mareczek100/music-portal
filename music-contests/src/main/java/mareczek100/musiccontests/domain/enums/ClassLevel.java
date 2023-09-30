package mareczek100.musiccontests.domain.enums;

public enum ClassLevel {
    FIRST("Pierwsza"),
    SECOND("Druga"),
    THIRD("Trzecia"),
    FOURTH("Czwarta"),
    FIFTH("Piąta"),
    SIXTH("Szósta");
    private final String classLevel;
    ClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }
    public String getClassLevel() {
        return classLevel;
    }
}
