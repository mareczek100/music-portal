package mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support;

public interface ControllerRestSupport {
    String FIND_ALL_INSTRUMENTS = "/instruments";
    String FIND_ALL_CITIES = "/cities";
    String FIND_ALL_COMPETITIONS_PAGEABLE = "/competitions/all/{currentPage}";
    String FIND_ALL_COMPETITIONS = "/competitions/all";
    String FIND_AVAILABLE_COMPETITIONS_BY_FILTERS = "/competitions/available/filters";
    String FIND_AVAILABLE_COMPETITIONS_BY_INSTRUMENT = "/competitions/available/instrument";
    String FIND_FINISHED_COMPETITIONS_BY_FILTERS = "/competitions/finished";
    String FIND_FINISHED_COMPETITIONS = "/competitions/finished/all";
    String FIND_ALL_TEACHER_STUDENTS = "/students";
    String FIND_AVAILABLE_CLASS_LEVELS = "/classes";
    String FIND_TEACHER_APPLICATIONS = "/competition/applications";
    String ANNOUNCE_STUDENT_TO_COMPETITION = "/competition/announce";
    String ANNOUNCE_STUDENT_UPDATE = "/competition/update/{applicationFormId}";
    String ANNOUNCE_STUDENT_CANCEL = "/competition/cancel";
    String CHECK_RESULT = "/results";
}