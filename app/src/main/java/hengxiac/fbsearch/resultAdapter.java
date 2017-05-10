package hengxiac.fbsearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by hengxiang1 on 2017/4/23.
 */

public class resultAdapter extends BaseAdapter {

    private String title;
    private static Drawable[] oneimage = new Drawable[10];
    private  Context mContext;
    private int type;
    private String  cachedvalue;
    private  fbObj[] currentpage;
    public resultAdapter(Context context) {
        mContext = context;

    }
    public resultAdapter(Context context, fbObj[] in,int type,String cachedvalue,String title) {
        mContext = context;
        currentpage = in;
        this.type = type;
        this.cachedvalue = cachedvalue;
        this.title = title;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return currentpage.length;
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

            final int pos = position;
            LayoutInflater inflater;
            inflater = LayoutInflater.from(mContext);
            View row;
            row = inflater.inflate(R.layout.resultrow, parent, false);
            TextView name;
            ImageView detailsimg;
            ImageView favimg;
            ImageView profilepic;
            profilepic = (ImageView) row.findViewById(R.id.profile);
            detailsimg = (ImageView) row.findViewById(R.id.detailicon);
            favimg = (ImageView) row.findViewById(R.id.favoriteicon);
            name       = (TextView) row.findViewById(R.id.names);


            if(currentpage[position]!=null) {
                name.setText(currentpage[position].getName()/* + " " + currentpage[position].getType()*/);
                detailsimg.setImageResource(R.drawable.details);

                SharedPreferences sharedpref = mContext.getSharedPreferences("favorite",MODE_PRIVATE);
                if(sharedpref.contains(currentpage[position].getId())) {
                    favimg.setImageResource(R.drawable.favorites_on);
                    currentpage[position].setFavorite(true);
                }
                else {
                    favimg.setImageResource(R.drawable.favorites_off);
                    currentpage[position].setFavorite(false);
                }
                profilepic.setImageResource(R.drawable.albums);
                DownloadImage getimg = new DownloadImage(currentpage[position].getProfile(), position, profilepic
                );
                getimg.execute();

            }
            detailsimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String originurl = ".limit(5){name,photos.limit(2){name,picture}},posts.limit(5)";
                    String url = "https://graph.facebook.com/v2.8/"+currentpage[pos].getId().toString()+"?fields=albums"+android.net.Uri.encode(originurl)+"&access_token=EAAC6vcfHGaEBAOZAZAXsoZBdHMJe7SU6s2qpKV2QmpZCTZAH9r1e2drz59ItUclvXpsDk9ySwgIhJAx3dS51qIWzypZA7C9Tq7vhFutdofJfS6JzGYZALQOFJUULq7B4ZAOBI0MJn49PpXPvjn8OoznPSmidZCRq3l2IlMOFxc55ZBygZDZD";
                    detailinfo deinfo = new detailinfo(mContext,url,currentpage[pos].getId(),currentpage[pos].getProfile(),currentpage[pos].getName(),cachedvalue,type,title);
                    deinfo.execute(url);
                }
            });
            return (row);

    }


}
