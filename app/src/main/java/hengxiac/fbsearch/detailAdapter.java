package hengxiac.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hengxiang1 on 2017/4/26.
 */

public class detailAdapter extends BaseAdapter {

    private Context mContext;
    private JSONArray Albumpost;
    private int type;
    private String name;
    private String profile;

    public detailAdapter(Context context,JSONArray albumpost,int type) {
        mContext = context;
        Albumpost = albumpost;
        this.type = type;
    }

    public detailAdapter(Context context,JSONArray albumpost,int type,String name, String profile) {
        mContext = context;
        //Albumpost = albumpost;
        this.type = type;
        this.name = name;
        this.profile = profile;
        Albumpost = new JSONArray();
        for (int i = 0;i<=albumpost.length()-1;i++) {
            try {
                if(albumpost.getJSONObject(i).has("message"))
                {
                    Albumpost.put(albumpost.getJSONObject(i));
                }
            }
            catch (JSONException e){

            }
        }
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return Albumpost.length();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater;
        inflater = LayoutInflater.from(mContext);
        View row;
        if(type == 0 )//ALbums
        {
            row = inflater.inflate(R.layout.albumview, parent, false);

            return (row);
        }
        else //if(type == 1)  posts
        {
            row = inflater.inflate(R.layout.postview,parent,false);
            ImageView profile = (ImageView)row.findViewById(R.id.profile);
            TextView postername = (TextView) row.findViewById(R.id.names);
            TextView createtime = (TextView)row.findViewById(R.id.time);
            TextView msg = (TextView)row.findViewById(R.id.msgs);

            /*postername.setText("阿知賀");
            createtime.setText("2017-4-26 12:03:34");
            msg.setText("奈良県の山中に暮らす少女・高鴨穏乃は、小学校6年生の時、幼馴染の新子憧と共に当時転校してきたばかりの原村和を「阿知賀こども麻雀クラブ」へと誘う。 \n" +
                    "コーチである赤土晴絵や中学生の松実玄と共に楽しい日々を送るが、晴絵は実業団への転向が決まり、麻雀クラブも解散となった。 \n" +
                    "その後、憧は麻雀の強い晩成高校への進学を見据えて阿太峯中学へ進学し、和も中学1年の年度末には再び転校してしまう。 ");
           */
            if(Albumpost == null)
            {
                msg.setText("No posts has been found!");
            }
            else
            {
                String messages="";
                String create_time="";
                try{
                    if(Albumpost.getJSONObject(position).has("message"))
                    {
                        messages = Albumpost.getJSONObject(position).getString("message");
                        create_time = Albumpost.getJSONObject(position).getString("created_time");
                        create_time = create_time.replace("T"," ");
                        int index = create_time.indexOf("+");
                        create_time = create_time.substring(0,index);

                    }

                }   catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(messages.length()>0)
                {
                    postername.setText(name);
                    createtime.setText(create_time);
                    msg.setText(messages);
                    DownloadImage img = new DownloadImage(this.profile,position,profile);
                    img.execute();
                }
            }
            return (row);
        }



    }

}
