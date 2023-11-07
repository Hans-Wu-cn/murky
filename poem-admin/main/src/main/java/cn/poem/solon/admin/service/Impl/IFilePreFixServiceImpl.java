package cn.poem.solon.admin.service.Impl;

import cn.poem.solon.file.service.IFilePreFixService;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;


/**
 * 文件路径前缀Service
 */
@Component
public class IFilePreFixServiceImpl implements IFilePreFixService {

    @Inject("${solon.cloud.minio.file.prefix}")
    private String prefix;
    @Override
    public String getPreFix(){
        return prefix;
    }
}
