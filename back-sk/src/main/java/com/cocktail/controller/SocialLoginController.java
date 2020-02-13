package com.cocktail.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cocktail.model.BasicResponse;
import com.cocktail.service.KakaoAPI;
import com.cocktail.service.NaverAPI;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@Controller
public class SocialLoginController {
    @Autowired
    NaverAPI naver;
    @Autowired
    KakaoAPI kakao;

    @GetMapping("/user/naverLogin")
    @ApiOperation(value = "네이버 로그인")
    public String naverLogin(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state)
            throws Exception {
        String clientId = "39EaMVDOSBDD93mc7mjA";// 애플리케이션 클라이언트 아이디값";
        String naverClientSecret = "R1KJH90E93";
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + clientId;
        apiURL += "&client_secret=" + naverClientSecret;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        String access_token = "";
        String refresh_token = "";
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if (responseCode == 200) { // 성공적으로 토큰을 가져온다면
                String id, nickName, email, tmp;
                JsonParser parser = new JsonParser();
                JsonElement accessElement = parser.parse(res.toString());
                access_token = accessElement.getAsJsonObject().get("access_token").getAsString();

                tmp = naver.getUserInfo(access_token);
                System.out.println(tmp);
                JsonElement userInfoElement = parser.parse(tmp);
                id = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
                nickName = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("nickname")
                        .getAsString();
                email = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
                access_token = "id=" + id + "&email=" + email + "&nickName=" + nickName;
                System.out.println(id + " " + email + " " + nickName);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:http://localhost:3000/auth" + access_token;
    }

    @GetMapping("/user/kakaoLogin")
    @ApiOperation(value = "카카오 로그인")
    public String kakaoLogin(@RequestParam("code") String code) {
        String access_token = kakao.getAccessToken(code);
        System.out.println(access_token);
        System.out.println("controller access_token : " + access_token);

        

        return "redirect:http://localhost:3000/auth" + access_token;
    }
    
    
}
