package hengxiac.fbsearch;


import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by hengxiang1 on 2017/4/24.
 */

public class Networkable {
    public static String GetJson(String url, String content) {
        try {

            HttpClient httpCient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            //httpGet.addHeader("Connection", "Keep-Alive");

            HttpResponse httpResponse = null;
            try {
                httpResponse = httpCient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");

                return response;
            }
            return "ERROR";

        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
