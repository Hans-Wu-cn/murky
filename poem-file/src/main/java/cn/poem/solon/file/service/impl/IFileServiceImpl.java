package cn.poem.solon.file.service.impl;

import cn.poem.solon.file.service.IFilePreFixService;
import cn.poem.solon.file.service.IFileService;
import cn.poem.solon.utils.ApiResult;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.model.Media;
import org.noear.solon.core.handle.Result;
import org.noear.solon.core.handle.UploadedFile;

/**
 * 文件Service
 *
 * @Author hans
 */
@Component
public class IFileServiceImpl implements IFileService {

    @Inject
    private IFilePreFixService iFilePreFixService;
    /**
     * 上传文件
     *
     * @return
     */
    @Override
    public ApiResult<?> upload(UploadedFile file) {
        // 文件路径获取方式
        String key = iFilePreFixService.getPreFix() + Utils.guid();
        // 上传媒体
        Result<?> result = CloudClient.file().put(key, new Media(file.getContent()));
        return toApiResult(result);
    }


    private ApiResult<?> toApiResult(Result<?> result){
        return new ApiResult().setCode(result.getCode())
                .setMessage(result.getDescription())
                .setResult(result.getData());
    }
}
