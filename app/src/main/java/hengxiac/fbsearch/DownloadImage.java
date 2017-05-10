package hengxiac.fbsearch;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by hengxiang1 on 2017/4/25.
 */

public class DownloadImage extends AsyncTask<String, Void, Drawable> {
        private String imageurl;
        private int index;
        private ImageView imgview;
        DownloadImage(String imgurl,int position, ImageView imgview) {
            imageurl = imgurl;
            index = position;
            this.imgview = imgview;
        }

        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(imageurl);
        }

        protected void onPostExecute(Drawable result) {
            //resultAdapter.setOneimage(result,index,imgview);
            imgview.setImageDrawable(result);
        }

    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
        }

        return drawable ;
    }
}

