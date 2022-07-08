package util;

import java.util.Objects;

public class FilePathUtility {
    public static String getResourceFilePath(Class cls, String fileName) {
        return Objects.requireNonNull(cls.getResource(fileName)).getPath().substring(System.getProperty("os.name").contains("Windows") ? 1 : 0);
    }
}
