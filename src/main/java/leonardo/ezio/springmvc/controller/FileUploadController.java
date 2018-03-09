package leonardo.ezio.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.rmi.server.ServerCloneException;

@Controller
public class FileUploadController {

    @Autowired
    private ServletContext servletContext;

    public FileUploadController() {
        System.out.println("FileUploadController ....init ");
    }

    @ResponseBody
    @PostMapping(value = "/fileUpload")
    public String fileUpload(
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
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
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        System.out.println("test....");
        return "Test  Successful";
    }
}
