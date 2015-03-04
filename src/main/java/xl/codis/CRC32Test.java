package xl.codis;

import java.util.zip.CRC32;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class CRC32Test {

	private static final String BASEPATH = "/zk/codis/db_test/slots/slot_";

	public static void main(String[] args) {
		ZKConnection zkConnection  = new ZKConnection();
    	try {
			zkConnection.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ZooKeeper zk = zkConnection.getZk();
		for(int i = 0 ; i < 100 ;i++ ) {
			//getSlotInfo(zk,"foo"+i);
		}
		
		getSlotInfo(zk,"foo"+1);
		getSlotInfo(zk,"foo"+2);
		getSlotInfo(zk,"foo"+1099);
		getSlotInfo(zk,"foo"+1439);
		getSlotInfo(zk,"first");
		getSlotInfo(zk,"second");
		getSlotInfo(zk,"third");
	}

	public static void getSlotInfo(ZooKeeper zk,String str) {
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		long slot =  crc32.getValue() % 1024;//1.获取该key所在的slot
		try {
			byte[] data = zk.getData(BASEPATH+slot, null, null);
			String s = new String(data);//2.获取该slot所在的server group
			int i = s.indexOf("\"group_id\"");
			int end = s.indexOf(",", i);
			System.out.println(str+":"+s.substring(i, end));
			
			//System.out.println(str+":"+s);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
