package xl.codis;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.zip.CRC32;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;

//TODO crc32
//-- 配置maxMemory

//TODO 服务器是使用了redis哪些命令 什么数据结构

//jinyangyang(金阳阳) 10:02:41
//骚年 你redis用了哪些命令啊
//linzhangkai(林章楷) 10:05:31
//set get hset hget hgetall expire keys

//测试是否在codis工作正常

//现在服务器点频次控制的订单多吗？

//通过以下公式确定所属的 Slot Id : SlotId = crc32(key) % 1024 每一个 slot 
//都会有一个特定的 server group id 来表示这个 slot 的数据由哪个 server group 来提供.

//about zookeeper
//if you want to setup zookeeper cluster, please refs to https://zookeeper.apache.org/doc/r3.3.3/zookeeperAdmin.html

//"10.11.8.20:2181" is zookeeper's address.
//also you can add zookeeper cluster like:
//new RoundRobinJedisPool("10.11.8.20:2181, 10.11.8.21:2181, 10.11.8.22:2181")

//about codis-proxy
//just start a new proxy and mark it online through dashboard

public class App 
{	
	private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main( String[] args )
    {
    	
    	//test1();
    	//test2();
    	test2();
    	try {
			countDownLatch.await(); //防止程序退出
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    public static void test3() {
   	 //连接zookeeper 
   	 //JedisResourcePool jedisPool = new RoundRobinJedisPool("10.10.159.164:2181", 30000, "/zk/codis/db_test/proxy", new JedisPoolConfig());
   	 
   	JedisResourcePool jedisPool = new RoundRobinJedisPool("10.10.159.165:2181,10.10.159.164:2181,10.11.8.20:2181", 30000, "/zk/codis/db_test/proxy", new JedisPoolConfig());
        try {
        	Jedis jedis = jedisPool.getResource();
        	
        	//initData(jedis);
        	
        	//testCRC32();
        	
        	System.out.println( jedis.get("foo1099"));
        	System.out.println( jedis.get("foo1439"));
        	
        	//System.out.println( jedis.get("handsom"));
        	//System.out.println( jedis.get("beauty"));
            //String value = jedis.get("foo5");
            //System.out.println(value);
        }finally {
        	try {
				jedisPool.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
   }
    
    public static void test2() {
    	
    	Jedis jedis =  new Jedis("10.11.8.20", 19000);//直接连接codis-proxy,注意这里的端口不是6379
    	System.out.println("2:"+jedis.get("foo1099"));
     	System.out.println("2:"+jedis.get("foo1439"));
     	System.out.println("2:"+jedis.get("foo1"));
     	System.out.println("2:"+jedis.get("foo2"));
     	//jedis.set("java4", "oracle4");
     	
//     	Jedis jedis2 =  new Jedis("10.10.159.164", 19000);//直接连接codis-proxy,注意这里的端口不是6379
//    	System.out.println("3:"+jedis2.get("foo1099"));
//     	System.out.println("3:"+jedis2.get("foo1439"));
//    	System.out.println("3:"+jedis2.get("foo1"));
//     	System.out.println("3:"+jedis2.get("foo2"));
//     	System.out.println("3:"+jedis2.get("java4"));
     	/*
    	System.out.println("3:"+jedis2.get("java"));//实际运行情况获取到了java
    	System.out.println("3:"+jedis2.get("java1"));
    	*/
    	
    	 JedisResourcePool jedisPool = new RoundRobinJedisPool("10.10.159.165:2181,10.10.159.164:2181,10.11.8.20:2181", 30000, "/zk/codis/db_test/proxy", new JedisPoolConfig());
         try {
         	Jedis jedis3 = jedisPool.getResource();
        	System.out.println("5:"+jedis3.get("foo1099"));
         	System.out.println("5:"+jedis3.get("foo1439"));
        	System.out.println("5:"+jedis3.get("foo1"));
         	System.out.println("5:"+jedis3.get("foo2"));
        	System.out.println("5:"+jedis3.get("java4"));
        	//System.out.println("4:"+jedis3.get("java"));//实际运行情况获取到了java
        	//System.out.println("4:"+jedis3.get("java1"));
        	
         } catch (Exception e) {
        	 
         }
    }
    
    public static void test1() {
    	 //连接zookeeper 
    	 JedisResourcePool jedisPool = new RoundRobinJedisPool("10.11.8.20:2181", 30000, "/zk/codis/db_test/proxy", new JedisPoolConfig());
         try {
         	Jedis jedis = jedisPool.getResource();
         	
         	initData(jedis);
         	
         	//testCRC32();
         	
         	System.out.println( jedis.get("foo1099"));
         	System.out.println( jedis.get("foo1439"));
         	
         	//System.out.println( jedis.get("handsom"));
         	//System.out.println( jedis.get("beauty"));
             //String value = jedis.get("foo5");
             //System.out.println(value);
         }finally {
         	try {
 				jedisPool.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
         }
    }
    
    public static void testCRC32() {
    	CRC32 crc32 = new CRC32();
    	crc32.update("foo1246".getBytes());
    	System.out.println(crc32.getValue()%1024);
    }

	private static void initData(Jedis jedis) {
		int i = 0;
		while ( i++ < 5000 ) {
			  String result = jedis.set("foo"+i, "bar"+i);
			  System.out.println(result);
		}
	}
}
