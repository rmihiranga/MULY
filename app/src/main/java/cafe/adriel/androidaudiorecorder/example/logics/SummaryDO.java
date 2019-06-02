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

@DynamoDBTable(tableName = "mulyv-mobilehub-241976042-summary")

public class SummaryDO {
    private String _userId;
    private Map<String, String> _days;
    private Map<String, String> _teachers;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "days")
    public Map<String, String> getDays() {
        return _days;
    }

    public void setDays(final Map<String, String> _days) {
        this._days = _days;
    }
    @DynamoDBAttribute(attributeName = "teachers")
    public Map<String, String> getTeachers() {
        return _teachers;
    }

    public void setTeachers(final Map<String, String> _teachers) {
        this._teachers = _teachers;
    }

}
