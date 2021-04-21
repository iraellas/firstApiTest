import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static String getJsonFromFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));

    }
}
