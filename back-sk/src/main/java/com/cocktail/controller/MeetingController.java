package com.cocktail.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.MeetingDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.meeting.Meeting;
import com.cocktail.model.user.User;

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
@RequestMapping(value = "/backend/meeting")
public class MeetingController {
	 @Autowired
	    private MeetingDao meetingDao;

	    @Autowired
	    private UserDao userDao;
	
	    @Autowired
	    private BoardDao boardDao;
	    
	    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	    public Object meetinglist() {

	        final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        result.object = meetingDao.findAll();
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    }

	    // 게시판 번호로 상세조회
	    @GetMapping(value = "/{mid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Meeting> getMeeting(@PathVariable int mid) {
	        return new ResponseEntity<Meeting>(meetingDao.findById(mid), HttpStatus.OK);
	    }

	    @PostMapping
	    public Object save(@RequestBody Meeting meeting) {
	        System.out.println(meeting);
	        meetingDao.save(meeting);
	        
	        final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    }

	    @PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Map<String, String>> updateMeeting(@RequestBody Meeting meeting) {
	    	meetingDao.save(meeting);
	        Map<String, String> resultMap = new HashMap<>();
	        resultMap.put("data", "success");
	        return new ResponseEntity<>(resultMap, HttpStatus.OK);
	    }

	    @DeleteMapping(value = "/{mid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Map<String, String>> deleteBoard(@PathVariable("mid") int mid) {
	        meetingDao.delete(meetingDao.findById(mid));
	        Map<String, String> resultMap = new HashMap<>();
	        resultMap.put("data", "Success");
	        return new ResponseEntity<>(resultMap, HttpStatus.OK);
	    }

	    @GetMapping(value = "/user/{user_uid}")
	    public Object getMeetingByUser(
	            @PathVariable final int user_uid) {
	    	User user = userDao.findById(user_uid).orElseThrow(CocktailException::new);
	        final List<Meeting> meetings = meetingDao.findAllByAuthor(user.getNickname());
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("boards", meetings);
	        return new ResponseEntity<>(resultMap, HttpStatus.OK);
	    }
}
