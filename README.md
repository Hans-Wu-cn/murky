# poem-solon
以solon+mybatis-flex为基础的脚手架，用于快速开发项目,基于satoken实现了RBAC权限模型
目前系统仍在开发中,所以不提供演示地址,欢迎贡献代码
### 系统简介

1. 基于MIT协议完整开源
2. 前端采用naive admin开源版本,ui库采用naive ui
3. 后端采用solon + mybaits-flex + satoken 


### 技术选型
 **为什么使用[solon](http://solon.noear.org/)**: solon是一款国产的生态框架同时兼容jdk8、jdk11、jdk17、jdk20,相比springboot拥有更小的包体积，更小的内存占用，更快的启动速度

 **为什么使用[mybaitis-flex](https://mybatis-flex.com/)**: 采用APT技术而不是反射而拥有了更强大的性能,使用QueryWrap无sql模式更加得心应手,不需要在为系统固定某个数据库x.com/)

 **为什么使用pgsql]**: 开源免费,社区强大,性能卓越

> 1.因为内部采用mybatis-flex的queryWrap而不是直接手写sql,所以理论上mybatis-flex支持的库都是支持的,目前开发环境采用的是pgsql:12.15
> 
> 2.系统采用插件模式,可根据需要移除添加插件,既可切换分布式/单体应用,或自己需求添加自己的中间件,仅需要实现扩展接口
> 
> 3.前端使用的naive admin 开源版本,但是仍需要做删减
#### 安装教程

1.  部署数据库运行sql脚本
2.  根据需求选择引入redis或者使用单体
3.  根据需求选择minio或者本地文件系统
4.  前端运行yarn install ,yarn dev 运行
5.  后端直接运行即可


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
