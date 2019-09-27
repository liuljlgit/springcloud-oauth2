package com.cloud.zuul.zuulserver.utils;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

public class JwtUtil {

    public static Map getJwtClaimMap(String accessToken){
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore.jks"), "123456".toCharArray())
                .getKeyPair("o2jks");
        Jwt jwt1 = JwtHelper.decodeAndVerify(accessToken, new RsaVerifier((RSAPublicKey) keyPair.getPublic(), "SHA256withRSA"));
        Jwt jwt = JwtHelper.decode(accessToken);
        String claims = jwt.getClaims();
        //String user_name = String.valueOf(claimsMap.get("user_name"));    //这样获取claim中的内容
        return jsonParser.parseMap(claims);
    }
}
