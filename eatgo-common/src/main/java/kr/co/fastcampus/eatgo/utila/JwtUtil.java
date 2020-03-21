package kr.co.fastcampus.eatgo.utila;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private  Key key;
    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());

    }

    public String createToken(Long userId, String name){
        String secret = "12345678901234567890123456789012";
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    };

    public Claims getClaims(String token) {
       return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();


    }
}
