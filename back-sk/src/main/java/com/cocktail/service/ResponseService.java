package com.cocktail.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cocktail.model.BasicResponse;
import com.cocktail.model.ListResponse;
import com.cocktail.model.SingleResponse;

@Service
public class ResponseService {

    // enum으로 api 요청 결과에 대한 code, message를 정의합니다.
    public enum BasicResult {
        SUCCESS(true, "success");
    	
    	boolean status;
    	String data;

    	BasicResult(boolean status, String data){
        	this.status = status;
        	this.data = data;
        }
        
		public void setStatus(boolean status) {
			this.status = status;
		}

		public void setData(String data) {
			this.data = data;
		}

		public boolean isStatus() {
			return status;
		}
		public String getData() {
			return data;
		}

    }
    // 단일건 결과를 처리하는 메소드
    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse<T> result = new SingleResponse<>();
        result.setObject(data);
        setSuccessResponse(result);
        return result;
    }
    // 다중건 결과를 처리하는 메소드
    public <T> ListResponse<T> getListResponse(List<T> list) {
    	ListResponse<T> result = new ListResponse<>();
        result.setList(list);
        setSuccessResponse(result);
        return result;
    }
    // 성공 결과만 처리하는 메소드
    public BasicResponse getSuccessResponse() {
    	BasicResponse result = new BasicResponse();
        setSuccessResponse(result);
        return result;
    }
    // 실패 결과만 처리하는 메소드
    public BasicResponse getFailResult(String data, Object obj) {
    	BasicResponse result = new BasicResponse();
        result.setStatus(false);
        result.setData(data);
        return result;
    }
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResponse(BasicResponse result) {
        result.setStatus(BasicResult.SUCCESS.isStatus());
        result.setData(BasicResult.SUCCESS.getData());
    }
}