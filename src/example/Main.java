package example;

import com.alex.serialization.JsonFormatter;
import com.alex.serialization.ObjectSerializer;

import java.nio.charset.StandardCharsets;

class Main {

    public static void main(String[] args) {

        ObjectSerializer os = new ObjectSerializer.Builder()
                .setFormatter(Header.class, new JsonFormatter(StandardCharsets.UTF_8))
                .setFormatter(TestResponse.class, new TestFormatter())
                .build();
        TestResponse response = new TestResponse(
                new Header("f1", "f2"),
                "f3",
                "f4"
        );

        System.out.println(new String(os.serialize(response), StandardCharsets.UTF_8));
    }
}
