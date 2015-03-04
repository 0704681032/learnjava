
package org.jiacheo.zkdl.lock;
 
import java.net.InetAddress;
import java.net.UnknownHostException;
 
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
 
/**
 * 类名：<b>Lock</b> <br/>
 * <p>
 * 类描述： 
 * </p>
 * 创建人：jiacheo <br/>
 * 创建时间：2011-1-27 上午01:30:25  <br/>  
 * @version 2011-1-27  
 *
 */
public class Lock {
    private String path;
    private ZooKeeper zooKeeper;
    public Lock(String path){
        this.path = path;
    }
     
    /**
     * <p>
     * 方法描述: 上锁 lock it
     * </p>
     * 创建人：jiacheo <br/>
     * 创建时间：2011-1-27 上午01:30:50  <br/>
     * @throws Exception
     */
    public synchronized void lock() throws Exception{
        Stat stat = zooKeeper.exists(path, true);
        String data = InetAddress.getLocalHost().getHostAddress()+":lock";
        zooKeeper.setData(path, data.getBytes(), stat.getVersion());
    }
     
    /**
     * <p>
     * 方法描述：开锁 unlock it
     * </p>
     * 创建人：jiacheo <br/>
     * 创建时间：2011-1-27 上午01:31:20  <br/>
     * @throws Exception
     */
    public synchronized void unLock() throws Exception{
        Stat stat = zooKeeper.exists(path, true);
        String data = InetAddress.getLocalHost().getHostAddress()+":unlock";
        zooKeeper.setData(path, data.getBytes(), stat.getVersion());
    }
     
    /**
     * <p>
     * 方法描述：是否锁住了, isLocked?
     * </p>
     * 创建人：jiacheo <br/>
     * 创建时间：2011-1-27 上午01:31:43  <br/>
     * @return
     */
    public synchronized boolean isLock(){
        try {
            Stat stat = zooKeeper.exists(path, true);
            String data = InetAddress.getLocalHost().getHostAddress()+":lock";
            String nodeData = new String(zooKeeper.getData(path, true, stat));
            if(data.equals(nodeData)){
//              lock = true;
                return true;
            }
        } catch (UnknownHostException e) {
            // ignore it
        } catch (KeeperException e) {
            //TODO use log system and throw a new exception
        } catch (InterruptedException e) {
            // TODO use log system and throw a new exception
        }
        return false;
    }
 
    public String getPath() {
        return path;
    }
 
    public void setPath(String path) {
        this.path = path;
    }
 
    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
     
     
}