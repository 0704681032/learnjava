import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jinyangyang on 18/05/2017 9:47 AM.
 */
public class DynamicConfigurer {


    public static final String BASEDIR = "/Users/jyy/";
    public static final String FILENAME = "a.properties";
    //classpathresource
    //propertiesloaderutils
    // ① 使用系统文件路径方式加载文件
    //static final Properties p = new Properties();

    private static final Map property = new ConcurrentHashMap<String,String>();

    static {
        // ② 使用类路径方式加载文件
        //Resource res2 = new ClassPathResource("conf/file1.txt");
        try {
            Resource res1 = new FileSystemResource(BASEDIR+ FILENAME);
            Properties p1 = PropertiesLoaderUtils.loadProperties(res1);
            //p.clear();
            //p.putAll(p1);
            property.putAll(p1);
            System.out.println(property);
            Path parentDir = Paths.get(res1.getFile().getParent());
            System.out.println(parentDir);
            final WatchService  watchService = FileSystems.getDefault().newWatchService() ;
            parentDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE);
            //启动给一个线程监听内容变化,并重新载入配置
            Thread watchThread = new Thread( () -> {
                while ( true ) {
                    try {
                        System.out.println("here11");
                        WatchKey watchKey = watchService.take();
                        for( WatchEvent event : watchKey.pollEvents() ) {
                            System.out.println(event.context().toString());
                            if ( Objects.equals(event.context().toString(), FILENAME) ) {
                                System.out.println("file changed!!");
                                //TODO 重新读取配置文件
                                try {
                                    Properties p2 = PropertiesLoaderUtils.loadProperties(res1);
                                    //p.clear();
                                    //p.putAll(p2);
                                    property.clear();
                                    property.putAll(p2);
                                    System.out.println("2=>"+property);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                        watchKey.reset();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            watchThread.setDaemon(true);

            watchThread.start();

            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                try {
                    watchService.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception{

        while( true ) {
            Thread.sleep(10000);
        }
    }
}
