package kr.co.fastcampus.eatgo.utila;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private  Key key;
    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());

    }

    public String createToken(Long userId, String name, Long restaurantId){
//        String secret = "12345678901234567890123456789012";
//        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name);

        if(restaurantId !=null){
            builder = builder.claim("restaurantId",restaurantId);
        }
//        String token = Jwts.builder()
//                .claim("userId", userId)
//                .claim("name", name)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    };

    public Claims getClaims(String token) {
       return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();


    }
}
