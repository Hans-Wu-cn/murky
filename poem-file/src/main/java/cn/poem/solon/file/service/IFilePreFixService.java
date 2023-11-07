package cn.poem.solon.file.service;

/**
 * 文件前缀service
 */
public interface IFilePreFixService {

    /**
     * 获取文件前缀方法
     * @return 前缀路径
     */
    default String getPreFix(){
        return "";
    }
}
