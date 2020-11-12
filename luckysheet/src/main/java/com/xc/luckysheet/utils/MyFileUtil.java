package com.xc.luckysheet.utils;

import com.mongodb.DBObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class MyFileUtil {
    public static String getExtensionName(MultipartFile file){
        if(file!=null){
            return getExtensionName(file.getOriginalFilename().toLowerCase());
        }
        return null;
    }

    public static void delFile(String filePath){
        if(filePath!=null){
            try {
                //保存文件
                File _f=new File(filePath);
                if(_f.exists()){
                    _f.delete();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Java文件操作 获取文件扩展名
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 传入文件名以及字符串, 将字符串信息保存到文件中
     *
     * @param strFilename
     * @param strBuffer
     */
    public static void TextToFile(final String strFilename, final String strBuffer){
        try {
            // 创建文件对象
            File fileText = new File(strFilename);
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(fileText);

            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String readTxtFile(MultipartFile fileUpload, String encoding) {
        InputStream input = null;
        InputStreamReader reader = null;
        BufferedReader bReader = null;
        try {
            input = fileUpload.getInputStream();
            if (encoding == null) {
                reader = new InputStreamReader(input);
            } else {
                reader = new InputStreamReader(input, encoding);
            }
            bReader = new BufferedReader(reader);
            //String str=bReader.readLine();
            String str = "";
            StringBuilder _sb=new StringBuilder();
            while ((str = bReader.readLine()) != null) {
                _sb.append(str);
            }
            return _sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bReader) {
                try {
                    bReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    /**
     * 计算文件大小
     *
     * @param fileLength 文件length
     * @return 文件大小
     */
    public static String FormetFileSize(Long fileLength) {
        String fileSizeString = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileLength != null) {
            if (fileLength < 1024) {
                fileSizeString = df.format((double) fileLength) + "B";
            }
            else if (fileLength < 1048576) {
                fileSizeString = df.format((double) fileLength / 1024) + "K";
            }
            else if (fileLength < 1073741824) {
                fileSizeString = df.format((double) fileLength / 1048576) + "M";
            }
            else {
                fileSizeString = df.format((double) fileLength / 1073741824) + "G";
            }
        }
        return fileSizeString;
    }

    /**
     * M换成b
     * @param l
     * @return
     */
    public static Long changeBByM(Long l){
        return l*1024*1024;
    }

    /**
     * 计算字符串字节长度
     * @param object
     * @return
     */
    public static Long getSizeByStr(DBObject object){
       if(object!=null && object.containsField("jfgridfile")){
           Object _b=object.get("jfgridfile");
           if(_b instanceof List){
              return getSizeByStr((List)_b);
           }
       }
        return getSizeByStr(object.toString());
    }
    public static Long getSizeByStr(List<DBObject> lists){
        Long l=0l;
        if(lists!=null && lists.size()>0){
            for(DBObject b:lists){
                l=l+getSizeByStr(b.toString());
            }
        }
        return l;
    }
    public static Long getSizeByStr(String str){
       try{
           return Long.valueOf(str.getBytes("GBK").length);
       }catch (Exception ex){
           System.out.println(ex.toString());
           return Long.valueOf(str.length());
       }
    }
    //转换为k
    public static double getSizeK(Long length){
        if(length!=null){
            return (double) length / 1024;
        }
        return 0;
    }

    /**
     * 字符串字节长度转换k
     * @param str
     * @return
     */
    public static double getSizeKByStr(String str){
        if(str!=null){
           try{
               //UTF-8 汉字算3个 GBK 汉字算2字节
               return getSizeK(Long.valueOf(str.getBytes("GBK").length));
           }catch (Exception ex){
               return getSizeK(Long.valueOf(str.length()));
           }
        }
        return 0;
    }
}
