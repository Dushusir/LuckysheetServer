# Luckysheet Server

简体中文 | [English](./README.md)

## 介绍
💻[Luckysheet](https://github.com/mengshukeji/Luckysheet/) 官方Java版本后台。

## 演示
- [协同编辑Demo](http://luckysheet.lashuju.com/demo/)（注意：请大家别操作频繁，防止搞崩服务器）

## 环境

jdk >= 1.8

postgre >= 10 (支持jsonb的版本)

redis >= 3

nginx >= 1.12

maven >= 3.6 

Intelli JIDEA >= 12 (非必须)

## 数据库初始化

创建数据库
```
CREATE DATABASE luckysheetdb
```
创建序列
```
DROP SEQUENCE IF EXISTS "public"."luckysheet_id_seq";
CREATE SEQUENCE "public"."luckysheet_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9999999999999
START 1
CACHE 10;
```
创建表
```
DROP TABLE IF EXISTS "public"."luckysheet";
CREATE TABLE "luckysheet" (
  "id" int8 NOT NULL,
  "block_id" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "index" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "list_id" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "json_data" jsonb,
  "order" int2,
  "is_delete" int2
);
CREATE INDEX "block_id" ON "public"."luckysheet" USING btree (
  "block_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "list_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "index" ON "public"."luckysheet" USING btree (
  "index" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "list_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "is_delete" ON "public"."luckysheet" USING btree (
  "is_delete" "pg_catalog"."int2_ops" ASC NULLS LAST
);
CREATE INDEX "list_id" ON "public"."luckysheet" USING btree (
  "list_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "order" ON "public"."luckysheet" USING btree (
  "list_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "order" "pg_catalog"."int2_ops" ASC NULLS LAST
);
CREATE INDEX "status" ON "public"."luckysheet" USING btree (
  "list_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "status" "pg_catalog"."int2_ops" ASC NULLS LAST
);
ALTER TABLE "public"."luckysheet" ADD CONSTRAINT "luckysheet_pkey" PRIMARY KEY ("id");
```

插入初始化语句
```
INSERT INTO "public"."luckysheet" VALUES (nextval('luckysheet_id_seq'), 'fblock', '1', '1079500#-8803#7c45f52b7d01486d88bc53cb17dcd2c3', 1, '{"row":84,"name":"Sheet1","chart":[],"color":"","index":"1","order":0,"column":60,"config":{},"status":0,"celldata":[],"ch_width":4748,"rowsplit":[],"rh_height":1790,"scrollTop":0,"scrollLeft":0,"visibledatarow":[],"visibledatacolumn":[],"jfgird_select_save":[],"jfgrid_selection_range":{}}', 0, 0);
INSERT INTO "public"."luckysheet" VALUES (nextval('luckysheet_id_seq'), 'fblock', '2', '1079500#-8803#7c45f52b7d01486d88bc53cb17dcd2c3', 0, '{"row":84,"name":"Sheet2","chart":[],"color":"","index":"2","order":1,"column":60,"config":{},"status":0,"celldata":[],"ch_width":4748,"rowsplit":[],"rh_height":1790,"scrollTop":0,"scrollLeft":0,"visibledatarow":[],"visibledatacolumn":[],"jfgird_select_save":[],"jfgrid_selection_range":{}}', 1, 0);
INSERT INTO "public"."luckysheet" VALUES (nextval('luckysheet_id_seq'), 'fblock', '3', '1079500#-8803#7c45f52b7d01486d88bc53cb17dcd2c3', 0, '{"row":84,"name":"Sheet3","chart":[],"color":"","index":"3","order":2,"column":60,"config":{},"status":0,"celldata":[],"ch_width":4748,"rowsplit":[],"rh_height":1790,"scrollTop":0,"scrollLeft":0,"visibledatarow":[],"visibledatacolumn":[],"jfgird_select_save":[],"jfgrid_selection_range":{}}', 2, 0);
```

## nginx配置 
http块配置
```
#websocket配置
map $http_upgrade $connection_upgrade {
    default upgrade;
    ''      close;
}

upstream ws_dataqk {
      server 项目的ip:端口;
}    
```
server块配置
```
#websocket配置
location /luckysheet/websocket/luckysheet {
    proxy_pass http://ws_dataqk/luckysheet/websocket/luckysheet;

    proxy_set_header Host $host;
    proxy_set_header X-real-ip $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

    #websocket
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
}       

#动态资源配置
location /luckysheet/ {
    proxy_pass http://ws_dataqk;
}

#静态资源配置
location /luckysheet/demo/ {
    root /资源路径/;
    index  index.html index.htm;
}
```

## 项目用法 
application.yml 项目配置
```
server:
  port: 项目端口
  servlet:
    context-path: /项目路径
redis.channel: redis通道名称
row_size: 表格中行数 默认500
col_size: 表格中列数 默认500
pgSetUp: 是否启用pgsql作为存储数据（0为是，1为否）目前只能设置为0
```
application-dev.yml 数据库配置
```
spring:
  redis:
    host: ip地址
    port: 端口
    password: 密码
    
db:
  postgre:
    druid:
      url: jdbc:postgresql://ip地址:端口/luckysheetdb?useSSL=false
      driverClassName: org.postgresql.Driver
      username: 用户名
      password: 密码    
```
logback-spring.xml 日志配置
```
 <property name="log.path" value="日志输出目录"/>
```
## 项目主要类
WebApplication 项目启动类

JfGridFileController 表格数据加载类
```
/load       加载表格结构
/loadsheet  加载指定表格 
```
WebSocketConfig websocket地址配置类
```
//1.注册WebSocket 设置websocket的地址
String websocket_url = "/websocket/luckysheet";
//2.注册SockJS，提供SockJS支持(主要是兼容ie8) 设置sockjs的地址
String sockjs_url = "/sockjs/luckysheet";        
        
```


## 相关链接
- [Luckysheet官方文档](https://mengshukeji.github.io/LuckysheetDocs/)
- [Luckysheet如何把表格里的数据保存到数据库](https://www.cnblogs.com/DuShuSir/p/13857874.html)

## 贡献者和感谢

### 团队
- [@iamxuchen800117](https://github.com/iamxuchen800117)
- [@wpxp123456](https://github.com/wpxp123456)

## 版权信息
有关详细信息，请查阅附件的[LICENSE](./LICENSE)文件。原始作者保留Apache 2.0许可未明确授予的所有权利。
