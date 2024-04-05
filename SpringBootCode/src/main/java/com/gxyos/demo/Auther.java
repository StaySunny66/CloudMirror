package com.gxyos.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gxyos.demo.entity.UserInf;

import java.util.Date;

public class Auther {
    private static String KEY = "gxyososdhhfkfkkf";

    public static String makeToken(UserInf user){
        String token = JWT.create().withExpiresAt(new Date(System.currentTimeMillis()+3600))
                .withAudience(user.getUserid())
                .sign(Algorithm.HMAC256(KEY));

        return token;

    }


}
