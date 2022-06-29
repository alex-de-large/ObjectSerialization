package example;

public class TestResponse extends Response{

    private String f4;

    public TestResponse(Header header, String field1, String f4) {
        super(header, field1);
        this.f4 = f4;
    }
}
