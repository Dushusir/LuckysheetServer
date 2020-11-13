package com.xc.common.constant;


/**
 * @author Administrator
 */
public interface SysConstant {

    interface MSG{
        /**
         * 操作成功
         */
        String SUCCESS = "操作成功";

        /**
         * 系统错误，请联系管理员
         */
        String ERROR = "系统错误，请联系管理员";
    }

    /**
     * xml声明
     */
    interface XML_HEAD {
        String UTF8 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

        String GBK = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

        String GB18030 = "<?xml version=\"1.0\" encoding=\"GB18030\"?>";
    }

    /**
     * 编码类型
     */
    interface ENCODING {
        String UTF8 = "UTF-8";

        String GBK = "GBK";
    }

    /**
     * 符号
     */
    interface MARK{

        String EQUALS = "=";

        String AND = "&";

        String NEWLINE = "\n";
    }

    /**
     * 状态
     */
    public interface STATUS {
        /**
         * 有效/成功
         */
        String Valid = "1";

        /**
         * 失效/失败
         */
        String Invalid = "0";

        /**
         * 用户过期
         */
        String Over = "99";

        /**
         * 未查询出数据
         */
        String Empty = "98";

    }

    /**
     * 系统级自身消息代码
     *
     * @author guanhongwei
     */
    public interface SYS_CODE {
        /**
         * 系统消息代码统一前缀
         */
        String SYS = "SYS";

        /**
         * token 错误
         */
        String TokenError = SYS + "00000";

        /**
         * 成功
         */
        String STATUS_SUCCESS = SYS + "1";

        /**
         * 失败
         */
        String STATUS_ERROR = SYS + "0";

        /**
         * 需要验证码
         */
        String STATUS_NEEDCODE = SYS + "98";

        /**
         * 用户过期
         */
        String STATUS_OVER = SYS + "99";



    }

    interface Editor {
        /**
         * 原子保存队列名字
         */
        String editorQueue = "editor_queue";
    }

    interface Redis {
        /**
         * 缓存时间
         */
        Integer sessionTime=30;
        /**
         * 保存token
         */
        String TOKEN="session:token:";
        /**
         * 保存用户信息
         */
        String userinfo="session:userinfo:";

        /**
         * 用户登录错误次数
         */
        String loginErrorCount="login:error";
        /**
         * 用户登录验证码
         */
        String loginCode="login:code";
    }

    interface Login{
        /**
         * 请求头
         */
        String token="x-auth-token";
        /**
         * 登陆错误次数
         */
        int loginErrorCount=3;
        /**
         * 5分钟
         */
        int loginErrorTime=5;
    }

}
