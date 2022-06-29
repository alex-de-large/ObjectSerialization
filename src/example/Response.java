package test;

import com.alex.serialization.SerializedFieldStringValueExtractionPolicy;
import com.alex.serialization.annotations.Serializable;
import com.alex.serialization.annotations.StringValueExtractionPolicy;

@Serializable
public class Response {

    @StringValueExtractionPolicy(policy = SerializedFieldStringValueExtractionPolicy.FROM_SERIALIZED_DATA)
    private Header header;
    private String field1;

    public Response(Header header, String field1) {
        this.header = header;
        this.field1 = field1;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }
}
