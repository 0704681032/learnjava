import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by jinyangyang on 18/05/2017 9:47 AM.
 */
public class DynamicConfigurer {

    static {
        final String filename = "";
        Properties p = null ;
        //classpathresource
        //propertiesloaderutils
        // ① 使用系统文件路径方式加载文件
        //Resource res1 = new FileSystemResource(filePath);
        // ② 使用类路径方式加载文件
        //Resource res2 = new ClassPathResource("conf/file1.txt");
        try {
            Path parentDir = Paths.get(filename);
            final WatchService  watchService = FileSystems.getDefault().newWatchService() ;
            parentDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE);
            //启动给一个线程监听内容变化,并重新载入配置
            Thread watchThread = new Thread( () -> {
                while ( true ) {
                    try {
                        WatchKey watchKey = watchService.take();
                        for( WatchEvent event : watchKey.pollEvents() ) {
                            if ( Objects.equals(event.context().toString(),filename) ) {
                                //TODO 重新读取配置文件
                                Properties p1 = null;
                                p.clear();
                                p.putAll(p1);
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


    }
}
