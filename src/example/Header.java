package example;

import com.alex.serialization.annotations.Key;
import com.alex.serialization.annotations.Position;
import com.alex.serialization.annotations.Serializable;

@Serializable
class Header {

    @Position(position = 1)
    @Key(value = "pudge")
    private String field1;
    @Position(position = 0)
    @Key(value = "void")
    private String field2;

    public Header(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
