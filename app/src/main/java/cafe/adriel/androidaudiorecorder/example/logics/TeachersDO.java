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

@DynamoDBTable(tableName = "mulyv-mobilehub-241976042-teachers")

public class TeachersDO {
    private String _teacherid;
    private String _name;
    private String _address;
    private String _email;
    private List<String> _instruments;
    private List<String> _students;
    private String _telephone;

    @DynamoDBHashKey(attributeName = "teacherid")
    @DynamoDBIndexHashKey(attributeName = "teacherid", globalSecondaryIndexName = "search_teachers")
    public String getTeacherid() {
        return _teacherid;
    }

    public void setTeacherid(final String _teacherid) {
        this._teacherid = _teacherid;
    }
    @DynamoDBRangeKey(attributeName = "name")
    @DynamoDBIndexRangeKey(attributeName = "name", globalSecondaryIndexName = "search_teachers")
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
    @DynamoDBAttribute(attributeName = "students")
    public List<String> getStudents() {
        return _students;
    }

    public void setStudents(final List<String> _students) {
        this._students = _students;
    }
    @DynamoDBAttribute(attributeName = "telephone")
    public String getTelephone() {
        return _telephone;
    }

    public void setTelephone(final String _telephone) {
        this._telephone = _telephone;
    }

}
