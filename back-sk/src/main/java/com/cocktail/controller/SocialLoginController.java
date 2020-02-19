package com.cocktail.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cocktail.config.JwtTokenProvider;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.SingleResponse;
import com.cocktail.model.user.User;
import com.cocktail.service.KakaoAPI;
import com.cocktail.service.NaverAPI;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/backend")
public class SocialLoginController {
    @Autowired
    NaverAPI naver;
    @Autowired
    KakaoAPI kakao;
    @Autowired
    UserDao userDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    
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

        return "redirect:http://localhost:3000/" + access_token;
    }

    @GetMapping("/user/kakaoLogin")
    @ApiOperation(value = "카카오 로그인")
    public ModelAndView kakaoLogin(@RequestParam("code") String code) {
        String access_token = kakao.getAccessToken(code);
        String userInfo = kakao.getUserInfo(access_token);
        ModelAndView mav = new ModelAndView();
        String[] arr = userInfo.split("&");
        System.out.println(Arrays.toString(arr));
		String nickname = arr[0].split("=")[1];
		String email = null;
		String[] tmp = arr[1].split("=");
		if(tmp.length > 1)
			email = tmp[1];
		else {
			User find = userDao.findByNickname(nickname);
			if(find != null)
				email = find.getEmail(); 
		}
//		mav.setViewName("redirect:http://localhost:3000/auth"); // local
		mav.setViewName("redirect:https://i02a309.p.ssafy.io");  // aws
		if(email == null) {
			mav.addObject("nickname", nickname);
			mav.addObject("msg", "email이 존재하지 않아 회원가입 페이지로 이동합니다.");
			return mav;
		}else if(userDao.findByNickname(nickname) == null) {
			userDao.save(User.builder().email(email).password(passwordEncoder.encode("kakao4312!@#$"))
					.nickname(nickname).roles(Collections.singletonList("ROLE_USER")).build());
		}
		mav.addObject("token", access_token);
        return mav;
    }
    
    @ResponseBody
    @PostMapping("/user/auth")
    @ApiOperation(value = "토큰 인증")
    public Object auth(@RequestParam(required = true) String token, HttpServletResponse response) {
		String userInfo = kakao.getUserInfo(token);
		String[] arr = userInfo.split("&");
		String nickname = arr[0].split("=")[1];
		String email = null;
		String[] tmp = arr[1].split("=");
		if(tmp.length > 1)
			email = tmp[1];
		else {
			User find = userDao.findByNickname(nickname);
			if(find != null)
				email = find.getEmail(); 
		}
		String password = "kakao4312!@#$";
		User user = userDao.getUserByEmail(email).orElseThrow(CocktailException::new);
		if (!passwordEncoder.matches(password, user.getPassword()))
			throw new CocktailException();
		String access_token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		// 헤더에 token을 저장하기 위한 설정
		response.addHeader("Access-Control-Allow-Headers", "Authorization");
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Authorization", access_token);
		final SingleResponse<User> result = new SingleResponse<>();
		result.setStatus(true);
		result.setObject(user);
		result.setData("succecss");
		System.out.println(result);
		return result;
    }
}
