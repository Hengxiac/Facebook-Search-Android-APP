package hengxiac.fbsearch;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by hengxiang1 on 2017/4/21.
 */

public class DetailsActivity extends AppCompatActivity {

    private MenuItem fav_or_remove;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private TabLayout.Tab album;
    private TabLayout.Tab post;
    private String cachedcontext;
    private int currentpage;
    private String type;
    private String name;
    private String profile;
    private String id;
    private String title;
    private boolean isfav;


    private myFragPagerAdapter fragadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent = this.getIntent();
        cachedcontext = intent.getStringExtra("cached");
        name = intent.getStringExtra("name");
        type = intent.getStringExtra("type");
        profile = intent.getStringExtra("profile");
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        isfav = intent.getBooleanExtra("isfavorite",false);

        currentpage = intent.getIntExtra("currentpage",0);

        initviews();
        initevents();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("More Details");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(DetailsActivity.this,ResultActivity.class);
                intent.putExtra("result",cachedcontext);
                intent.putExtra("currentpage",currentpage);
                intent.putExtra("showtitle",title);
                intent.putExtra("isfavorite",isfav);
                startActivity(intent);
                return true;

            case R.id.action_addfav:
                SharedPreferences sharedpreference = getSharedPreferences("favorite",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreference.edit();
                String value = "&type="+type+"&url="+profile+"&name="+name;
                if(sharedpreference.contains(id))
                {
                    editor.remove(id);
                    fav_or_remove.setTitle("Add to Favorite");
                    editor.commit();
                    try {
                        JSONObject jobj = new JSONObject(cachedcontext);
                        for(int i =0;i<=jobj.getJSONObject(type).getJSONArray("data").length()-1;i++)
                        {
                            String parseid = (String)jobj.getJSONObject(type).getJSONArray("data").getJSONObject(i).getString("id");
                            if( parseid.equals(id))
                            {
                                /*JSONArray newary = new JSONArray();
                                for(int k = 0;k<=i-1;k++)
                                    newary.put(k,jobj.getJSONObject(type).getJSONArray("data").getJSONObject(k));
                                newary.put(i,new JSONObject());
                                for(int j = i;j<=jobj.getJSONObject(type).getJSONArray("data").length()-2;j++)
                                {
                                    newary.put(j,jobj.getJSONObject(type).getJSONArray("data").getJSONObject(j+1));
                                }
                                jobj.getJSONObject(type).put("data",newary);*/
                                jobj.getJSONObject(type).put("data",remove(i,jobj.getJSONObject(type).getJSONArray("data")));
                                break;

                            }
                        }

                        cachedcontext = jobj.toString();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else
                {
                   editor.putString(id,value);
                    fav_or_remove.setTitle("Remove from Favorite");
                    editor.commit();
                }
                return true;
            case R.id.action_share:
                FacebookSdk.sdkInitialize(DetailsActivity.this);
                CallbackManager callbackManager = CallbackManager.Factory.create();
                ShareDialog shareDialog = new ShareDialog(this);

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                    @Override
                    public void onSuccess(Sharer.Result result) {
                       // Log.d(LOG_TAG, "success");
                        new AlertDialog.Builder(DetailsActivity.this).setTitle("Info")//Title
                                .setMessage("Post Successfully！")//content
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        dialog.dismiss();
                                    }
                                }).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                       // Log.d(LOG_TAG, "error");
                        new AlertDialog.Builder(DetailsActivity.this).setTitle("Info")//Title
                                .setMessage("Failed！")//content
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        dialog.dismiss();
                                    }
                                }).show();
                    }

                    @Override
                    public void onCancel() {
                       // Log.d(LOG_TAG, "cancel");
                    }
                });
                String url = "";//"http://homework8-163001.appspot.com/"
                if(shareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentTitle(name)
                            .setContentDescription("FB SEARCH FROM USC CSCI571")
                            .setContentUrl(Uri.parse("http://homework8-163001.appspot.com/"))
                            .setImageUrl(Uri.parse(profile))
                            .build();
                    shareDialog.show(content);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void initviews(){



        tablayout = (TabLayout) findViewById(R.id.tabLayoutdetail);
        viewpager = (ViewPager) findViewById(R.id.viewPagerdetail);


        fragadapter = new myFragPagerAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter(fragadapter);
        tablayout.setupWithViewPager(viewpager);


        album  = tablayout.getTabAt(0);
        post  = tablayout.getTabAt(1);


        album.setIcon(getResources().getDrawable(R.drawable.albums));
        post.setIcon(getResources().getDrawable(R.drawable.posts));


        viewpager.setCurrentItem(0);

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

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        fav_or_remove = menu.findItem(R.id.action_addfav);
        SharedPreferences sharedpreference = getSharedPreferences("favorite",MODE_PRIVATE);
        if(sharedpreference.contains(id))
        {
            fav_or_remove.setTitle("Remove from Favorite");
        }
        else
        {
            fav_or_remove.setTitle("Add to Favorite");
        }
        return super.onCreateOptionsMenu(menu);

    }

    public static JSONArray remove(final int idx, final JSONArray from) {
        final List<JSONObject> objs = asList(from);
        objs.remove(idx);

        final JSONArray ja = new JSONArray();
        for (final JSONObject obj : objs) {
            ja.put(obj);
        }

        return ja;
    }

    public static List<JSONObject> asList(final JSONArray ja) {
        final int len = ja.length();
        final ArrayList<JSONObject> result = new ArrayList<JSONObject>(len);
        for (int i = 0; i < len; i++) {
            final JSONObject obj = ja.optJSONObject(i);
            if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }
}
