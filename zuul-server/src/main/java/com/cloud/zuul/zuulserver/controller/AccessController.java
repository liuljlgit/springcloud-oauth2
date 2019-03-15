package com.cloud.zuul.zuulserver.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行登录和登出操作
 */
@RestController
public class AccessController {

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/login")
    public String createAuthenticationToken(@RequestBody JSONObject reqEntity, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @PostMapping(value = "/testRestTemplate")
    public String testRestTemplate(@RequestBody JSONObject reqEntity, HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://auth-server/sysRole/1000001?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiI2NDU2MDQ4OTkiLCJzY29wZSI6WyJhbGwiXSwiYWRkaXRpb25hbEluZm8iOiIxMjMiLCJleHAiOjE1NTI3MjUzNTgsImF1dGhvcml0aWVzIjpbIui2hee6p-euoeeQhuWRmCJdLCJqdGkiOiI1OTdlMGE2Ni0xMzRjLTRjNzYtYTIyYi01NDg3NTc1MGI2NTYiLCJjbGllbnRfaWQiOiJnYXRld2F5X2NsaWVudCJ9.XRVEm0zFXdZ6Di_Gkkt5H5NRCLT4DMeb5RsAaJPn4UpZCVlc72xDkkAs9B_UoDBZ9R_yakAk7cuq4ejUpq_B2DYq6C3dgiy3oheO9q2qn4pRgQz3ds8Z-er0KT2MqoSXfqZfz6Y5BQwac09tfLvsyX2ByVBjblWtpi13IvAe6bt1ObTzpwl45gk-QzbRpQCDrqaRJk7O0SGFlRbPbhk0iSEYcGloXVF3c89hnRzok0BAH0tlYUB9k2UEYEkZKaB4rqHAVOoCk-CDbG3uCSrusIYPfNokeYHEPGSVqR4XLbWnA1fWj0oo5W_rhH3Sp1Zs-7jH4HNV5mzC3feVNkshjg", String.class);
        return forEntity.getBody();
    }
}
