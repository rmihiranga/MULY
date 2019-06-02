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

@DynamoDBTable(tableName = "mulyv-mobilehub-241976042-students")

public class StudentsDO {
    private String _stdId;
    private String _name;
    private String _address;
    private String _email;
    private List<String> _instruments;
    private String _results;
    private List<String> _teachers;
    private String _telephone;

    @DynamoDBHashKey(attributeName = "stdId")
    @DynamoDBIndexHashKey(attributeName = "stdId", globalSecondaryIndexName = "search_students")
    public String getStdId() {
        return _stdId;
    }

    public void setStdId(final String _stdId) {
        this._stdId = _stdId;
    }
    @DynamoDBRangeKey(attributeName = "name")
    @DynamoDBIndexRangeKey(attributeName = "name", globalSecondaryIndexName = "search_students")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {
        return _address;
    }

    public void setAddress(final String _address) {
        this._address = _address;
    }
    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return _email;
    }

    public void setEmail(final String _email) {
        this._email = _email;
    }
    @DynamoDBAttribute(attributeName = "instruments")
    public List<String> getInstruments() {
        return _instruments;
    }

    public void setInstruments(final List<String> _instruments) {
        this._instruments = _instruments;
    }
    @DynamoDBAttribute(attributeName = "results")
    public String getResults() {
        return _results;
    }

    public void setResults(final String _results) {
        this._results = _results;
    }
    @DynamoDBAttribute(attributeName = "teachers")
    public List<String> getTeachers() {
        return _teachers;
    }

    public void setTeachers(final List<String> _teachers) {
        this._teachers = _teachers;
    }
    @DynamoDBAttribute(attributeName = "telephone")
    public String getTelephone() {
        return _telephone;
    }

    public void setTelephone(final String _telephone) {
        this._telephone = _telephone;
    }

}
