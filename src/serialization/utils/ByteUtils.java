package serialization.utils;

import java.io.ByteArrayOutputStream;

public class ByteUtils {

    private ByteUtils() {}

    public static byte[] slice(byte[] src, int from, int to) {
        return slice(src, from, to, 1);
    }

    public static byte[] slice(byte[] src, int from, int to, int step) {
        validateParams(src, from, to);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = from; i < src.length && i < to; i += step) {
            out.write(src[i]);
        }
        return out.toByteArray();
    }

    private static boolean checkBounds(byte[] src, int i) {
        return i >= 0 && i < src.length;
    }

    private static void validateParams(byte[] src, int from, int to) {
        if (from < 0 || to < from) {
            throw new IllegalArgumentException("Incorrect slice params");
        }
    }
}
