package cn.poem.solon.admin.service.Impl;

import cn.poem.solon.file.IFilePreFixService;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.text.MessageFormat;
import java.time.LocalDateTime;


/**
 * 文件路径前缀Service
 */
@Component
public class IFilePreFixServiceImpl implements IFilePreFixService {

    @Inject("${solon.cloud.minio.file.prefix}")
    private String prefix;

    /**
     * 获取文件前缀方法
     * @return 前缀路径
     */
    @Override
    public String getPreFix(){
        LocalDateTime now = LocalDateTime.now();
        StringBuilder path=new StringBuilder(prefix);
        path.append("/");
        path.append(now.getYear());
        path.append("/");
        path.append(now.getMonthValue());
        path.append("/");
        path.append(now.getDayOfMonth());

        return path.toString();
    }

}
