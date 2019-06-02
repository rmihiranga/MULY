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

@DynamoDBTable(tableName = "mulyv-mobilehub-241976042-rating")

public class RatingDO {
    private String _userId;
    private Double _count;
    private Double _rate;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "count")
    public Double getCount() {
        return _count;
    }

    public void setCount(final Double _count) {
        this._count = _count;
    }
    @DynamoDBAttribute(attributeName = "rate")
    public Double getRate() {
        return _rate;
    }

    public void setRate(final Double _rate) {
        this._rate = _rate;
    }

}
