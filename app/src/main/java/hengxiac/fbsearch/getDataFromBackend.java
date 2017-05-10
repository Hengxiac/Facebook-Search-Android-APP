package hengxiac.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by hengxiang1 on 2017/4/24.
 */

public class getDataFromBackend extends AsyncTask<String, Integer, String> {

    private Context mContext;
    private String url;
    private String postValue;

    getDataFromBackend(Context context, String string , String postValue){
        mContext = context;
        url = string;
        this.postValue = postValue;
    }

    @Override
    protected void onPreExecute()
    {
        //Toast.makeText(mContext,"Send http request",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String...param)
    {
        String result = Networkable.GetJson(url,postValue);
        //getData(result);
        return result;
    }

    @Override
    protected void onPostExecute(String i) {
        //Toast.makeText(mContext,i,Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        intent.setClass(mContext,ResultActivity.class);
        intent.putExtra("result", i);
        intent.putExtra("isfavorite",false);
        intent.putExtra("showtitle","Results");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }



    @Override
    protected void onProgressUpdate(Integer... values)
    {
    }
/*
    private void getData(String i)
    {
        JSONObject resultobj =null ;
        JSONArray userary = new JSONArray();
        JSONArray  pageary = new JSONArray();
        JSONArray  eventary = new JSONArray();
        JSONArray  placeary = new JSONArray();
        JSONArray  groupary = new JSONArray();

        fbObj[] userobj  = null;
        fbObj[] pageobj  = null;
        fbObj[] eventobj =null ;
        fbObj[] placeobj =null;
        fbObj[] groupobj =null ;

        JSONObject objuser,objpage,objevent,objplace,objgroup;
        boolean[] hasprev = {false,false,false,false,false};
        boolean[] hasnext = {false,false,false,false,false};
        String[][] urls = new String[5][2];

        try{
            resultobj = new JSONObject(i);
            userary = resultobj.getJSONObject("user").getJSONArray("data");
            pageary = resultobj.getJSONObject("page").getJSONArray("data");
            eventary = resultobj.getJSONObject("event").getJSONArray("data");
            placeary = resultobj.getJSONObject("place").getJSONArray("data");
            groupary = resultobj.getJSONObject("group").getJSONArray("data");

            userobj = new fbObj[userary.length()];
            pageobj = new fbObj[pageary.length()];
            eventobj = new fbObj[eventary.length()];
            placeobj = new fbObj[placeary.length()];
            groupobj = new fbObj[groupary.length()];
        }
        catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        try{
            for(int count =0;count<=userary.length()-1;count++)
            {
                userobj[count] = new fbObj();
                userobj[count].setType("user");
                userobj[count].setProfile(userary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                userobj[count].setName(userary.getJSONObject(count).getString("name"));
                userobj[count].setId(userary.getJSONObject(count).getString("id"));
                userobj[count].setFavorite(false);
            }

            for(int count =0;count<=pageary.length()-1;count++)
            {
                pageobj[count] = new fbObj();
                pageobj[count].setType("page");
                pageobj[count].setProfile(pageary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                pageobj[count].setName(pageary.getJSONObject(count).getString("name"));
                pageobj[count].setId(pageary.getJSONObject(count).getString("id"));
                pageobj[count].setFavorite(false);
            }
            for(int count =0;count<=eventary.length()-1;count++)
            {
                eventobj[count] = new fbObj();
                eventobj[count].setType("event");
                eventobj[count].setProfile(eventary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                eventobj[count].setName(eventary.getJSONObject(count).getString("name"));
                eventobj[count].setId(eventary.getJSONObject(count).getString("id"));
                eventobj[count].setFavorite(false);
            }
            for(int count =0;count<=placeary.length()-1;count++)
            {
                placeobj[count] = new fbObj();
                placeobj[count].setType("place");
                placeobj[count].setProfile(placeary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                placeobj[count].setName(placeary.getJSONObject(count).getString("name"));
                placeobj[count].setId(placeary.getJSONObject(count).getString("id"));
                placeobj[count].setFavorite(false);
            }
            for(int count =0;count<=groupary.length()-1;count++)
            {
                groupobj[count] = new fbObj();
                groupobj[count].setType("group");
                groupobj[count].setProfile(groupary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                groupobj[count].setName(groupary.getJSONObject(count).getString("name"));
                groupobj[count].setId(groupary.getJSONObject(count).getString("id"));
                groupobj[count].setFavorite(false);
            }
            PageFragment.setData(PageFragment.CURRENT,true,userobj,true,pageobj,true,eventobj,true,placeobj,true,groupobj);
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            objuser =  resultobj.getJSONObject("user").getJSONObject("paging");
            if(objuser != null){
                if(objuser.has("next"))
                {
                    hasnext[0] = true;
                    urls[0][1] =objuser.getString("next");

                    NextPrev getnextuser = new NextPrev(urls[0][1],"user",true);
                    getnextuser.execute();
                }
                if(objuser.has("previous"))
                {
                    hasprev[0] = true;
                    urls[0][0] = objuser.getString("previous");

                    NextPrev getprevuser = new NextPrev(urls[0][0],"user",false);
                    getprevuser.execute();
                }
                else
                {
                    hasprev[0] = false;
                    urls[0][0]="";
                }
            }

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            objpage =  resultobj.getJSONObject("page").getJSONObject("paging");
            if(objpage != null){
                if(objpage.has("next"))
                {
                    hasnext[1] = true;
                    urls[1][1] =objpage.getString("next");

                    NextPrev getnextpage = new NextPrev(urls[1][1],"page",true);
                    getnextpage.execute();
                }
                if(objpage.has("previous"))
                {
                    hasprev[1] = true;
                    urls[1][0] = objpage.getString("previous");

                    NextPrev getprevpage = new NextPrev(urls[1][0],"page",false);
                    getprevpage.execute();
                }
            }

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            objevent = resultobj.getJSONObject("event").getJSONObject("paging");
            if(objevent != null){
                if(objevent.has("next"))
                {
                    hasnext[2] = true;
                    urls[2][1] =objevent.getString("next");

                    NextPrev getnextevent = new NextPrev(urls[2][1],"event",true);
                    getnextevent.execute();
                }
                if(objevent.has("previous"))
                {
                    hasprev[2] = true;
                    urls[2][0] = objevent.getString("previous");

                    NextPrev getprevevent = new NextPrev(urls[2][0],"event",false);
                    getprevevent.execute();
                }
            }

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{

            objplace = resultobj.getJSONObject("place").getJSONObject("paging");
            if(objplace != null){
                if(objplace.has("next"))
                {
                    hasnext[3] = true;
                    urls[3][1] =objplace.getString("next");

                    NextPrev getnextplace = new NextPrev(urls[3][1],"place",true);
                    getnextplace.execute();
                }
                if(objplace.has("previous"))
                {
                    hasprev[3] = true;
                    urls[3][0] = objplace.getString("previous");

                    NextPrev getprevplace= new NextPrev(urls[3][0],"place",false);
                    getprevplace.execute();
                }
            }

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try{
            objgroup = resultobj.getJSONObject("group").getJSONObject("paging");
            if(objgroup != null){
                if(objgroup.has("next"))
                {
                    hasnext[4] = true;
                    urls[4][1] =objgroup.getString("next");

                    NextPrev getnextgroup= new NextPrev(urls[4][1],"group",true);
                    getnextgroup.execute();
                }
                if(objgroup.has("previous"))
                {
                    hasprev[4] = true;
                    urls[4][0] = objgroup.getString("previous");

                    NextPrev getprevgroup = new NextPrev(urls[4][0],"group",false);
                    getprevgroup.execute();
                }
            }

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        PageFragment.setpaging(hasnext,hasprev,urls);
    }*/
}
