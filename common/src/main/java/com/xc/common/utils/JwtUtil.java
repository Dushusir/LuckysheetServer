package com.xc.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT
 */
@Slf4j
public class JwtUtil {

    private static final String privateKey="privateKey";
    private static final String ISSUER ="www.test.com";


    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private static SecretKey generalKey(){
        byte[] encodedKey = Base64.decodeBase64(privateKey);
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 生成token
     * @param subject （主体信息）
     * @param claims 自定义身份信息
     * @return
     */
    public static String generateToken(String subject,  Map<String,Object> claims) {
        return generateToken(subject,60*5,claims);
    }
    /**
     * 生成token
     * @param subject （主体信息）
     * @param expirationSeconds 过期时间（秒）
     * @param claims 自定义身份信息
     * @return
     */
    public static String generateToken(String subject, int expirationSeconds, Map<String,Object> claims) {
        if(claims==null){
           claims=new HashMap<>();
        }
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();

        //签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //过期时间
        long exp = nowMillis + expirationSeconds*1000;



        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                //签发时间
                .setIssuedAt(now)
                .setExpiration(new Date(exp))
                //签发者
                .setIssuer(ISSUER)
//                .signWith(SignatureAlgorithm.HS512, salt) // 不使用公钥私钥
//                .signWith(SignatureAlgorithm.HS256, privateKey)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    /**
     * 获取jwt中的subject
     * @param token
     * @return
     * @throws Exception
     */
    public static String getSubjectByToken(String token)throws Exception{
        return getTokenBody(token).getSubject();
    }
    /**
     * 是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token)throws Exception{
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 获取token自定义属性
     * @param token
     * @return
     */
    public static Claims getClaims(String token)throws Exception{
        Claims claims = null;
        try {
            claims = getTokenBody(token);
            log.info("签发时间：{},过期时间：{}",DateUtil.parseTime(claims.getIssuedAt()),DateUtil.parseTime(claims.getExpiration()));
        }catch (Exception e) {
        }

        return claims;
    }
    private static Claims getTokenBody(String token)throws Exception{
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    public static void main(String [] args) throws Exception {
        Map<String,Object> claims=new HashMap<>();
        claims.put("age",11);
        claims.put("name","test");

        String token=generateToken("subject",1000*10,claims);
        System.out.println(token);

        token="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3d3cudGVzdC5jb20iLCJzdWIiOiJhZG1pbiIsImxvZ2luVGltZSI6IjIwMjAtMDgtMDEgMDE6MDY6MjEiLCJleHAiOjE1OTYyMTUxODEsImlhdCI6MTU5NjIxNTE4MX0.-QQXsq1wstj_xYClmFoTNkWG5KROriuqcBHNFNapJok";
        Map<String,Object> map=getClaims(token);
        System.out.println(isExpiration(token));
        System.out.println(JsonUtil.toJson(map));

        System.out.println("====================");
        Thread.sleep(1000*3);
        map=getClaims(token);
        System.out.println(isExpiration(token));
        System.out.println(JsonUtil.toJson(map));
    }

}
