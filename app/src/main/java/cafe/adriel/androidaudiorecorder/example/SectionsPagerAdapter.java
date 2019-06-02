package cafe.adriel.androidaudiorecorder.example;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
//section page adapter designed for tab activity StudentMActivity
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mfragmentList=new ArrayList();
    private final List<String> mfragmentTitlelist=new ArrayList();


    public void addFragment(Fragment fragment,String Title){
        mfragmentList.add(fragment);
        mfragmentTitlelist.add(Title);
    }
    SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mfragmentTitlelist.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
