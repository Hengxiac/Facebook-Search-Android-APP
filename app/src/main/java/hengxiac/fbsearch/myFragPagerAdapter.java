package hengxiac.fbsearch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by hengxiang1 on 2017/4/26.
 */

public class myFragPagerAdapter extends FragmentStatePagerAdapter {
    private final int count = 2;
    private String[] tabnames = {"ALBUMS","POSTS"};
    private Context context;


    public myFragPagerAdapter(FragmentManager fm, Context context){
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
        return detailFragment.newInstance(position);

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
