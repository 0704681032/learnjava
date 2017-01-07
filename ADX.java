import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by jinyangyang on 06/01/2017 8:24 PM.
 */
public class ADX {

    private static CloseableHttpAsyncClient httpclient ;

    static {
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor();
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        cm.setMaxTotal(100);
         httpclient = HttpAsyncClients.custom()
                .setConnectionManager(cm)
                .build();
        httpclient.start();
    }


    public static CompletableFuture<HttpResponse> getResponseFromDsp(String dspurl) { //向dsp发出请求
        final HttpGet httpget = new HttpGet(dspurl);
        //HttpAsyncClient的execute返回的是Future需要转化成CompletableFuture
        CompletableFuture<HttpResponse> promise = new CompletableFuture<HttpResponse>();
        httpclient.execute(httpget, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse response) {
                try {
                    if ( dspurl.contains("baidu") ) { //模拟超时
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("222222:"+dspurl);
                promise.complete(response);
            }
            @Override
            public void failed(final Exception ex) {
                promise.completeExceptionally(ex);
            }
            @Override
            public void cancelled() {
                promise.cancel(true);
            }
        });
        return promise;
    }


    public static void main(String[] args) {

        List<String> dspUrls = new ArrayList<String>();
        dspUrls.add("http://www.baidu.com");
        dspUrls.add("http://www.qidian.com");

//        List<CompletableFuture<HttpResponse>> dspRequests = new ArrayList<CompletableFuture<HttpResponse>>(dspUrls.size());
//        for(String dspUrl : dspUrls ) {
//            dspRequests.add( getResponseFromDsp(dspUrl) );
//        }

        //去掉for循环
        List<CompletableFuture<HttpResponse>> dspRequests = dspUrls.stream().map(ADX::getResponseFromDsp)
                .collect(Collectors.toList());


        CompletableFuture[] requestArr = dspRequests.toArray(new CompletableFuture[dspRequests.size()]);

        Duration duration = Duration.ofMillis(2000); // 留给dsp的超时时间

        // ----------------------> composition <-----------------
        CompletableFuture compose = CompletableFuture.anyOf(
                CompletableFuture.allOf(requestArr),
                failAfter(duration)
        );

//        compose.exceptionally( e ->  {
//            System.out.println("超时");
//            return 1 ;
//        }).thenAccept( o -> {
//            System.out.println("thenAcceptAsync:"+o);
//            //System.out.println(o);
//        });

        try {
            compose.get();
        } catch (InterruptedException e) {
            //e.printStackTrace();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        //去掉for循环
//        List<String> results = new ArrayList<String>(dspRequests.size());
//        for(CompletableFuture<HttpResponse> dspRequest :  dspRequests )  {
//             if ( dspRequest.isDone() ) {
//                 try {
//                     HttpResponse response =  dspRequest.get();
//                     HttpEntity entity = response.getEntity();
//                     String  result = entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
//                     System.out.println("done");
//                     results.add(result.substring(result.length()-50,result.length()));
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 } catch (ExecutionException e) {
//                     e.printStackTrace();
//                 } catch (Exception e) {
//                     e.printStackTrace();
//                 }
//             }
//        }

        List<String> results = dspRequests.stream().filter( dspRequest -> dspRequest.isDone() ).map( dspRequest -> {
            try {
                HttpResponse response =  dspRequest.get();
                HttpEntity entity = response.getEntity();
                String  result = entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
                return result ;
            } catch (InterruptedException e  ) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }).filter( s -> StringUtils.isNotEmpty(s) ).collect(Collectors.toList());


        System.out.println(results.size());
        System.out.println("=======end======");
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static <T> CompletableFuture<T> within(CompletableFuture<T> future, Duration duration) {
        final CompletableFuture<T> timeout = failAfter(duration);
        return future.applyToEither(timeout, Function.identity());
    }

    public static <T> CompletableFuture<T> failAfter(Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            final TimeoutException ex = new TimeoutException("Timeout after " + duration);
            return promise.completeExceptionally(ex);
        }, duration.toMillis(), MILLISECONDS);
        return promise;
    }

    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(
                    1,
                    new ThreadFactoryBuilder()
                            .setDaemon(true)
                            .setNameFormat("failAfter-%d")
                            .build());

}
