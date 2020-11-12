package com.xc.common.utils;


import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 16-1-22
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public class SendEmail {
    public static void main(String[] args){
        try{
            String filePath="c:\\4-6月考勤(1).xls";
            String fileName="中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文中文.xls";
            System.out.println(MimeUtility.encodeText(fileName));

            fileName=MimeUtility.encodeText(fileName);
            fileName=fileName.replace("\\r","").replace("\\n","");
            System.out.println(fileName);

            //SendEmailByEmail(filePath,fileName);
            //SendEmailForEnclosure();
        }catch (Exception ex){
            System.out.println(ex.toString());
        }

    }
    public static void SendEmailByEmail(String filePath,String fileName) throws Exception{

        System.setProperty("mail.mime.splitlongparameters","true");
        //System.setProperty("mail.mime.charset","UTF-8");

        String host="smtp.qq.com";//Config.getProperties().getProperty("smtp");
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        //String mailNeedAuth = "0";
        String mailNeedAuth = "1";
        String sender="1321348848@qq.com";//Config.getProperties().getProperty("sender");
        String mailUser ="1321348848@qq.com";//Config.getProperties().getProperty("mailUser");
        String mailPassword ="ajxibgyucsfgjaaa";// Config.getProperties().getProperty("mailPassword");
        String recipients1="chen.xu1@newtouch.com;xuct@ft.cntaiping.com;juliezcy1@sina.com;xzrj.xuchen@waibao.cntaiping.com;758446830@qq.com;xzrj.zhangcy@newtouch.com;xzrj.zhangcy1@waibao.cntaiping.com";//"631789194@qq.com;758446830@qq.com";//user.getMailAddress();
        //String recipients2="758446830@qq.com";
        //String recipients1="2639797432@qq.com";//user.getMailAddress();
        String recipients2="";

        if (mailNeedAuth.equals("1")) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }

        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        String subject="邮箱测试";
        String body="Dear "+"nane"+",<br><br>";
        body+="Your Code is : "+"123"+",<br>";

        //Session session = Session.getDefaultInstance(props);
        Session session = Session.getInstance(props);
        session.setDebug(true);

        javax.mail.Message msg = new MimeMessage(session);
        String[] str=recipients1.split(";");
        InternetAddress[] toAddrs = new InternetAddress[str.length];

        if (recipients1 != null && !recipients1.equals("")) {
            //toAddrs = InternetAddress.parse(recipients1, false);
            for(int x=0;x<str.length;x++){
                toAddrs[x]=new InternetAddress(str[x]);
            }
            msg.setRecipients(javax.mail.Message.RecipientType.TO, toAddrs);
        } else {
            //throw new Exception("No recipient address specified");
        }

        msg.addRecipient(javax.mail.Message.RecipientType.CC, new InternetAddress("631789194@qq.com", "", "UTF-8"));

        if (sender != null) {
            msg.setFrom(new InternetAddress(sender));
        } else {
            throw new Exception("No sender address specified");
        }

        if (subject != null) {
            msg.setSubject(subject);
        }


        DataHandler dh =new DataHandler(body,"text/html;charset=gb2312");
        msg.setDataHandler(dh);



        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        // 如果有多个附件，可以创建多个多次添加
        //mm.addBodyPart(setMimeBodyPart("c:\\架构图.png","架构.png图"));
        mm.addBodyPart(setMimeBodyPart(filePath,fileName));
        mm.setSubType("mixed");         // 混合关系

        msg.setContent(mm);

        Transport trans = session.getTransport("smtp");
        trans.connect(host, mailUser, mailPassword);
        trans.sendMessage(msg, msg.getRecipients(javax.mail.Message.RecipientType.TO));
        trans.sendMessage(msg, msg.getRecipients(Message.RecipientType.CC));
    }

    private static MimeBodyPart setMimeBodyPart(String filepath,String filename) throws Exception{
        // 9. 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh2 = new DataHandler(new FileDataSource(filepath));
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        //attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
        //attachment.setFileName(MimeUtility.encodeText(filename,"utf-8",null));
        filename=MimeUtility.encodeText(filename);
        filename=filename.replace("\\r","").replace("\\n","");
        attachment.setFileName(filename);
        return attachment;
    }

    public static void SendEmailForEnclosure() throws Exception{
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "10.4.1.16");
        //String mailNeedAuth = "0";
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", 25);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        String body="";
        Session session = Session.getInstance(props, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("da@fsc.cntaiping.com","TaiP@da_2006");
            }
        });
        session.setDebug(true);

        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("da@fsc.cntaiping.com"));
        //设置收件人(群发)
        String toMail="xzrj.xuchen@waibao.cntaiping.com;758446830@qq.com;xzrj.zhangcy@newtouch.com;xzrj.zhangcy1@waibao.cntaiping.com";
        String []emailStr=toMail.split(";");//对输入的多个邮件进行逗号分割
        InternetAddress[] toAddress = new InternetAddress[emailStr.length];
        for (int i=0; i<emailStr.length; i++){
            toAddress[i] = new InternetAddress(emailStr[i]);
        }
        message.setRecipients(Message.RecipientType.TO, toAddress);
        //
        String csEmail=null;
        if(StringUtils.isNotBlank(csEmail)){
            String [] csEmailStr=csEmail.split(";");
            // 抄送
            if (csEmailStr != null&&csEmailStr.length>0) {
                InternetAddress[] iaToListcs = new InternetAddress[csEmailStr.length];
                for (int i=0; i<csEmailStr.length; i++){
                    iaToListcs[i] = new InternetAddress(csEmailStr[i]);
                }
                message.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
            }
        }



        //设置主题
        message.setSubject("邮件测试");
		/*DataHandler dh =new DataHandler(body,"text/html;charset=UTF-8");
		message.setDataHandler(dh);*/
        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        // 如果有单个个附件，也可以创建多个多次添加
        //mm.addBodyPart(setMimeBodyPart(filePath,subject+".xls"));
        mm.addBodyPart(setMimeBodyPart("c:\\4-6月考勤(1).xls","4-6月考勤(1).xls"));
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setText("此邮件由系统自动发送,无需回复,如有问题请联系以下邮箱:test");// 设置邮件的文本内容
        mm.addBodyPart(contentPart);
        // 混合关系
        mm.setSubType("mixed");
        message.setContent(mm);
        Transport trans = session.getTransport("smtp");
        trans.connect( "10.4.1.16", "da@fsc.cntaiping.com","TaiP@da_2006");
        trans.sendMessage(message, message.getRecipients(javax.mail.Message.RecipientType.TO));
    }

}
