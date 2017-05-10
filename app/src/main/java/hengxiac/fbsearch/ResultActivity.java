package hengxiac.fbsearch;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.Toast;
import android.support.design.widget.TabLayout.Tab;

import java.net.URLEncoder;

/**
 * Created by hengxiang1 on 2017/4/21.
 */

public class ResultActivity extends AppCompatActivity{

    //private TabHost tabhost;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private TabLayout.Tab user;
    private TabLayout.Tab page;
    private TabLayout.Tab place;
    private TabLayout.Tab event;
    private TabLayout.Tab group;
    private TabLayout.Tab fav;
    private cusFragmentPagerAdapter fragadapter;
    private int startpage;
    private  ActionBarDrawerToggle actionbartoggle;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent intentnow = getIntent();
        startpage = intentnow.getIntExtra("currentpage",0);
        initviews();
        initevents();


        title=intentnow.getStringExtra("showtitle");
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(title);
        if(title.equals("Favorites"))
           initdrawer();
        else{
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(title.equals( "Results")) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
         }
        else
        {
            return actionbartoggle.onOptionsItemSelected(item)
                    || super.onOptionsItemSelected(item);
        }
    }


    private void initviews(){


        tablayout = (TabLayout) findViewById(R.id.tabLayout);
        viewpager = (ViewPager) findViewById(R.id.viewPager);


        fragadapter = new cusFragmentPagerAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter(fragadapter);
        tablayout.setupWithViewPager(viewpager);


        user  = tablayout.getTabAt(0);
        page  = tablayout.getTabAt(1);
        event = tablayout.getTabAt(2);
        place = tablayout.getTabAt(3);
        group = tablayout.getTabAt(4);


        user.setIcon(getResources().getDrawable(R.drawable.users));
        page.setIcon(getResources().getDrawable(R.drawable.pages));
        event.setIcon(getResources().getDrawable(R.drawable.events));
        place.setIcon(getResources().getDrawable(R.drawable.places));
        group.setIcon(getResources().getDrawable(R.drawable.groups));

        viewpager.setCurrentItem(startpage);

    }
    private void initevents(){
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               if(tab == tablayout.getTabAt(0))
               {
                   viewpager.setCurrentItem(0);

               }
               else if(tab == tablayout.getTabAt(1))
               {
                   viewpager.setCurrentItem(1);

               }
                else if(tab == tablayout.getTabAt(2))
                {
                    viewpager.setCurrentItem(2);

                }
                else if(tab == tablayout.getTabAt(3))
                {

                    viewpager.setCurrentItem(3);
                }
                else if(tab == tablayout.getTabAt(4))
                {
                    viewpager.setCurrentItem(4);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    private void initdrawer(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.puredrawer, null); // "null" is important.

        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        FrameLayout container = (FrameLayout) drawer.findViewById(R.id.container); // This is the container we defined just now.
        container.addView(child);

        // Make the drawer replace the first child
        decor.addView(drawer);

        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final LinearLayout left = (LinearLayout)findViewById(R.id.leftdrawer);
        ListView listview = (ListView)findViewById(R.id.left_listview);
        ListView aboutlv = (ListView) findViewById(R.id.aboutme);

        String []aboutmeinfo={"About Me"};
        String []textString ={"Home","Favorites"};
        int []drawableIds = {R.drawable.ic_home,R.drawable.fav};

        Context contextNow = drawer.getContext();
        CustomAdapter cadapter = new CustomAdapter(contextNow,  textString, drawableIds);
        ArrayAdapter adapteraboutme = new ArrayAdapter<String>(contextNow,android.R.layout.simple_list_item_1, aboutmeinfo);
        listview.setAdapter(cadapter);
        aboutlv.setAdapter(adapteraboutme);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ham);
        actionbartoggle = new ActionBarDrawerToggle(this, drawerLayout,0, 0);
        drawerLayout.addDrawerListener(actionbartoggle);
        actionbartoggle.syncState();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        /*Intent intent = new Intent();
                        intent.setClass(MainActivity.this,ResultActivity.class);
                        startActivity(intent);*/
                        Intent intent = new Intent();
                        intent.setClass(ResultActivity.this,MainActivity.class);
                        startActivity(intent);

                        break;
                    case 1:
                        drawerLayout.closeDrawer(left);
                        break;
                    default:
                        break;
                }
            }
        });


        aboutlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0 :
                        Intent intent = new Intent();
                        intent.setClass(ResultActivity.this,AboutActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });


    }
/*
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        actionbartoggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionbartoggle.onConfigurationChanged(newConfig);
    }
*/
}
