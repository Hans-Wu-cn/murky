<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">Murky v0.1.0</h1>
<h4 align="center">以solon + mybatis-flex + satoken为基础的脚手架,用于快速开发项目,基于satoken实现了RBAC权限模型的分布式架构,同时支持多租户和对租户端权限的管理,实现各种国际化为互联网出海助力</h4>
<p align="center">

</p>

目前系统仍在开发中,所以不提供演示地址,欢迎贡献代码

### 系统简介

1. 基于MIT协议完整开源
2. 前端采用[TDesign](https://tdesign.tencent.com/vue-next/overview)模板适配
3. 前端技术栈:Vue3 + Ts + Vite + Pina
4. 后端技术栈:Solon + Mybaits-Flex + Satoken + SocketD + DamiBus
5. 数据库使用postgresql,redis
6. jdk支持默认21
7. 系统内置了国际化功能,采用了字典+国际化语言包数据化的管理方式,可以自由拓展需要的语言
8. 菜单采用动态管理的方案
### 功能简介

1. 菜单管理
2. 部门管理
3. 角色管理
4. 用户管理
5. 数据字典
6. 语言包管理
7. 系统配置
8. 多租户管理
9. 租户菜单管理
10. 租户权限组管理

### 系统模块

~~~
cn.murky     
├── murky-ui
│       └── murky-admin      // 前端管理端
│       └── murky-tenant     // 前端商户端
├── murky-admin         // 管理端服务端模块
│       └── murky-admin-server                 // 管理端启动模块
│       └── murky-admin-auth                   // 管理端权限模块 
│       └── murky-admin-library                // 管理端依赖库
│                   └── murky-admin-common             // 管理端通用模块
│                   └── murky-admin-core               // 管理端核心模块
│       └── murky-admin-tenant                 // 管理端租户管理模块
│       └── murky-admin-system                 // 管理端系统管理模块
├── murky-library       // 抽象通用库
│       └── murky-common                       // 抽象公共库
│       └── murky-core                         // 抽象核心库
│       └── murky-flex                         // 抽象mybats-flex库
│       └── murky-redismq                      // 抽象基于redismq库
│       └── murky-security                     // 抽象安全库
│       └── murky-socketd                      // 抽象socketd库
├── murky-tenant          // 租户端服务端模块
│       └── murky-tenant-server                 // 租户端启动模块
│       └── murky-tenant-auth                   // 租户端权限模块 
│       └── murky-tenant-library                // 租户端依赖库
│                   └── murky-tenant-core       // 租户端核心模块
│       └── murky-tenant-tenant                 // 租户端租户管理模块
│       └── murky-tenant-system                 // 租户端系统管理模块
├── pom.xml             // 公共依赖管理声明
~~~

### 开发计划

| 功能规划   | 管理端 | 租户端 |
|--------|-----|-----|
| 文字国际化  | 已完成 | 规划中 |
| 路由国际化  | 已完成 | 规划中 |
| 时间国际化  | 开发中 | 规划中 |
| 多租户    | 已完成 | 开发中 |
| 页面     | 开发中 | 规划中 |
| 数据级权限  | 已完成 | 开发中 |
| 密码加密   | 已完成 | 规划中 |
| 接口数据脱敏 | 规划中 | 规划中 |
| 代码生成   | 规划中 | 规划中 |
| 租户环境隔离 |     | 已完成 |
**租户环境隔离目前数据库采用schema隔离,redis采用按个人需求,可以为租户配置需要隔离的redis的配置,若不配置则采用系统默认的redis**  
#### 文件上传

solon对文件上传做了一些抽象,所以使用不同的文件服务替换很简单,如果需要加入,可以参考文档[http://solon.noear.org/article/family-solon-cloud-file](http://solon.noear.org/article/family-solon-cloud-file)

### 安装教程

1. 部署数据库运行sql脚本
2. 根据需求选择引入redis或者使用单体
3. 前端运行pnpm install ,pnpm run dev运行
4. 后端修改server中的配置文件即可直接运行

### 页面展示

![img.png](img.png)

### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
