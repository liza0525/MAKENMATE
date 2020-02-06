// package com.cocktail.controller;
//
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.cocktail.dao.UserDao;
// import com.cocktail.exception.CUserNotFoundException;
// import com.cocktail.model.BasicResponse;
// import com.cocktail.model.SingleResponse;
// import com.cocktail.model.user.User;
// import com.cocktail.service.ResponseService;
//
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiImplicitParam;
// import io.swagger.annotations.ApiImplicitParams;
// import io.swagger.annotations.ApiOperation;
// import io.swagger.annotations.ApiParam;
// import lombok.RequiredArgsConstructor;
//
// @Api(tags = {"2. User"})
// @RequiredArgsConstructor
// @RestController
// @RequestMapping(value = "/v1")
// public class UserController {
//
// private final UserDao userDao;
// private final ResponseService responseService; // 결과를 처리할 Service
//
// @ApiImplicitParams({
// @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token",
// required = false, dataType = "String", paramType = "header")
// })
// @ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원을 조회한다")
// @GetMapping(value = "/user")
// public SingleResponse<User> findUserById(@ApiParam(value = "언어", defaultValue
// = "ko") @RequestParam String lang) {
// // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
// Authentication authentication =
// SecurityContextHolder.getContext().getAuthentication();
// String id = authentication.getName();
// // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
// return
// responseService.getSingleResponse(userDao.findById(id).orElseThrow(CUserNotFoundException::new));
// }
//
// @ApiImplicitParams({
// @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token",
// required = true, dataType = "String", paramType = "header")
// })
// @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
// @PutMapping(value = "/user")
// public SingleResponse<User> modify(
// @ApiParam(value = "닉네임", required = true) @RequestParam String nickname,
// @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
// User user = User.builder()
// .nickname(nickname)
// .build();
// return responseService.getSingleResponse(userDao.save(user));
// }
//
// @ApiImplicitParams({
// @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token",
// required = true, dataType = "String", paramType = "header")
// })
// @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
// @DeleteMapping(value = "/user/{msrl}")
// public BasicResponse delete(
// @ApiParam(value = "id", required = true) @PathVariable String id) {
// userDao.deleteById(id);
// // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
// return responseService.getSuccessResponse();
// }
// }