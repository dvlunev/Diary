public class StringUtils {

    private StringUtils() {

    }

    public static boolean isNullOrEmptyOrBlank(String s) {
        return s==null || s.isEmpty() || s.isBlank();
    }
}
