package cn.fintecher.file.server.fileservice.service;

import cn.fintecher.filesystem.common.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by wangquan on 2018/5/11.
 */
public interface FileService {

    /**
     * 图片上传至服务器
     * @param multipartFile
     * @return
     */
    @ResponseBody
    ResponseEntity<Message> fileUpload(MultipartFile multipartFile);

    /**
     * 文件下载
     * @param fullPath   fastDFS返回的完整文件路径
     * @param fileName   文件名
     * @return
     */
    @ResponseBody
    ResponseEntity fileDownLoad(@RequestParam("fullPath") String fullPath, @RequestParam("fileName") String fileName);

}
