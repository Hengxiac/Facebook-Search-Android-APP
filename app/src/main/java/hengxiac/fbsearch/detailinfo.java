package hengxiac.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by hengxiang1 on 2017/4/26.
 */

public class detailinfo extends AsyncTask<String, Integer, String> {

    private Context mcontext;
    private String url;
    private String id;
    private String profile;
    private String name;
    private String cachedinfo;
    private int pagenow;
    private String title;

    detailinfo(Context context , String url, String id,String profile,String name,String cachedinfo,int pagenow,String title)
    {
        mcontext = context;
        this.url = url;
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.cachedinfo = cachedinfo;
        this.pagenow = pagenow;
        this.title = title;
    }
    @Override
    protected void onPreExecute()
    {
    }

    @Override
    protected String doInBackground(String...param)
    {
        String result = Networkable.GetJson(url,url);
        return result;
    }

    @Override
    protected void onPostExecute(String i) {

        if(i!="ERROR") {
            String pagetype ="";
            switch(pagenow)
            {
                case 0:
                    pagetype="user";
                    break;
                case 1:
                    pagetype="page";
                    break;
                case 2:
                    pagetype="event";
                    break;
                case 3:
                    pagetype="place";
                    break;
                case 4:
                    pagetype="group";
                    break;

            }
            Intent intent = new Intent();
            intent.setClass(mcontext, DetailsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("profile", profile);
            intent.putExtra("name", name);
            intent.putExtra("type",pagetype);
            intent.putExtra("RESULT", i);
            intent.putExtra("cached",cachedinfo);
            intent.putExtra("currentpage",pagenow);
            intent.putExtra("title",title);
            if(title.equals("Favorites"))
               intent.putExtra("isfavorite",true);
            else
                intent.putExtra("isfavorite",false);
            mcontext.startActivity(intent);
        }
        else{
            Toast.makeText(mcontext,"Details not found",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onProgressUpdate(Integer... values)
    {
    }
}
