package cn.fintecher.file.server.fileservice.fastdfs.service.impl;

import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import cn.fintecher.file.server.fileservice.fastdfs.service.FileService;
import cn.fintecher.file.server.fileservice.fastdfs.utils.FastDFSUtils;
import com.github.tobato.fastdfs.domain.StorePath;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传service
 * Created by wangquan on 2018/5/11.
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final String MESSAGE_TYPE = "fastdfs-file-service";

    @Autowired
    FastDFSUtils fastDFSUtils;

    /**
     * 文件上传至服务器
     * @param multipartFile
     * @return path
     */
    @Override
    public ResponseEntity<Message> fileUpload(MultipartFile multipartFile) {
        Message message = null;
        StorePath storePath = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (null != multipartFile){
            try {
                storePath =  fastDFSUtils.uploadFile(multipartFile);
                message = new Message(MessageType.MSG_SUCCESS, MESSAGE_TYPE, storePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == storePath){
                message = new Message(MessageType.MSG_ERROR, MESSAGE_TYPE, "文件上传失败，请稍后重试！");
            }
        } else {
            message = new Message(MessageType.MSG_ERROR, MESSAGE_TYPE, "文件上传失败，请检查参数是否正确！");
        }
        return new ResponseEntity<Message>(message, headers, HttpStatus.OK);
    }

    /**
     * 文件下载
     * @param fullPath   fastDFS返回的完整文件路径
     * @param fileName   文件名
     * @return
     */
    @Override
    public ResponseEntity fileDownLoad(String fullPath, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        String msg = null;
        if (StringUtils.isBlank(fullPath) || StringUtils.isBlank(fileName)){
            msg = "文件下载失败，请检查参数是否正确：{fullPath : "+fullPath+", fileName : "+fileName+"}";
        } else {
            StorePath storePath = FastDFSUtils.getStorePath(fullPath);
            try {
                byte [] content = fastDFSUtils.downloadFile(storePath);
                headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return new ResponseEntity(content, headers, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
            }
        }
        if (null == msg ){
            msg = "文件下载失败，请稍后重试！";
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(new Message(MessageType.MSG_ERROR, MESSAGE_TYPE, msg), headers, HttpStatus.OK);
    }

    /**
     * 文件删除
     *
     * @param fullPath
     * @return
     */
    @Override
    public ResponseEntity<Message> fileDelete(String fullPath) {
        Message message = new Message(MessageType.MSG_SUCCESS, MESSAGE_TYPE, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String msg = null;
        try {
            if (!fastDFSUtils.deleteFile(fullPath)) {
                msg = "操作失败！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }

        if (null != msg){
            message = new Message(MessageType.MSG_ERROR, MESSAGE_TYPE, msg);
        }
        return new ResponseEntity<Message>(message, headers, HttpStatus.OK);
    }

}
