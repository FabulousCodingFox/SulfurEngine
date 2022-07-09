package util;

import java.io.*;

public class FileUtils {
    public static String readFile(String path) throws IOException {
        return getFullStringOfBufferedReader(new BufferedReader(new FileReader(path)));
    }

    public static String getFullStringOfBufferedReader(BufferedReader br) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {resultStringBuilder.append(line).append("\n");}
        return resultStringBuilder.toString();
    }
}
