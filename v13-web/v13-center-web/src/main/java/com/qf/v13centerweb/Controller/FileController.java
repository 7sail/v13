package com.qf.v13centerweb.Controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.v13.common.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("file")

public class FileController {
    @Autowired
    private FastFileStorageClient client;
    @Value("${image.server}")
    private String imageServer;
    @PostMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file){
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        StorePath storePath = null;
        try {
            storePath = client.uploadFile(file.getInputStream(), file.getSize(), extName, null);
            String path = new StringBuilder(imageServer).append(storePath.getFullPath()).toString();
            System.out.println(storePath);
            return new ResultBean("200",path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean("404","你的网络有问题");

    }
}
