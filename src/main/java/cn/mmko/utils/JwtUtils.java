package cn.mmko.utils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Slf4j
public class JwtUtils {
    private static final String signatureKey="mmko";
    private static final long expireTime=1000*60*60*24;
    public static String generateJwt(String userName, List<String> role, List<String> permission, Long userId, Long customerId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header部分
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload部分
                .claim("username", userName)
                .claim("role", role)
                .claim("permission", permission)
                .claim("userId", userId)
                .claim("customerId", customerId)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .setId(UUID.randomUUID().toString())
                //signature部分
                .signWith(SignatureAlgorithm.HS256, signatureKey)
                .compact();
        return jwtToken;
    }
    public static String generateSellerJwt(String userName, List<String> role, List<String> permission, Long userId, Long sellerId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header部分
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload部分
                .claim("username", userName)
                .claim("role", role)
                .claim("permission", permission)
                .claim("userId", userId)
                .claim("sellerId", sellerId)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .setId(UUID.randomUUID().toString())
                //signature部分
                .signWith(SignatureAlgorithm.HS256, signatureKey)
                .compact();
        return jwtToken;
    }
    //TODO: 后期添加管理员token
    public static String generateAdminJwt(String userName, List<String> role, List<String> permission, Long userId, Long adminId) {
        return null;
    }

    public static boolean validateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().get("username").toString();
    }
    public static List<String> getRoleFromToken(String token) {
        return (List<String>) Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().get("role");
    }
    public static List<String> getPermissionFromToken(String token) {
        return (List<String>) Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().get("permission");
    }

    public static Long getUserIdFromToken(String token) {
        Object userId = Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().get("userId");
        log.info("userId:{}",userId);
        return Long.parseLong(userId.toString());
    }
    public static Long getCustomerIdFromToken(String token) {
        Object customerId = Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().get("customerId");
        log.info("customerId:{}",customerId);
        if (customerId==null) return null;
        return Long.parseLong(customerId.toString());
    }
    public static Long getSellerIdFromToken(String token) {
        Object sellerId = Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().get("sellerId");
        log.info("sellerId:{}",sellerId);
        if (sellerId==null) return null;
        return Long.parseLong(sellerId.toString());
    }

}
