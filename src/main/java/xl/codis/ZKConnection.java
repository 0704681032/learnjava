package xl.codis;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZKConnection {
    /**
     * server列表, 以逗号分割
     */
    protected String hosts = "10.10.159.165:2181,10.10.159.164:2181,10.11.8.20:2181";
    /**
     * 连接的超时时间, 毫秒
     */
    private static final int SESSION_TIMEOUT = 5000;
    private CountDownLatch connectedSignal = new CountDownLatch(1);
    protected ZooKeeper zk;

    public ZooKeeper getZk() {
		return zk;
	}


	/**
     * 连接zookeeper server
     */
    public void connect() throws Exception {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new ConnWatcher());
        // 等待连接完成
        connectedSignal.await();
        System.out.println("connected!");
    }

    public class ConnWatcher implements Watcher {
        public void process(WatchedEvent event) {
            // 连接建立, 回调process接口时, 其event.getState()为KeeperState.SyncConnected
            if (event.getState() == KeeperState.SyncConnected) {
                // 放开闸门, wait在connect方法上的线程将被唤醒
            	System.out.println("event:"+event);
                connectedSignal.countDown();
            }
        }
    }
    
    /**
     * 创建临时节点
     */
    public void create(String nodePath, byte[] data) throws Exception {
        zk.create(nodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    
    public static void main(String[] args) {
    	ZKConnection zkConnection  = new ZKConnection();
    	try {
			zkConnection.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
