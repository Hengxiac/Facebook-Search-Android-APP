package hengxiac.fbsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by hengxiang1 on 2017/4/27.
 */

public class expandAdapter extends BaseExpandableListAdapter {

    private ArrayList<HashMap<String, Object>> dataset = new ArrayList<HashMap<String, Object>>();
    private JSONArray albums;
    private Context context;
    expandAdapter(Context context , JSONArray albums){
        this.context = context;
        this.albums = albums;

        if(albums == null)
        {
            dataset = null;
        }
        else
        {

            try{
                for(int i =0;i<= albums.length()-1;i++)
                {
                    HashMap<String,Object> info = new HashMap<String,Object>();
                    info.put("name",albums.getJSONObject(i).getString("name"));
                    if(albums.getJSONObject(i).has("photos"))
                    {
                         ArrayList<String>pictures = new ArrayList<String>();
                         JSONArray temp = albums.getJSONObject(i).getJSONObject("photos").getJSONArray("data");
                         for(int j = 0;j<=temp.length()-1;j++)
                         {
                             if(temp.getJSONObject(j).has("id"))
                                pictures.add(temp.getJSONObject(j).getString("id"));
                         }
                         info.put("pictures",pictures);
                    }
                    dataset.add(info);
                }
            }
            catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
    }

    @Override
    public Object getChild(int parentPos, int childPos) {
        ArrayList<String> photo_id = (ArrayList<String>)dataset.get(parentPos).get("pictures");
        return photo_id.get(childPos);
    }


    @Override
    public int getGroupCount() {
        return dataset.size();
    }


    @Override
    public int getChildrenCount(int parentPos) {
        ArrayList<String> photo_id = (ArrayList<String>)dataset.get(parentPos).get("pictures");
        return photo_id.size();
    }


    @Override
    public Object getGroup(int parentPos) {
        return dataset.get(parentPos);
    }


    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }


    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.album_header,viewGroup,false);
        TextView header = (TextView) view.findViewById(R.id.album_header);
        header.setText((String)dataset.get(parentPos).get("name"));
        return view;
    }


    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.album_content,viewGroup,false);
        ImageView content = (ImageView) view.findViewById(R.id.album_content);
        //"https://graph.facebook.com/v2.8/"+id+"/picture?access_token=EAAC6vcfHGaEBAOZAZAXsoZBdHMJe7SU6s2qpKV2QmpZCTZAH9r1e2drz59ItUclvXpsDk9ySwgIhJAx3dS51qIWzypZA7C9Tq7vhFutdofJfS6JzGYZALQOFJUULq7B4ZAOBI0MJn49PpXPvjn8OoznPSmidZCRq3l2IlMOFxc55ZBygZDZD"

        String id = (String)getChild(parentPos,childPos);
        String imgurl =  "https://graph.facebook.com/v2.8/"+id+"/picture?access_token=EAAC6vcfHGaEBAOZAZAXsoZBdHMJe7SU6s2qpKV2QmpZCTZAH9r1e2drz59ItUclvXpsDk9ySwgIhJAx3dS51qIWzypZA7C9Tq7vhFutdofJfS6JzGYZALQOFJUULq7B4ZAOBI0MJn49PpXPvjn8OoznPSmidZCRq3l2IlMOFxc55ZBygZDZD";
        DownloadImage downimg = new DownloadImage(imgurl,childPos,content);
        downimg.execute();
        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
