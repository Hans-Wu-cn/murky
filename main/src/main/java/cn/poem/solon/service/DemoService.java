package cn.poem.solon.service;

import cn.poem.solon.entity.Demo;

public interface DemoService  {
    Demo findById(Long id);

    int insert(Demo demo);
}
