# ----------------- LittleBoy ------------------
这是一个基于springboot和netty的一些demo集

环境: IDEA14及以上 , JDK1.8(>=1.6就行), maven, redis, mysql5.1以上(windows下直接wamp一套集成)
插件：lombok(IDEA需要安装的插件，作用是自动生成gettter和setter)

使用spirngbooot好处: 零配置就可以启动一个web项目，整合了Spring+SpringMVC+JPA+Tomcat, 可以直接打包成jar运行(而不是war)
文件夹主要目录结构如下：
LittleBoy
    -logs   -- 日志  
    -src
        -main
            -java   -- 代码目录
                -web    --springboot环境下的demo  
                    -config     --所有的springboot配置bean(可以取代传统的.xml配置bean)
                    -controller --控制器类
                    -entity     --实体
                    -mapper     --mybatis目录
                    -redis      --redis封装
                    -repository --spring-data-jpa相关
                    -service    --逻辑层
                    WebApplication.java  --springboot启动主函数 
                -websocket   基于netty和websocket，没有springboot环境
            -resources  --  资源目录(包括配置文件)
                -environment    --分dev,product,test环境，最终打包只有一个环境,详细见pom.xml注释
                -mapper     --mybatis的配置文件
                -protoc     -- protobuff相关协议
                -static     --前端静态资源
                -templates  --view层
        -test  --   所有的单元测试案例，里面有注释      
    -target maven命令生成目录
    pom.xml   --maven依赖包的配置


maven打包与多环境：见pom.xml文件里面的注释    
springboot：com.thanple.little.boy.web目录下，配置文件在里面config目录下，看注释即可。
mysql:先建立一个数据库db_minigame，修改environment/*/application.properties配置，其他表都可以自动生成。
mybatis: 集成了使用注解和.xml配置文件的方式
spring-data-jpa: 和mybatis竞争的一种ORM，也集成了注解和自定义实现类的方式
redis: web目录下和websocket目录下都有，分别集成了在有spring环境下和没有spring环境下的jedis访问层(数据对象序列化方式为Jaskson) 
        另外还集成了redis的发布-订阅, redis锁。
websocket:  集成了基于protobuff和基于http压缩传输两种方式  
ptotobuff: 生成代码工具需要到https://github.com/google/protobuf/releases下载3.5.1版本     
    