package cn.fintecher.file.server.fileservice.fastdfs.controller;


import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.file.server.fileservice.fastdfs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastFDS控制器
 *
 * @author wangquan
 */
@RestController
@RequestMapping(value = "/fast/file")
public class FastDFSController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
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

    /**
     * 文件删除
     * @param fullPath  文件上传返回的文件保存完整地址
     * @return
     */
    @RequestMapping(value = "/delete")
    public ResponseEntity<Message> delete(String fullPath) throws Exception {
        return fileService.fileDelete(fullPath);
    }
}