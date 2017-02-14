package com.luosee.manager.modeling;

import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by server2 on 2017/1/5.
 */
public class CreateTxtAndWrite {

    final org.slf4j.Logger logger = LoggerFactory.getLogger(CreateTxtAndWrite.class);

    static boolean creatFile(String filename) throws IOException {
        boolean flag = false;
        File file = new File(filename);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
            System.out.println("文件夹已创建");
            flag = true;
        }
        return flag;
    }


    static boolean creatTxtFile(String filename) throws IOException {
        boolean flag = false;
        File file = new File(filename+".txt");
        if (!file.exists()) {
            file.createNewFile();
            flag = true;
        }
        return flag;
    }



    static void contentToTxt(String filePath, String content) {

        String str; //原有txt内容
        String s1 = "";//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));
            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

