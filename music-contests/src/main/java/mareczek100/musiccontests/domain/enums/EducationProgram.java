package mareczek100.musiccontests.domain.enums;

public enum EducationProgram {

    FOUR_YEAR("Czteroletni"),
    SIX_YEAR("Sześcioletni");

    private final String yearProgram;


    EducationProgram(String yearProgram) {
        this.yearProgram = yearProgram;
    }

    public String getYearProgram() {
        return yearProgram;
    }
}
