package com.cocktail.controller;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.config.JwtTokenProvider;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
//import com.cocktail.exception.CEmailSigninFailedException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.SingleResponse;
import com.cocktail.model.user.User;
import com.cocktail.service.EmailService;
import com.cocktail.service.KakaoAPI;
import com.cocktail.service.ResponseService;

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
@RestController
@RequestMapping(value = "/backend")
public class AccountController {
	// 디비 셋팅 후 주석을 푸세요.
	@Autowired
	UserDao userDao;
	@Autowired
	EmailService emailService;
	@Autowired
	private final ResponseService responseService;
	@Autowired
    KakaoAPI kakao;
	
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/user/login")
	@ApiOperation(value = "로그인")

	public Object login(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password, HttpServletResponse response) {
		User user = userDao.getUserByEmail(email).orElseThrow(CocktailException::new);

		if (!passwordEncoder.matches(password, user.getPassword()))
			throw new CocktailException();
		String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		// 헤더에 token을 저장하기 위한 설정
		response.addHeader("Access-Control-Allow-Headers", "Authorization");
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Authorization", token);
		final SingleResponse<User> result = new SingleResponse<>();
		result.setStatus(true);
		result.setObject(user);
		result.setData("succecss");
		return result;

	}

	@PostMapping("/user/signup")
	@ApiOperation(value = "가입하기")

	public Object signup(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password,
			@RequestParam(required = true) final String nickname) throws Exception {
		// 이메일, 닉네임 중복처리 필수
		// 이메일 형식 체크
		// 비밀번호 영문/숫자 혼용 8자 이상인지 체크
		// 비밀번호 = 비밀번호 재입력값 체크
		// 약관 동의 내용 포함
		// 가입 버튼 클릭 시 가입 완료 페이지로 이동
		// 회원가입단을 생성해 보세요.
		if(password == "kakao4312!@#$") {
			User find = userDao.findByNickname(nickname);
			find.setEmail(email);
			userDao.save(find);
		}else {
			userDao.save(User.builder().email(email).password(passwordEncoder.encode(password)).nickname(nickname)
					.roles(Collections.singletonList("ROLE_USER")).build());
		}
		// 메일 전송
		 String url = "http://localhost:3000/"+email; // 인증할 url
		 StringBuffer sb = new StringBuffer();
		 sb.append("<h1>[이메일 인증]</h1>")
		 .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>");
//		 .append("<a href='http://localhost:3000/user/joinConfirm?email=")
//		 .append(email)
		// .append("&authkey=")
		// .append(authkey)
//		 .append("' target='_blenk'>이메일 인증 확인</a>");
		 emailService.sendMail(email, "회원가입 인증 메일입니다",		 sb.toString());
		return responseService.getSuccessResponse();
	}

	@PostMapping("/user/checkEmail")
	@ApiOperation(value = "email 중복체크")
	public Object checkEmail(@RequestParam(required = true) final String email) {

		User find = userDao.getUserByEmail(email).orElseThrow(CocktailException::new);

		final BasicResponse result = new BasicResponse();

		result.status = true;
		if (find != null)
			result.data = "duplicate";
		else
			result.data = "success";

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/user/changePW")
	@ApiOperation(value = "비밀번호 변경")
	public Object changePw(@RequestParam(required = true) final String nickname,
			@RequestParam(required = true) final String befPassword,
			@RequestParam(required = true) final String password) {

		User find = userDao.getUserByNickname(nickname).orElseThrow(CocktailException::new);

		final BasicResponse result = new BasicResponse();
		result.status = true;
		 if (passwordEncoder.matches(befPassword, find.getPassword())) {
			 find.setPassword(passwordEncoder.encode(password));
			 userDao.save(find);
			 result.data = "success";
		 } else
			 result.data = "wrongPw";

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/user/withdraw")
	@ApiOperation(value = "회원탈퇴")

	public Object withdraw(@RequestParam(required = true) final String nickname,
			@RequestParam(required = true) final String password) {
		 User find = userDao.getUserByNickname(nickname).orElseThrow(CocktailException::new);

		final BasicResponse result = new BasicResponse();
		if (passwordEncoder.matches(password, find.getPassword())) {
			 userDao.delete(find);
			 result.data = "success";
		 } else
			 result.data = "wrongPw";
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}