package cn.poem.solon.admin.service;

import cn.poem.solon.admin.entity.Demo;

public interface DemoService  {
    Demo findById(Long id);

    int insert(Demo demo);
}
