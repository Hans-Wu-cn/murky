package cn.poem.solon.file.service;


import cn.poem.solon.utils.ApiResult;
import org.noear.solon.core.handle.UploadedFile;

/**
 * 文件Service
 *
 * @Author hans
 */
public interface IFileService {
    /**
     * 上传文件
     *
     * @return
     */
    ApiResult<?> upload(UploadedFile file);
}
