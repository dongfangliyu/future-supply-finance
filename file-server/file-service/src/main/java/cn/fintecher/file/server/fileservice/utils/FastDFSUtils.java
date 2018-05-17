package cn.fintecher.file.server.fileservice.utils;

import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * FastDFS 工具类
 * @Data 2018/05/16
 * @Author wq
 */
@Component
public class FastDFSUtils {
    private final Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * fastDFS文件上传
     *
     * @param file 上传的文件 FastDFSFile
     * @return StorePath 返回文件信息
     */
    public StorePath uploadFile(MultipartFile file) {
        StorePath storePath = null;
        try {
            //文件扩展名
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            // 文件描述
            Set<MataData> metaDataSet = new HashSet<MataData>();
            metaDataSet.add(new MataData("fileName", file.getOriginalFilename()));
            metaDataSet.add(new MataData("fileExt", ext));
            metaDataSet.add(new MataData("fileSize", String.valueOf(file.getSize())));

            storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, metaDataSet);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storePath;
    }

    /**
     * fastDFS文件下载
     *
     * @param storePath
     * @return ResponseEntity<byte[]>
     */
    @NotNull
    public byte[] downloadFile(StorePath storePath) {
        return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadCallback<byte[]>() {
            @Override
            public byte[] recv(InputStream inputStream) throws IOException {

                byte[] buffer = null;
                ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
                byte[] b = new byte[512];
                int n;
                while ((n = inputStream.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                inputStream.close();
                bos.close();
                buffer = bos.toByteArray();
                return buffer;
            }
        });
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * 根据fastDFS返回的path获取StorePath
     *
     * @param path fastDFS返回的path
     * @return StorePath
     */
    public static StorePath getStorePath(String path) {
        return StorePath.praseFromUrl(path);
    }

}