package hengxiac.fbsearch;

//import com.facebook.FacebookSdk;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.text.TextUtils;
import android.content.DialogInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import 	android.support.v4.content.ContextCompat;
import android.os.AsyncTask;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener */{

    private DrawerLayout drawerLayout;
    private LinearLayout left;
    private ArrayAdapter<String> adapteraboutme;
    private ListView listview;
    private ListView aboutlv;
    private ActionBarDrawerToggle actionbartoggle;
    private Button btnsearch;
    private Button btnclear;
    private EditText inKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //actionbartoggle = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_ham,0,0);
        //actionbartoggle.syncState();

        // Inflate the "decor.xml"
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

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        left = (LinearLayout)findViewById(R.id.leftdrawer);
        listview = (ListView)findViewById(R.id.left_listview);
        aboutlv = (ListView) findViewById(R.id.aboutme);
        inKey = (EditText) findViewById(R.id.inkey);

        String []aboutmeinfo={"About Me"};
        String []textString ={"Home","Favorites"};
        int []drawableIds = {R.drawable.ic_home,R.drawable.fav};

        Context contextNow = drawer.getContext();
        CustomAdapter cadapter = new CustomAdapter(contextNow,  textString, drawableIds);
        adapteraboutme = new ArrayAdapter<String>(contextNow,android.R.layout.simple_list_item_1, aboutmeinfo);
        listview.setAdapter(cadapter);
        aboutlv.setAdapter(adapteraboutme);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        /*Intent intent = new Intent();
                        intent.setClass(MainActivity.this,ResultActivity.class);
                        startActivity(intent);*/
                        drawerLayout.closeDrawer(left);
                        break;
                    case 1:
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,ResultActivity.class);
                        intent.putExtra("isfavorite",true);
                        intent.putExtra("showtitle","Favorites");
                        startActivity(intent);
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
                        intent.setClass(MainActivity.this,AboutActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        btnsearch = (Button)findViewById(R.id.search_button);//get resource
        btnsearch.setOnClickListener(new Button.OnClickListener(){//set listener
            public void onClick(View v) {

                String str = inKey.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Input Error")//Title
                            .setMessage("Please input keyword！")//content
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    /*Intent intent = new Intent();
                    intent.setClass(MainActivity.this,ResultActivity.class);
                    intent.putExtra("keyword", str);
                    startActivity(intent);*/
                    double latitude = 34, longitude = -118;
                    getGEOLocation locationget = new getGEOLocation(getApplicationContext());

                    if (locationget.canGetLocation) {
                        locationget.getLocation();
                        longitude = locationget.getLongitude();
                        latitude = locationget.getLatitude();
                    }
                    //Toast.makeText(getApplicationContext(), "Location :(LAT,LOG) = (" + Double.toString(latitude) + " , " + Double.toString(longitude) + ")", Toast.LENGTH_SHORT).show();

                String url = "http://homework8-163001.appspot.com/HW9.php";
                str = str.replaceAll(" ", "+");
                String pair = "?keyword=" + str;
                url = url + pair;
                getDataFromBackend getdata = new getDataFromBackend(getApplicationContext(), url, pair);
                getdata.execute();


            }

            }

        });

        btnclear = (Button)findViewById(R.id.clear_button);//get resource
        btnclear.setOnClickListener(new Button.OnClickListener(){//set listener
            public void onClick(View v) {
                inKey.setText("");
            }

        });

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.app_name);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ham);
        actionbartoggle = new ActionBarDrawerToggle(this, drawerLayout,0, 0);
        drawerLayout.addDrawerListener(actionbartoggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        actionbartoggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionbartoggle.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionbartoggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


}
