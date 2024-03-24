package tw.ch1ck3n.bettertp.utils;

public class StringUtils {

    public static String getString1(float f) {
        return String.format("%.1f", f);
    }

    public static String getString3(double d) {
        return String.format("%.3f", d);
    }
}
