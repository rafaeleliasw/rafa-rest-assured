package restassured.spotify;

import org.apache.commons.codec.binary.Base64;

public class Token {

    public static String getEncodedToken(String clientid, String clientSecret){
        String idSecret = clientid +":"+clientSecret;
        byte[] bytesEncoded = Base64.encodeBase64(idSecret.getBytes());
        return new String(bytesEncoded);
    }
}
