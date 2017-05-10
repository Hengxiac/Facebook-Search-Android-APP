package hengxiac.fbsearch;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hengxiang1 on 2017/4/26.
 */

public class NextPrev extends AsyncTask<String, Integer, String> {

    private Context context;
    private String url;
    private String type;
    private boolean gotonext;
    private resultAdapter readapter;
    private Button next;
    private Button prev;
    private ListView listview;
    private String cached;
    private String title;

    NextPrev( Context context , String string ,String type,boolean gonext,resultAdapter re,ListView listview, Button prev,Button next,String cached,String title){
        this.context = context;
        url = string;
        this.type = type;
        gotonext = gonext;
        readapter = re;
        this.next = next;
        this.prev = prev;
        this.listview = listview;
        this.cached = cached;
        this.title = title;
    }

    @Override
    protected void onPreExecute()
    {
    }

    @Override
    protected String doInBackground(String...param)
    {
        String result = Networkable.GetJson(url,"");
        //getData(result);
        return result;
    }

    @Override
    protected void onPostExecute(String i) {


        int index;
        switch(type){
            case "user":
                index = 0;
                break;
            case "page":
                index = 1;
                break;
            case "event":
                index = 2;
                break;
            case "place":
                index = 3;
                break;
            case "group":
                index = 4;
                break;
            default:
                index = -1;
                break;
        }
        JSONObject result = null;
        JSONArray ary = null;
        fbObj[] obj = null;
        JSONObject paging = null;
        try{
            result = new JSONObject(i);
            ary = result.getJSONArray("data");
            paging = result.getJSONObject("paging");
            obj = new fbObj[ary.length()];

            JSONObject jobj = new JSONObject(cached);
            jobj.put(type,result);
            cached = jobj.toString();

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            for(int count =0;count<=ary.length()-1;count++)
            {
                obj[count] = new fbObj();
                obj[count].setType(type);
                obj[count].setProfile(ary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                obj[count].setName(ary.getJSONObject(count).getString("name"));
                obj[count].setId(ary.getJSONObject(count).getString("id"));
                obj[count].setFavorite(false);
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            if(paging!=null&&paging.has("previous"))
            {
                PageFragment.paging[index][0] = paging.getString("previous");
                PageFragment.hasprev[index] = true;
                prev.setEnabled(true);
            }
            else{
                PageFragment.hasprev[index] = false;
                PageFragment.paging[index][0] ="";
                prev.setEnabled(false);
            }

            if(paging!=null&&paging.has("next"))
            {
                PageFragment.paging[index][1] = paging.getString("next");
                PageFragment.hasnext[index] = true;
                next.setEnabled(true);
            }
            else{
                PageFragment.hasnext[index] = false;
                PageFragment.paging[index][1] ="";
                next.setEnabled(false);
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        readapter = new resultAdapter(context,obj,index,cached,title);
        listview.setAdapter(readapter);
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
    }

   /* private void getData(String i)
    {

        try{
            JSONObject resultobj = new JSONObject(i);
            JSONArray ary = new JSONArray();

            ary = resultobj.getJSONArray("data");

            fbObj[] obj = new fbObj[ary.length()];

            for(int count =0;count<=ary.length()-1;count++)
            {
                obj[count] = new fbObj();
                obj[count].setType(type);
                obj[count].setProfile(ary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                obj[count].setName(ary.getJSONObject(count).getString("name"));
                obj[count].setId(ary.getJSONObject(count).getString("id"));
                obj[count].setFavorite(false);
            }
            int setindex;
            if(gotonext == true)
                setindex = PageFragment.NEXT;
            else
                setindex = PageFragment.PREV;
            switch(type)
            {
                case "user":
                PageFragment.setData(setindex,true,obj,false,null,false,null,false,null,false,null);
                break;
                case "page":
                    PageFragment.setData(setindex,false,null,true,obj,false,null,false,null,false,null);
                    break;
                case "event":
                    PageFragment.setData(setindex,false,null,false,null,true,obj,false,null,false,null);
                    break;
                case "place":
                    PageFragment.setData(setindex,false,null,false,null,false,null,true,obj,false,null);
                    break;
                case "group":
                    PageFragment.setData(setindex,false,null,false,null,false,null,false,null,true,obj);
                    break;
                default:
                    break;

            }


            JSONObject objpaging;
            boolean hasprev = false;
            boolean hasnext = false;
            String[] urls = new String[2];

            objpaging =  resultobj.getJSONObject("paging");

            if(objpaging != null){
                if(objpaging.has("next"))
                {
                    hasnext = true;
                    urls[1] =objpaging.getString("next");

                }
                if(objpaging.has("previous"))
                {
                    hasprev = true;
                    urls[0] = objpaging.getString("previous");
                }
            }

            PageFragment.setonepaging(setindex,type,hasnext,hasprev,urls);


        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
