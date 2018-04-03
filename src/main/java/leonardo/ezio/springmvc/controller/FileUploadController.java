package leonardo.ezio.springmvc.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.rmi.server.ServerCloneException;

@Controller
public class FileUploadController {

    public FileUploadController() {
        System.out.println("FileUploadController ....init ");
    }

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private  StorageClient storageClient;

    /*@ResponseBody
    @PostMapping(value = "/fileUpload")
    public String fileUpload(
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws Exception{
        if (!file.isEmpty()) {
            ClientGlobal.init("src/fastdfs_conf.conf");
            TrackerClient client = new TrackerClient();
            TrackerServer tracker = client.getConnection();
            StorageClient storageClient = new StorageClient(tracker,null);
            storageClient.upload_file()
            String path = this.servletContext.getRealPath("/upload");
            String filename = file.getOriginalFilename();
            System.out.println("OriginalFilename :"+filename);
            if (name != null && !name.equals("")){
                String fileType = filename.substring(filename.lastIndexOf("."));
                filename = name + fileType;
            }
            System.out.println("after filename : "+filename);
            System.out.println(path+"/"+filename);
            File newFile = new File(path + "/" + filename);
            System.out.println("path : "+path);
            try {
                InputStream inputStream = file.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(newFile);
                byte[] arr = new byte[1024];
                int len;
                while ((len = inputStream.read(arr))!=-1){
                    outputStream.write(arr,0,len);
                }
                outputStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "FileUpload Successful";
        }
        return "FileUpload Fail";
    }*/

    @ResponseBody
    @PostMapping(value = "/fileUpload")
    public String fileUpload(
            /*@RequestParam("name") String name,*/
            @RequestParam("file") MultipartFile [] files) throws Exception{
            for (MultipartFile file : files){
                if (!file.isEmpty()) {
                    String filename = file.getOriginalFilename();
                    String fileType = filename.substring(filename.lastIndexOf(".")+1);
                    System.out.println("fileType"+fileType);
                    String[] strings = storageClient.upload_file(file.getBytes(), fileType, null);
                    StringBuffer storagePath = new StringBuffer();
                    for (String s : strings){
                        storagePath.append(s);
                    }
                    String fileLocation = storagePath.toString().substring(6);
                    String storageGroup = storagePath.substring(0,6);
                    System.out.println("fileAccessLocation : "+fileLocation);
                    System.out.println("storageGroup : "+storageGroup);
                    System.out.println(storagePath.toString());
                }
            }
        return "FileUpload Fail";
    }

    @RequestMapping("/download")
    @ResponseBody
    public String fileDownLoad(String group,String filename){
        System.out.println("downLoad............");
        System.out.println("group : "+group);
        System.out.println("filename ： "+filename);
        try {
            byte[] bytes = storageClient.download_file(group,filename);
            System.out.println(bytes == null);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            int i = 0;
            FileOutputStream os = new FileOutputStream("D://1.jpg");
            while ((i = bais.read())!=-1){
                os.write(i);
            }
            os.flush();
            os.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return "downLoadsuccessful";
    }
}
