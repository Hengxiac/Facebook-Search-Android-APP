package hengxiac.fbsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hengxiang1 on 2017/4/26.
 */

public class detailFragment extends Fragment {
    private  Context mContext;
    private final static String TYPE = "ARGS_TYPE";
    private int pagenum;
    private JSONObject resultobj;
    private JSONArray  albumarry;
    private JSONArray  postarry;
    private String id;
    private String profile;
    private String name;
    public static detailFragment newInstance(int position) {
        Bundle type = new Bundle();
        type.putInt(TYPE, position);
        detailFragment fragment = new detailFragment();
        fragment.setArguments(type);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagenum = getArguments().getInt(TYPE);
        try {
            resultobj = new JSONObject(getActivity().getIntent().getStringExtra("RESULT"));
            if(resultobj.has("albums"))
            {
                JSONObject temp = resultobj.getJSONObject("albums");
                if(temp.has("data")){
                albumarry = temp.getJSONArray("data");
                }else{
                    albumarry = null;
                }
            }
            else
                albumarry =null;
            if(resultobj.has("posts")){
                JSONObject temp = resultobj.getJSONObject("posts");
                if(temp.has("data")) {
                    postarry = temp.getJSONArray("data");
                }
                else{
                    postarry = null;
                }
            }
            else
                postarry = null;
        }  catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        id = getActivity().getIntent().getStringExtra("id");
        name = getActivity().getIntent().getStringExtra("name");
        profile = getActivity().getIntent().getStringExtra("profile");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        detailAdapter detailadapter;
        expandAdapter exadapter;
        if(pagenum == 0)
        {
            if(albumarry!=null) {
                View view = inflater.inflate(R.layout.albumview, container, false);
                final ExpandableListView detaillist = (ExpandableListView) view.findViewById(R.id.expandablelistview);
                exadapter = new expandAdapter(mContext, albumarry);
                detaillist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPos) {
                        int count = detaillist.getCount();
                        for (int j = 0; j <= count - 1; j++) {
                            if (j != groupPos) {
                                detaillist.collapseGroup(j);
                            }
                        }
                    }
                });
                detaillist.setAdapter(exadapter);
                //detailadapter = new detailAdapter(mContext,albumarry,pagenum);
                return view;
            }
            else{
                View view = inflater.inflate(R.layout.puretext, container, false);
                TextView text = (TextView) view.findViewById(R.id.no_post_album);
                text.setText("No albums has been found!");
                return view;
            }
        }
        else
        {
            if(postarry!=null) {
                View view = inflater.inflate(R.layout.details, container, false);
                ListView detaillist = (ListView) view.findViewById(R.id.detaillist);
                detailadapter = new detailAdapter(mContext, postarry, pagenum, name, profile);
                detaillist.setAdapter(detailadapter);
                return view;
            }
            else{
                View view = inflater.inflate(R.layout.puretext, container, false);
                TextView text = (TextView) view.findViewById(R.id.no_post_album);
                text.setText("No Posts has been found!");
                return view;
            }
        }



    }

}
