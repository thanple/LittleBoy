package com.thanple.little.boy.websocket.util;

import java.io.*;

/**
 * Created by Thanple on 2017/1/22.
 */
public class CreateProtocolUtil {

    /**
     * 执行protoc命令
     * protoc -I=proto的输入目录 --java_out=java类输出目录 proto的输入目录包括包括proto文件
     * @param protoDir proto源文件路径
     * @param protoFile proto源文件名
     * */
    public static void generate(String protoDir , String protoFile , String javaOutputDir){
        String strCmd = String.format("protoc -I=%s --java_out=%s %s",
                protoDir,
                javaOutputDir,
                protoDir+"/"+protoFile);
        //"d:/dev/protobuf-master/src/protoc.exe -I=./proto --java_out=./src/main/java ./proto/"+ protoFile;

        try {
            Process process = Runtime.getRuntime().exec(strCmd);

            //打印结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str;
            while ((str = reader.readLine())!=null){
                System.out.println(str);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
