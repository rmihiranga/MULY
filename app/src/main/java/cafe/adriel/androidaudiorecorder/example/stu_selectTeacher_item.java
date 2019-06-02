package cafe.adriel.androidaudiorecorder.example;

public class stu_selectTeacher_item {
    private String mImagerResource3;
    private String mText3;
    private String mEmail;

    stu_selectTeacher_item(String ImageResource, String text1,String email){
        mImagerResource3=ImageResource;
        mText3=text1;
        mEmail = email;

    }

    public String getmImagerResource2() {
        return mImagerResource3;
    }

    public String getmText2() {
        return mText3;
    }

    public String getmEmail() {
        return mEmail;
    }

}