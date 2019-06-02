package cafe.adriel.androidaudiorecorder.example.logics;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "mulyv-mobilehub-241976042-user_data_time_acc")

public class UserDataTimeAccDO {
    private String _userId;
    private String _d1;
    private String _d2;
    private String _d3;
    private String _d4;
    private String _d5;
    private String _d6;
    private String _d7;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "d1")
    public String getD1() {
        return _d1;
    }

    public void setD1(final String _d1) {
        this._d1 = _d1;
    }
    @DynamoDBAttribute(attributeName = "d2")
    public String getD2() {
        return _d2;
    }

    public void setD2(final String _d2) {
        this._d2 = _d2;
    }
    @DynamoDBAttribute(attributeName = "d3")
    public String getD3() {
        return _d3;
    }

    public void setD3(final String _d3) {
        this._d3 = _d3;
    }
    @DynamoDBAttribute(attributeName = "d4")
    public String getD4() {
        return _d4;
    }

    public void setD4(final String _d4) {
        this._d4 = _d4;
    }
    @DynamoDBAttribute(attributeName = "d5")
    public String getD5() {
        return _d5;
    }

    public void setD5(final String _d5) {
        this._d5 = _d5;
    }
    @DynamoDBAttribute(attributeName = "d6")
    public String getD6() {
        return _d6;
    }

    public void setD6(final String _d6) {
        this._d6 = _d6;
    }
    @DynamoDBAttribute(attributeName = "d7")
    public String getD7() {
        return _d7;
    }

    public void setD7(final String _d7) {
        this._d7 = _d7;
    }

}
