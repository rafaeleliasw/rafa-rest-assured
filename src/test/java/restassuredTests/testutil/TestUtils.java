package restassuredTests.testutil;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestUtils {

    public static String getEncodedToken(String clientid, String clientSecret){
        String idSecret = clientid +":"+clientSecret;
        byte[] bytesEncoded = Base64.encodeBase64(idSecret.getBytes());
        return new String(bytesEncoded);
    }

    public static Map<String, String> parseUrlFragment(String url) {

        Map<String, String> output = new LinkedHashMap<>();
        String[] keys = url.split ("&");
        for (String key : keys) {
            String[] values = key.split ("=");
            output.put (values[0], (values.length > 1 ? values[1] : ""));
        }
        return output;
    }

    public static String generateStringFromResource(String path) throws IOException {

        return new String(Files.readAllBytes(Paths.get(path)));

    }

}
