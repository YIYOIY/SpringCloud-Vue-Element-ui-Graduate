package com.yoi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author 游弋
 * @create 2023-07-28 1:10
 */
public class JwtUtils {
//     token到期时间30分钟(根据需求改)
//    private static final long EXPIRE_TIME= 30*60*1000;
//    过期时间
//    Date invalidateDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//    builder.withExpiresAt(invalidateDate);
//    System.out.println("\n过期时间：" + invalidateDate);
    //密钥 (随机生成,可以从网上找到随机密钥生成器)
    private static final String SIGNATURE="MD9youyiqunxun+chunhelixhzneMG^EG79RV+T?J87AI4NWQVT^&";

    /**
     * 生成token
     * @param map //传入payload
     * @return 返回token
     *  它的含义就像是编程语言的保留字一样，属于JWT标准里面规定的一些claim。JWT标准里面定义好的claim有：
     *               iss(Issuer)：代表这个JWT的签发主体；
     *               sub(Subject)：代表这个JWT的主体，即它的所有人；
     *               aud(Audience)：代表这个JWT的接收对象；
     *               exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
     *               nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
     *               iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
     *               jti(JWT ID)：是JWT的唯一标识。
     * //        JWT的唯一标识,避免重放攻击
     * //        String jwi = UUID.randomUUID().toString();
     * //        builder.withJWTId(jwi);

     *jwt的签名算法有三种：
     * HMAC【哈希消息验证码(对称)】：HS256/HS384/HS512
     * RSASSA【RSA签名算法(非对称)】（RS256/RS384/RS512）
     * ECDSA【椭圆曲线数据签名算法(非对称)】（ES256/ES384/ES512）
     * 声明类型，这里是jwt
     * 声明加密的算法 通常直接使用 HMAC SHA256
     * JWT里验证和签名使用的算法，可选择下面的。
     * //        header,不指名就是默认的 HMAC SHA256加密
     * //        HashMap<String,Object> headerMap = new HashMap<>();
     * //        headerMap.put("alg", "HS256");
     * //        headerMap.put("typ", "JWT");
     * //        builder.withHeader(headerMap);

     * jwt的第三部分是一个签证信息，这个签证信息算法如下：
     * base64UrlEncode(header) + "." + base64UrlEncode(payload)+your-256-bit-secret
     * 这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。

     * //有10天有效期
     * Calendar nowTime = Calendar.getInstance();
     * nowTime.add(Calendar.DATE, 10);
     * Date expiresDate = nowTime.getTime();
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
//        私有声明存放数据  payload，如果有私有声明，一定要先设置这个自己创建的私有的声明，
//        这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
          map.forEach(builder::withClaim);//这是lambda的优化，相当于下方
//        map.forEach((k,v)->{
//            builder.withClaim(k,v);
//        });

//        发行人
        builder.withIssuer("yoi");

//        签发时间
        Date signDate = new Date(System.currentTimeMillis());
        builder.withIssuedAt(signDate);
        System.out.println("\n签发时间：" + signDate);

//        过期时间

        Calendar nowTime = Calendar.getInstance();
        nowTime.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        nowTime.add(Calendar.SECOND, 90);
        Date expiresDate = nowTime.getTime();
        builder.withExpiresAt(expiresDate);
        System.out.println("\n过期时间：" + expiresDate);


        return builder.sign(Algorithm.HMAC256(SIGNATURE));
    }

    /**
     * 验证token
     * @param token 请求传递的token
     */
    public static void verify(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
            System.out.println("认证通过：");
            System.out.println("id: " + verify.getClaim("id").asString());
            System.out.println("name: " + verify.getClaim("name").asString());
            System.out.println("过期时间：" + verify.getExpiresAt());
    }

    /**
     * 获取token中payload
     * @param token 前端传递的token
     * @return 返回jwt验证对象
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }


}
