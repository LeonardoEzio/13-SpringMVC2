package leonardo.ezio.springmvc.config;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "leonardo.ezio.springmvc",excludeFilters ={@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)})
public class AppConfig {

    public AppConfig(){
        System.out.println("AppConfig  init.....");
    }

    /*
     配置storageClient
     */
    @Bean
    public StorageClient storageClient(){
        StorageClient storageClient = null;
        try {
            ClientGlobal.init("fastdfs_conf.conf");
            TrackerClient client = new TrackerClient();
            TrackerServer tracker = client.getConnection();
            storageClient = new StorageClient(tracker,null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return storageClient;
    }
}
