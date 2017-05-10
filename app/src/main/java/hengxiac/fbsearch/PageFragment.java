package hengxiac.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class PageFragment extends Fragment {
    public static final String DISPLAY = "type";
    private  String content;

    private String title;

    private  String cachedvalue;
    private  resultAdapter radapter = null;
    private  fbObj[] curPage = new fbObj[10];

    private   fbObj[] users =  new fbObj[10];
    private   fbObj[] pages =  new fbObj[10];
    private   fbObj[] events = new fbObj[10];
    private   fbObj[] places = new fbObj[10];
    private   fbObj[] groups = new fbObj[10];

    public static boolean[] hasprev = {false,false,false,false,false};
    public static boolean[] hasnext = {false,false,false,false,false};
    public static String[][] paging =  new String[5][2];

    //public   static JSONObject cachedvalue;
    private  JSONObject result;
    private  JSONObject userjson = null;
    private  JSONObject pagejson = null;
    private  JSONObject eventjson = null;
    private  JSONObject placejson = null;
    private  JSONObject groupjson = null;


    public static PageFragment newInstance(int position) {
        Bundle type = new Bundle();
        String typetodisp;
        if(position == 0) {
            typetodisp = "user";
        }
        else if(position == 1) {
            typetodisp = "page";
        }else if(position == 2) {
            typetodisp = "event";
        }else if(position == 3) {
            typetodisp = "place";
        }else if(position == 4){
                typetodisp = "group";
        }
        else{
             typetodisp="";
        }
        type.putString(DISPLAY, typetodisp);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(type);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getArguments().getString(DISPLAY);
        Intent intent = getActivity().getIntent();
        boolean isfavorite = intent.getBooleanExtra("isfavorite",false);
        if(!isfavorite) {
            title ="Results";
            try {
                if (intent.hasExtra("result")) {
                    cachedvalue = intent.getStringExtra("result");
                    result = new JSONObject(cachedvalue);
                    userjson = result.getJSONObject("user");
                    pagejson = result.getJSONObject("page");
                    eventjson = result.getJSONObject("event");
                    placejson = result.getJSONObject("place");
                    groupjson = result.getJSONObject("group");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else{
            title = "Favorites";
            SharedPreferences sharepref = getContext().getSharedPreferences("favorite",Context.MODE_PRIVATE);
            Map<String,?> pref = sharepref.getAll();
            String idnow,typenow,profilenow,namenow,rawdata;
            int index = 0;
            int[] indices={0,0,0,0,0};
            JSONObject jobj = new JSONObject();
            JSONArray userjary = new JSONArray();
            JSONArray pagejary = new JSONArray();
            JSONArray eventjary = new JSONArray();
            JSONArray placejary = new JSONArray();
            JSONArray groupjary = new JSONArray();


            for(Map.Entry<String, ?>  entry : pref.entrySet()){
                idnow = entry.getKey();
                rawdata = (String)entry.getValue();
                int typeindex = rawdata.indexOf("&type=");
                int urlindex = rawdata.indexOf("&url=");
                int nameindex  = rawdata.indexOf("&name=");
                typenow = rawdata.substring(typeindex+6,urlindex);
                profilenow  = rawdata.substring(urlindex+5,nameindex);
                namenow   = rawdata.substring(nameindex+6);
                try {
                switch(typenow)
                {
                    case "user":
                        index = indices[0]++;
                        JSONObject ulayer1 = new JSONObject();
                        JSONObject ulayer2 = new JSONObject();
                        JSONObject ulayer3 = new JSONObject();
                        ulayer3.put("url", profilenow);
                        ulayer2.put("data", ulayer3);
                        ulayer1.put("picture",ulayer2);
                        ulayer1.put("id",idnow);
                        ulayer1.put("name",namenow);
                        userjary.put(index,ulayer1);
                    break;
                    case "page":
                        index = indices[1]++;
                        JSONObject palayer1 = new JSONObject();
                        JSONObject palayer2 = new JSONObject();
                        JSONObject palayer3 = new JSONObject();
                        palayer3.put("url", profilenow);
                        palayer2.put("data", palayer3);
                        palayer1.put("picture",palayer2);
                        palayer1.put("id",idnow);
                        palayer1.put("name",namenow);
                        pagejary.put(index,palayer1);
                        break;
                    case "event":
                        index = indices[2]++;
                        JSONObject elayer1 = new JSONObject();
                        JSONObject elayer2 = new JSONObject();
                        JSONObject elayer3 = new JSONObject();
                        elayer3.put("url", profilenow);
                        elayer2.put("data", elayer3);
                        elayer1.put("picture",elayer2);
                        elayer1.put("id",idnow);
                        elayer1.put("name",namenow);
                        eventjary.put(index,elayer1);
                        break;
                    case "place":
                        index = indices[3]++;
                        JSONObject pllayer1 = new JSONObject();
                        JSONObject pllayer2 = new JSONObject();
                        JSONObject pllayer3 = new JSONObject();
                        pllayer3.put("url", profilenow);
                        pllayer2.put("data", pllayer3);
                        pllayer1.put("picture",pllayer2);
                        pllayer1.put("id",idnow);
                        pllayer1.put("name",namenow);
                        placejary.put(index,pllayer1);
                        break;
                    case "group":
                        index = indices[4]++;
                        JSONObject glayer1 = new JSONObject();
                        JSONObject glayer2 = new JSONObject();
                        JSONObject glayer3 = new JSONObject();
                        glayer3.put("url", profilenow);
                        glayer2.put("data", glayer3);
                        glayer1.put("picture",glayer2);
                        glayer1.put("id",idnow);
                        glayer1.put("name",namenow);
                        groupjary.put(index,glayer1);
                        break;
                }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                userjson = new JSONObject();
                userjson.put("data", userjary);
                pagejson = new JSONObject();
                pagejson.put("data", pagejary);
                eventjson = new JSONObject();
                eventjson.put("data", eventjary);
                placejson = new JSONObject();
                placejson.put("data", placejary);
                groupjson = new JSONObject();
                groupjson.put("data", groupjary);
                jobj.put("user", userjson);
                jobj.put("page", pagejson);
                jobj.put("event", eventjson);
                jobj.put("place", placejson);
                jobj.put("group", groupjson);
            } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            result = jobj;
            cachedvalue = result.toString();


            /*Context mcontext = getActivity();
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            View view = inflater.inflate(R.layout.resultfrag,null);
            LinearLayout buttonlist = (LinearLayout) view.findViewById(R.id.buttonlayout);
            buttonlist.removeViewAt(0);
            buttonlist.removeViewAt(0);
            */
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resultfrag,container,false);
        Context mcontext = getActivity();

        LinearLayout buttonlist = (LinearLayout) view.findViewById(R.id.buttonlayout);
        buttonlist.removeViewAt(0);
        buttonlist.removeViewAt(0);
        final ListView resultlist = (ListView)view.findViewById(R.id.resullist);
        final Button next;
        final Button prev;
        next = (Button) view.findViewById(R.id.next);
        prev = (Button) view.findViewById(R.id.prev);

        if(content == "user")
        {

            JSONArray userary = null;
            fbObj[] userobj = null;
            JSONObject userpaging = null;
            try{
                if(userjson.has("data"))
                {
                    userary = userjson.getJSONArray("data");
                    if(userary!=null)
                        userobj = new fbObj[userary.length()];
                }
                if(userjson.has("paging"))
                    userpaging = userjson.getJSONObject("paging");


            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(userary!=null)
                for(int count =0;count<=userary.length()-1;count++)
                {
                    userobj[count] = new fbObj();
                    userobj[count].setType("user");
                    userobj[count].setProfile(userary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                    userobj[count].setName(userary.getJSONObject(count).getString("name"));
                    userobj[count].setId(userary.getJSONObject(count).getString("id"));
                    userobj[count].setFavorite(false);
                }
               }catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try{
                    if(userpaging!=null&&userpaging.has("previous"))
                    {
                        paging[0][0] = userpaging.getString("previous");
                        hasprev[0] = true;
                    }
                    else{
                        hasprev[0] = false;
                        paging[0][0] ="";
                    }

                    if(userpaging!=null&&userpaging.has("next"))
                    {
                        paging[0][1] = userpaging.getString("next");
                        hasnext[0] = true;
                    }
                    else{
                        hasnext[0] = false;
                        paging[0][1] ="";
                    }
                }
                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                curPage = users = userobj;

               if(prev!=null && next!=null) {
                   prev.setOnClickListener(new Button.OnClickListener() {
                       public void onClick(View v) {

                           {
                               NextPrev getprev = new NextPrev(getActivity(), paging[0][0], "user", false, radapter, resultlist, prev, next, cachedvalue, title);
                               getprev.execute();
                           }

                       }

                   });
                   if (hasprev[0] == true)
                       prev.setEnabled(true);
                   else
                       prev.setEnabled(false);


                   next.setOnClickListener(new Button.OnClickListener() {
                       public void onClick(View v) {

                           {
                               NextPrev getnext = new NextPrev(getActivity(), paging[0][1], "user", true, radapter, resultlist, prev, next, cachedvalue, title);
                               getnext.execute();
                           }
                       }

                   });
                   if (hasnext[0] == true)
                       next.setEnabled(true);
                   else
                       next.setEnabled(false);
               }
            //curPage = users[CURRENT];
            radapter = new resultAdapter(mcontext,userobj,0,cachedvalue,title);
            resultlist.setAdapter(radapter);

        }
        else if(content == "page")
        {

            JSONArray pageary = null;
            fbObj[] pageobj = null;
            JSONObject pagepaging = null;
            try{
                if(pagejson.has("data"))
                {
                    pageary = pagejson.getJSONArray("data");
                    if(pageary!=null)
                        pageobj = new fbObj[pageary.length()];
                }
                if(pagejson.has("paging"))
                    pagepaging = pagejson.getJSONObject("paging");


            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(pageary!=null)
                for(int count =0;count<=pageary.length()-1;count++)
                {
                    pageobj[count] = new fbObj();
                    pageobj[count].setType("page");
                    pageobj[count].setProfile(pageary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                    pageobj[count].setName(pageary.getJSONObject(count).getString("name"));
                    pageobj[count].setId(pageary.getJSONObject(count).getString("id"));
                    pageobj[count].setFavorite(false);
                }
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(pagepaging!=null&&pagepaging.has("previous"))
                {
                    paging[1][0] = pagepaging.getString("previous");
                    hasprev[1] = true;
                }
                else{
                    hasprev[1] = false;
                    paging[1][0] ="";
                }

                if(pagepaging!=null&&pagepaging.has("next"))
                {
                    paging[1][1] = pagepaging.getString("next");
                    hasnext[1] = true;
                }
                else{
                    hasnext[1] = false;
                    paging[1][1] ="";
                }
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            curPage = pages = pageobj;
            if(prev!=null && next!=null) {
                prev.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getprev = new NextPrev(getActivity(), paging[1][0], "page", false, radapter, resultlist, prev, next, cachedvalue, title);
                            getprev.execute();
                        }

                    }

                });
                if (hasprev[1] == true)
                    prev.setEnabled(true);
                else
                    prev.setEnabled(false);


                next.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getnext = new NextPrev(getActivity(), paging[1][1], "page", true, radapter, resultlist, prev, next, cachedvalue, title);
                            getnext.execute();
                        }
                    }

                });
                if (hasnext[1] == true)
                    next.setEnabled(true);
                else
                    next.setEnabled(false);
                //curPage = users[CURRENT];
            }
            radapter = new resultAdapter(mcontext,pageobj,1,cachedvalue,title);
            resultlist.setAdapter(radapter);

        }
        else if(content == "event")
        {

            JSONArray eventary = null;
            fbObj[] eventobj = null;
            JSONObject eventpaging = null;
            try{
                if(eventjson.has("data"))
                {
                    eventary = eventjson.getJSONArray("data");
                    if(eventary!=null)
                        eventobj = new fbObj[eventary.length()];
                }
                if(eventjson.has("paging"))
                    eventpaging = eventjson.getJSONObject("paging");


            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(eventary!=null)
                for(int count =0;count<=eventary.length()-1;count++)
                {
                    eventobj[count] = new fbObj();
                    eventobj[count].setType("event");
                    eventobj[count].setProfile(eventary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                    eventobj[count].setName(eventary.getJSONObject(count).getString("name"));
                    eventobj[count].setId(eventary.getJSONObject(count).getString("id"));
                    eventobj[count].setFavorite(false);
                }
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(eventpaging!=null&&eventpaging.has("previous"))
                {
                    paging[2][0] = eventpaging.getString("previous");
                    hasprev[2] = true;
                }
                else{
                    hasprev[2] = false;
                    paging[2][0] ="";
                }

                if(eventpaging!=null&&eventpaging.has("next"))
                {
                    paging[2][1] = eventpaging.getString("next");
                    hasnext[2] = true;
                }
                else{
                    hasnext[2] = false;
                    paging[2][1] ="";
                }
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            curPage = events = eventobj;
            if(prev!=null && next!=null) {
                prev.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getprev = new NextPrev(getActivity(), paging[2][0], "event", false, radapter, resultlist, prev, next, cachedvalue, title);
                            getprev.execute();
                        }

                    }

                });
                if (hasprev[2] == true)
                    prev.setEnabled(true);
                else
                    prev.setEnabled(false);


                next.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getnext = new NextPrev(getActivity(), paging[2][1], "event", true, radapter, resultlist, prev, next, cachedvalue, title);
                            getnext.execute();
                        }
                    }

                });
                if (hasnext[2] == true)
                    next.setEnabled(true);
                else
                    next.setEnabled(false);
                //curPage = users[CURRENT];
            }
            radapter = new resultAdapter(mcontext,eventobj,2,cachedvalue,title);
            resultlist.setAdapter(radapter);

        }
        else if(content == "place")
        {

            JSONArray placeary = null;
            fbObj[] placeobj = null;
            JSONObject placepaging = null;
            try{
                if(placejson.has("data"))
                {
                    placeary = placejson.getJSONArray("data");
                    if(placeary!=null)
                        placeobj = new fbObj[placeary.length()];
                }
                if(placejson.has("paging"))
                    placepaging = placejson.getJSONObject("paging");


            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(placeary!=null)
                for(int count =0;count<=placeary.length()-1;count++)
                {
                    placeobj[count] = new fbObj();
                    placeobj[count].setType("place");
                    placeobj[count].setProfile(placeary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                    placeobj[count].setName(placeary.getJSONObject(count).getString("name"));
                    placeobj[count].setId(placeary.getJSONObject(count).getString("id"));
                    placeobj[count].setFavorite(false);
                }
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(placepaging!=null && placepaging.has("previous"))
                {
                    paging[3][0] = placepaging.getString("previous");
                    hasprev[3] = true;
                }
                else{
                    hasprev[3] = false;
                    paging[3][0] ="";
                }

                if(placepaging!=null && placepaging.has("next"))
                {
                    paging[3][1] = placepaging.getString("next");
                    hasnext[3] = true;
                }
                else{
                    hasnext[3] = false;
                    paging[3][1] ="";
                }
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            curPage = places = placeobj;
            if(prev!=null && next!=null) {
                prev.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getprev = new NextPrev(getActivity(), paging[3][0], "place", false, radapter, resultlist, prev, next, cachedvalue, title);
                            getprev.execute();
                        }

                    }

                });
                if (hasprev[3] == true)
                    prev.setEnabled(true);
                else
                    prev.setEnabled(false);


                next.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getnext = new NextPrev(getActivity(), paging[3][1], "place", true, radapter, resultlist, prev, next, cachedvalue, title);
                            getnext.execute();
                        }
                    }

                });
                if (hasnext[3] == true)
                    next.setEnabled(true);
                else
                    next.setEnabled(false);
                //curPage = users[CURRENT];
            }
            radapter = new resultAdapter(mcontext,placeobj,3,cachedvalue,title);
            resultlist.setAdapter(radapter);

        }
        else if(content == "group")
        {

            JSONArray groupary = null;
            fbObj[] groupobj = null;
            JSONObject grouppaging = null;
            try{
                if(groupjson.has("data"))
                {
                    groupary = groupjson.getJSONArray("data");
                    if(groupary!=null)
                        groupobj = new fbObj[groupary.length()];
                }
                if(groupjson.has("paging"))
                    grouppaging = groupjson.getJSONObject("paging");


            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(groupary!=null)
                for(int count =0;count<=groupary.length()-1;count++)
                {
                    groupobj[count] = new fbObj();
                    groupobj[count].setType("group");
                    groupobj[count].setProfile(groupary.getJSONObject(count).getJSONObject("picture").getJSONObject("data").getString("url"));
                    groupobj[count].setName(groupary.getJSONObject(count).getString("name"));
                    groupobj[count].setId(groupary.getJSONObject(count).getString("id"));
                    groupobj[count].setFavorite(false);
                }
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                if(grouppaging!=null&&grouppaging.has("previous"))
                {
                    paging[4][0] = grouppaging.getString("previous");
                    hasprev[4] = true;
                }
                else{
                    hasprev[4] = false;
                    paging[4][0] ="";
                }

                if(grouppaging!=null&&grouppaging.has("next"))
                {
                    paging[4][1] = grouppaging.getString("next");
                    hasnext[4] = true;
                }
                else{
                    hasnext[4] = false;
                    paging[4][1] ="";
                }
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            curPage = groups = groupobj;
            if(prev!=null && next!=null) {
                prev.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getprev = new NextPrev(getActivity(), paging[4][0], "group", false, radapter, resultlist, prev, next, cachedvalue, title);
                            getprev.execute();
                        }

                    }

                });
                if (hasprev[4] == true)
                    prev.setEnabled(true);
                else
                    prev.setEnabled(false);


                next.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        {
                            NextPrev getnext = new NextPrev(getActivity(), paging[4][1], "group", true, radapter, resultlist, prev, next, cachedvalue, title);
                            getnext.execute();
                        }
                    }

                });
                if (hasnext[4] == true)
                    next.setEnabled(true);
                else
                    next.setEnabled(false);
                //curPage = users[CURRENT];
            }
            radapter = new resultAdapter(mcontext,groupobj,4,cachedvalue,title);
            resultlist.setAdapter(radapter);

        }

        return view;
    }




}
