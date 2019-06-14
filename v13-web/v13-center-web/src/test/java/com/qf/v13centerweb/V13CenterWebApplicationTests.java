package com.qf.v13centerweb;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13CenterWebApplicationTests {
    @Autowired
    private FastFileStorageClient client;
    @Test
    public void contextLoads() throws FileNotFoundException {
        File file = new File("D:\\IdeaProjects\\v13\\v13-web\\v13-center-web\\4.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        StorePath storePath = client.uploadFile(fileInputStream, file.length(), "jpg", null);
        System.out.println(storePath.getFullPath());
    }

}
