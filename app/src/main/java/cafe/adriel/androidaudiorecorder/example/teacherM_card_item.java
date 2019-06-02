package cafe.adriel.androidaudiorecorder.example;

public class teacherM_card_item {
    private String mImagerResourcestudent;
    private String mText4;
    private String email;

    teacherM_card_item (String ImageResource1, String text1,String email1){
        mImagerResourcestudent=ImageResource1;
        mText4=text1;
        email=email1;

    }

    public String getmImagerResourcestudent() {
        return mImagerResourcestudent;
    }
    public String getmText4() {
        return mText4;
    }

    public String getEmail(){
        return email;
    }
}