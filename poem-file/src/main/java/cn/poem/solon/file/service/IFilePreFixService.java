package cn.poem.solon.file.service;

public interface IFilePreFixService {
    default String getPreFix(){
        return "";
    }
}
