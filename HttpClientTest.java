import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by jinyangyang on 07/11/2016 9:41 PM.
 */
public class HttpClientTest {

    public static String httpPostWithJSON(String url,JSONObject jsonObject) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;
        //jsonObject.toString()
        StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

//        表单方式
//        List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
//        pairList.add(new BasicNameValuePair("name", "admin"));
//        pairList.add(new BasicNameValuePair("pass", "123456"));
//        httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
        HttpResponse resp = client.execute(httpPost);
        System.out.println(resp.getStatusLine().getStatusCode());
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
            //System.out.println(respContent);
            JSONObject jsonRes =  (JSONObject) JSONObject.parse(respContent);

            if ( jsonRes.containsKey("ads") ) {
                JSONArray jsonArray = jsonRes.getJSONArray("ads");
                int size = jsonArray.size();
                for( int i = 0 ; i < size ; i++) {
                    JSONObject ad = jsonArray.getJSONObject(i);
                    //System.out.println(ad);
                    JSONObject adsnippet = ad.getJSONObject("adsnippet");
                    if ( adsnippet != null ) {
                       JSONArray pvs =   adsnippet.getJSONArray("pv");
                        String price = "0+hOLLmuR26zKovjUDcrmZuRFvvbyMwMBJlz5A==";
                        price = URLEncoder.encode(price,"utf-8");
                        String pv = pvs.getString(0).replace("p=%%SETTLE_PRICE%%","p="+price);
                        System.out.println(pv);
                        //pv

                        sendPv( pv  );
                        System.out.println(pvs);
                        String link = adsnippet.getString("link");
                        System.out.println(link+"**");
                        link = link.replaceAll("%%CLICK_URL_UNESC%%&url=","");
                        //link = link.trim();
                        link = URLDecoder.decode(link,"utf-8");


                        //link
                        sendPv(link);


                    }
                }
            }

            //System.out.println(jsonRes);
        }
        return respContent;
    }

    public static URI toURI ( String link) throws  Exception{
        URL url1 = new URL(link);
        System.out.println(url1.getQuery());

        String query = URLEncoder.encode(url1.getQuery(),"utf8");

        URI uri = new URI(url1.getProtocol(), "",url1.getHost(),url1.getPort(), url1.getPath(),query, null);

        return uri;
    }


    public static void main(String[] args) throws Exception {
        List<String>  list = IOUtils.readLines( HttpClientTest.class.getResourceAsStream("1.json") ,"utf-8" ) ;
        JSONObject jsonObject = JSON.parseObject(StringUtils.join(list.iterator(),""));
       //System.out.println(jsonObject);
        String result = httpPostWithJSON("xxxxxxxx",jsonObject);
        //System.out.println(result);
    }

    public static void sendPv(String str) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(str);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity,"utf-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            //System.out.println("----------------------------------------");
            //System.out.println(responseBody);
        } finally {
            httpclient.close();
        }



    }

}
