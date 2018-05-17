package cn.fintecher.file.server.fileservice.controller;


import cn.fintecher.file.server.fileservice.service.FileService;
import cn.fintecher.filesystem.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastFDS控制器
 *
 * @author wangquan
 */
@RestController
@RequestMapping(value = "/file")
public class FastFDSController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload")
    public ResponseEntity<Message> upload(MultipartFile file) throws Exception {
        return fileService.fileUpload(file);
    }

    /**
     * 文件下载
     * @param fullPath  文件上传返回的文件保存完整地址
     * @param fileName  文件名称
     * @return
     */
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(String fullPath, String fileName) throws Exception {
        return fileService.fileDownLoad(fullPath, fileName);
    }
}