package com.thanple.little.boy.websocket;

import com.thanple.little.boy.websocket.util.Constant;
import com.thanple.little.boy.websocket.util.CreateProtocolUtil;
import org.junit.Test;

/**
 * Create by Thanple at 2018/5/26 下午2:43
 * protobuff工具下载: https://github.com/google/protobuf/releases
 * 关于protobuff的一些测试
 */
public class ProtoBuffTest {

    /**
     * 测试生成文件
     *
     * GameServerCMsg和GameServerSMsg为通用协议，里面的ByteString为具体的协议
     */
    @Test
    public void testGenerateProtocol(){

        CreateProtocolUtil.generate(Constant.Path.ROOT_PATH+"/src/main/resources/protoc","GameServerCMsg.proto" ,
                Constant.Path.ROOT_PATH+"/src/main/java");
        CreateProtocolUtil.generate(Constant.Path.ROOT_PATH+"/src/main/resources/protoc","GameServerSMsg.proto" ,
                Constant.Path.ROOT_PATH+"/src/main/java");

        CreateProtocolUtil.generate(Constant.Path.ROOT_PATH+"/src/main/resources/protoc","CPerson1.proto" ,
                Constant.Path.ROOT_PATH+"/src/main/java");
        CreateProtocolUtil.generate(Constant.Path.ROOT_PATH+"/src/main/resources/protoc","SPerson1.proto" ,
                Constant.Path.ROOT_PATH+"/src/main/java");
    }

}
