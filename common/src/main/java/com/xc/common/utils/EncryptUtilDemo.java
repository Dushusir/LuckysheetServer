package com.xc.common.utils;

import org.apache.tomcat.util.buf.HexUtils;
import java.util.Base64;

/**
 * @author Administrator
 */
public class EncryptUtilDemo {

    public static void main(String[] args) {
        System.out.println(EncryptUtil.md5Encrypt(123+""));
        System.out.println(EncryptUtil.md5Encrypt("123"));
        //md5简单加密
        System.out.println("md5简单加密==========================================");
        String text = "i am text";
        System.out.println(EncryptUtil.md5Encrypt(text));
        System.out.println(" ");
        System.out.println(" ");

        //base64进行加密解密,通常用作对二进制数据进行加密
        System.out.println("base64进行加密解密,通常用作对二进制数据进行加密===========");
        byte[] base64Encrypt = EncryptUtil.base64Encrypt("123456789");
        String toHexString = HexUtils.toHexString(base64Encrypt);
        System.out.println(toHexString);
        byte[] base64Decrypt = EncryptUtil.base64Decrypt(base64Encrypt);
        System.out.println(new String(base64Decrypt));
        System.out.println(" ");
        System.out.println(" ");

        //DES对称加密/解密
        System.out.println("DES对称加密/解密=====================================");
        //要求key至少长度为8个字符
        String key = "123456789";
        //加密
        System.out.println("加密");
        byte[] encode_bytes = EncryptUtil.DESEncrypt(key, "Hello, DES");
        System.out.println(Base64.getEncoder().encodeToString(encode_bytes));
        //解密
        System.out.println("解密");
        byte[] decode_bytes = EncryptUtil.DESDecrypt(key, encode_bytes);
        System.out.println(new String(decode_bytes));
        System.out.println(" ");
        System.out.println(" ");

        //RSA
        System.out.println("RSA================================================");
        //数据使用私钥加密
        System.out.println("数据使用私钥加密");
        byte[] en_byte = EncryptUtil.RSAEncrypt("Hi, RSA");
        System.out.println(Base64.getEncoder().encodeToString(en_byte));

        //用户使用公钥解密
        System.out.println("用户使用公钥解密");
        byte[] de_byte = EncryptUtil.RSADecrypt(en_byte);
        System.out.println(new String(de_byte));

        //服务器根据私钥和加密数据生成数字签名
        System.out.println("服务器根据私钥和加密数据生成数字签名");
        byte[] sign_byte = EncryptUtil.getSignature(en_byte);
        System.out.println(Base64.getEncoder().encodeToString(sign_byte));

        //用户根据公钥、加密数据验证数据是否被修改过
        System.out.println("用户根据公钥、加密数据验证数据是否被修改过");
        boolean verify_result = EncryptUtil.verifySignature(en_byte, sign_byte);
        System.out.println(verify_result);
        System.out.println(" ");
        System.out.println(" ");

        //SHA
        System.out.println("SHA====================================================");
        String sha = EncryptUtil.SHAEncrypt("Hi, RSA");
        System.out.println(sha);
        System.out.println(" ");
        System.out.println(" ");

        //HMAC
        System.out.println("HMAC===================================================");
        byte[] mac_bytes = EncryptUtil.HMACEncrypt(key, "Hi, HMAC");
        System.out.println(HexUtils.toHexString(mac_bytes));
    }
}
