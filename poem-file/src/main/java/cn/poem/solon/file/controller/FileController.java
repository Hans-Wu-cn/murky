package cn.poem.solon.file.controller;

import cn.poem.solon.file.service.IFileService;
import cn.poem.solon.utils.ApiResult;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Post;
import org.noear.solon.core.handle.UploadedFile;

/**
 * 文件上传接口
 */
@Controller
@Mapping("file")
public class FileController {

    @Inject
    private IFileService iFileService;

    @Post
    @Mapping("/upload")
    public ApiResult<?> upload(UploadedFile file) { //表单变量名要跟参数名对上
        return iFileService.upload(file);
    }
}
