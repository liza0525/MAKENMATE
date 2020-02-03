package com.cocktail.controller;

// import com.cocktail.dao.UserDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.user.User;
import org.json.JSONObject;

import com.cocktail.model.BasicResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RestController
public class MyPageController {
    // 디비 셋팅 후 주석을 푸세요.
    // @Autowired
    // UserDao userDao;

    @PostMapping("/user/MyPage")
    @ApiOperation(value = "마이페이지")
    public Object mypage(@RequestParam(required = true) final String email) {
        // final User user = userDao.getUserByEmail(email);

        // final JSONObject userData = new JSONObject();
        // userData.put("nickname", user.getNickname());
        // userData.put("email", user.getEmail());
        // if (user.getImage() != null) {
        // userData.put("image", user.getImage());
        // } else {
        // // 기본 이미지
        // }
        // // userData.put("", user.)

        final BasicResponse result = new BasicResponse();
        // result.status = true;
        // result.data = "success";
        // result.object = userData.toMap();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}