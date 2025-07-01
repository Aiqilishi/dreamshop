package cn.mmko.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class JwtUtils {
    private static final String signatureKey="mmko";
    private static final long expireTime=1000*60*60*24;
    public static String generateJwt(String userName) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header部分
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload部分
                .claim("username", userName)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .setId(UUID.randomUUID().toString())
                //signature部分
                .signWith(SignatureAlgorithm.HS256, signatureKey)
                .compact();
        return jwtToken;
    }

    public static boolean validateToken(String token) {
        return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().getExpiration().after(new Date());
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().get("username").toString();
    }
}
