package restassuredTests.entities;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"token"})

public class BookingToken {
    private Token token = null;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token= token;
    }
}
