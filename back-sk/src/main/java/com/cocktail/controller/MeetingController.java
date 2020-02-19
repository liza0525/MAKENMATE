package com.cocktail.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jni.Mmap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.MeetingDao;
import com.cocktail.dao.UserDao;
import com.cocktail.dao.UserMeetingDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.meeting.Mdetail;
import com.cocktail.model.meeting.Meeting;
import com.cocktail.model.meeting.UserMeeting;
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
	    private UserMeetingDao userMeetingDao;
	    
	    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	    public Object meetinglist() {

	        final BasicResponse result = new BasicResponse();
	        
	        List<Meeting> meetings = meetingDao.findAll();
	        System.out.println(meetings.toString());
//	        for (int i = 0; i < meetings.size(); i++) {
//				meetings.get(i).setUsermeeting(userMeetingDao.findAllByMeeting_mid(meetings.get(i).getMid()));
//			}
	        System.out.println(meetings.get(0).getUsermeeting());
	        result.status = true;
	        result.data = "success";
	        result.object = meetings;
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    }

	    @GetMapping(value = "/one" ,produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Meeting> getMeeting(@RequestParam(required = true) String latitude,
	    		@RequestParam(required = true) String longitude) {
	        return new ResponseEntity<Meeting>(meetingDao.findByLatitudeAndLongitude(latitude, longitude), HttpStatus.OK);
	    }
	    @PostMapping
	    public Object save(@RequestBody Mdetail mdetail) {
	        Meeting m = new Meeting();
	        User find = userDao.findByNickname(mdetail.getAuthor());
	        m.setMid(0);
	        m.setAuthor(mdetail.getAuthor());
	        m.setTitle(mdetail.getTitle());
	        m.setLatitude(mdetail.getLatitude());
	        m.setLongitude(mdetail.getLongitude());
	        m.setPlace(mdetail.getPlace());
	        m.setCount(mdetail.getCount());
	        m.setDate(mdetail.getDate());
	        m.setPeople(1); 
	        meetingDao.save(m);
	        userMeetingDao.save(new UserMeeting((long) 0, find, m, find.getImage(), mdetail.getAuthor()));
	        
	        final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        result.object = meetingDao.findAll();
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
	    public Object deleteMeeting(@PathVariable("mid") int mid) {
	    	List<UserMeeting> um = userMeetingDao.findAllByMeeting_mid(mid);
	    	for (int i = 0; i < um.size(); i++) 
	    		userMeetingDao.delete(um.get(i));
	        meetingDao.delete(meetingDao.findById(mid));
	        final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        result.object = meetingDao.findAll();
	        return new ResponseEntity<>(result, HttpStatus.OK);
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
	    @PostMapping(value = "/user")
	    public ResponseEntity<Map<String, String>> addMeeting(@RequestParam(required = true) String username,
	    		@RequestParam(required = true) int mid) {
	    	Map<String, String> resultMap = new HashMap<>();
	    	UserMeeting um = new UserMeeting();
	    	User find = userDao.findByNickname(username);
	    	Meeting meet = meetingDao.getOne(mid);
	    	meet.setPeople(meet.getPeople() + 1);
	    	um.setUser(find);
	    	um.setMeeting(meet);
    		um.setUserImg(find.getImage());
	    	um.setUsername(username);
	    	
	    	meetingDao.save(meet);
	    	if(userMeetingDao.findByUser_uidAndMeeting_mid(find.getUid(), mid) == null)
	    		userMeetingDao.save(um);
	    	resultMap.put("status", "true");
	    	resultMap.put("data", "success");
	        return new ResponseEntity<>(resultMap, HttpStatus.OK);
	    }
}
