package com.cocktail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.FileDAO;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
// import com.cocktail.dao.UserDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.UploadFile;
import com.cocktail.model.user.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RestController
public class UserProfileController {
    // 디비 셋팅 후 주석을 푸세요.
    @Autowired
    UserDao userDao;

    @Autowired
    FileDAO filedao;

    @PostMapping("/user/profile")
    @ApiOperation(value = "유저프로필")
    public Object userProfile(@RequestParam(required = true) final String email) {
        // final User user = userDao.getUserByEmail(email);

        // final JSONObject userData = new JSONObject();
        // userData.put("nickname", user.getNickname());
        // userData.put("email", user.getEmail());
        // userData.put("intro", user.getIntro());
        // if (user.getImage() != null) {
        // userData.put("image", user.getImage());
        // } else {
        // // 기본 이미지
        // }
        // // userData.put("", user.)

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        // result.object = userData.toMap();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/user/updateProfile")
    @ApiOperation(value = "유저프로필 변경")
    public Object updateUserProfile(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final String intro) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        user.setIntro(intro);
        userDao.save(user);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = user;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value="/userprofileimage/{fid}")
    public Object userimage(@PathVariable int fid, @RequestBody String username) {
        System.out.println("fid       " +  fid );
        System.out.println("username       "  + username);
        UploadFile file = filedao.findById(fid);
        
        String str = "C:/image/";
        String filename = str.concat(file.getFileName());

        System.out.println(filename);

        User user = userDao.findByNickname(username);
        user.setImage(filename);
        userDao.save(user);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}