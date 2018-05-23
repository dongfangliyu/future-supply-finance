package cn.fintecher.file.server.fileservice.fastdfs.service.impl;

import cn.fintecher.file.server.filecommon.message.Message;
import cn.fintecher.file.server.filecommon.message.MessageType;
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

    @Autowired
    FastDFSUtils fastDFSUtils;

    /**
     * 文件上传至服务器
     * @param multipartFile
     * @return path
     */
    @Override
    public ResponseEntity<Message> fileUpload(MultipartFile multipartFile) {
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS);
        StorePath storePath = null;
        try {
            storePath =  fastDFSUtils.uploadFile(multipartFile);
            message.setData(storePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == storePath){
            message.setStatus(MessageType.MSG_TYPE_ERROR);
            message.setError("文件上传失败，请稍后重试！");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
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
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS);
        byte [] content = null;
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isBlank(fullPath) || StringUtils.isBlank(fileName)){
            message.setError("文件下载失败，请检查参数是否正确：{fullPath : "+fullPath+", fileName : "+fileName+"}");
        } else {
            StorePath storePath = FastDFSUtils.getStorePath(fullPath);
            try {
                content = fastDFSUtils.downloadFile(storePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null != content ){
            try {
                headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity(content, headers, HttpStatus.CREATED);
        } else {
            message.setError("文件下载失败，请稍后重试！");
        }
        message.setStatus(MessageType.MSG_TYPE_ERROR);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(message, headers, HttpStatus.OK);
    }

    /**
     * 文件删除
     *
     * @param fullPath
     * @return
     */
    @Override
    public ResponseEntity<Message> fileDelete(String fullPath) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        boolean flag = fastDFSUtils.deleteFile(fullPath);
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS);
        if (!flag){
            message.setStatus(MessageType.MSG_TYPE_ERROR);
            message.setError("操作失败！");
        }
        return new ResponseEntity<Message>(message, headers, HttpStatus.OK);
    }

}
