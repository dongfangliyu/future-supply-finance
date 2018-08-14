【基础服务】公共基础服务
====================
主工程
-------
### (1)项目描述
        用来提供公共基础服务，比如权限认证、文件服务、基础工具类等。
        
### (2)目录结构说明
        common-utils：公用工具包
        file-server：文件服务；提供文件上传和下载，以及图片文件的动态缩略图功能。
                     服务内部对接的是FastDFS+Nginx构建的分布式高可用文件系统。
        lock-server：分布式锁；目前只是提供针对Redis中间件构建的锁和计数器服务。              
        oauth2-server：权限认证服务
        organize-server：组织服务
### (3)开发环境，部署环境描述以及对应资料的下载地址链接：
        开发环境：
            Base-Server: 172.16.10.114
            
        部署环境：
            服务都是基于SpringBoot技术构建的工程，可以通过JAR包直接发部。而且工程内已作好Dockerfile，
            可以通过Maven Docker Build生成Dokcer Image，然后利用Docker服务将其作为Docker容器进行发部运行。
        文档目录：
        
### (4)历史版本记录  
        暂无