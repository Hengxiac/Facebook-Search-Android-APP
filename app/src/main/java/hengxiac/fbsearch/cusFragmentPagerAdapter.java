package hengxiac.fbsearch;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import org.json.JSONObject;

/**
 * Created by hengxiang1 on 2017/4/23.
 */

public class cusFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final int count = 5;
    private String[] tabnames = {"USER","PAGE","EVENT","PLACE","GROUP"};
    private Context context;
    private long baseId = 0;


    public cusFragmentPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }



    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Fragment getItem(int position){
        return PageFragment.newInstance(position);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabnames[position];
    }



    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }


}

