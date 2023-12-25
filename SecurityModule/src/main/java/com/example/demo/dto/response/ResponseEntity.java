package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity {
	
	private boolean responseStatus;
	
	private String responseMessage;
	
	private Object responseData;

}
