import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * http://mp.weixin.qq.com/s?__biz=MzA5Nzc4OTA1Mw==&mid=2659597332&idx=1&sn=f2f6a3f660386aa9a89133db82697770&scene=0#wechat_redirect
 * Created by jinyangyang on 12/11/2016 1:29 PM.
 */
public abstract class BatchExecutor<T> {

    private final BlockingQueue<T> queue = new ArrayBlockingQueue<T>(10000);

    final Consumer c = new Consumer();

    public static void main(String[] args) {

    }

    public BatchExecutor () {
        c.start();
    }

    public abstract void handleMsgs(List<T> msgs);

    public void add(T msg) {
        queue.add(msg);
    }

     class Consumer extends Thread {

         public Consumer() {
             setDaemon(true);
         }

        //List messages = new ArrayList(1000);
        List messages = Lists.newArrayListWithCapacity(100);
        @Override
        public void run() {
            while ( true ) {
                try {
                    Queues.drain(queue,messages,100,1, TimeUnit.MINUTES);
                    if ( !messages.isEmpty() ) {
                        handleMsgs(messages);
                        messages.clear();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //super.run();
        }
    }

}
