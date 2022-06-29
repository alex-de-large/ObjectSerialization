import com.alex.serialization.JsonFormatter;
import com.alex.serialization.ObjectSerializer;
import test.Header;
import test.Response;
import test.TestFormatter;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        ObjectSerializer os = new ObjectSerializer.Builder()
                .setFormatter(Header.class, new JsonFormatter(StandardCharsets.UTF_8))
                .setFormatter(Response.class, new TestFormatter())
                .build();
        Response response = new Response(
                new Header("f1", "f2"),
                "f3"
        );

        System.out.println(new String(os.serialize(response), StandardCharsets.UTF_8));
    }

//    SignOn msg = new SignOn("48595406","JNE", 1234, "1.0");
//    byte[] signOnResponseRaw = { 0x31, 0x2C, 0x31, 0x2C, 0x31, 0x2C, 0x31, 0x2C, 0x31, 0x2C, 0x31, 0x2C, 0x31, 0x2C, 0x31, 0x2C, 0x31, 0x2C };
//
//    ObjectSerializer os = new ObjectSerializer.Builder().setDefaultFormatter(new MessageFormatter()).build();
//    ObjectSerializer os1 = new ObjectSerializer.Builder().setDefaultFormatter(new JsonFormatter()).build();
//
//    System.out.println(new String(os.serialize(msg), StandardCharsets.US_ASCII));
//    System.out.println(new String(os1.serialize(msg), StandardCharsets.US_ASCII));
//    System.out.println("---------------------------------");
//
//    Serdes s = new Serdes();
//    System.out.println(Arrays.toString(signOnResponseRaw));
//    System.out.println(s.deserializer().deserialize(signOnResponseRaw, SignOnResponse.class));
}
