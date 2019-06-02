package cafe.adriel.androidaudiorecorder.example;

public class stu_search_teacher_item {
    private String mImageResource;
    private String mtext1;
    private String mtext2;

    public stu_search_teacher_item(String ImageResource,String text1,String text2){
        mImageResource=ImageResource;
        mtext1=text1;
        mtext2=text2;

    }

    public String getmImageResource() {
        return mImageResource;
    }

    public String gettext1(){
        return mtext1;
    }

    public String gettext2(){
        return mtext2;
    }
}