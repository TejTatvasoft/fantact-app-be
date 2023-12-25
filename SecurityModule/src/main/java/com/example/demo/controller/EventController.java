//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.dto.response.ResponseEntity;
//import com.example.demo.repository.EventRepository;
//import com.example.demo.service.EventService;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/api/event")
//@CrossOrigin("*")
//public class EventController {
//
//	@Autowired
//	private MessageSource messageSource;
//
//	@Autowired
//	private EventService eventService;
//
//	@GetMapping("/events")
//	public ResponseEntity getAllEvents(HttpServletRequest request) {
//		try {
//			return eventService.getAllEvnets();
//		}
//		catch(Exception e) {
//			return new ResponseEntity(false,
//					messageSource.getMessage(
//							"MESSAGE_ERROR_EXCEPTION_OCCURRED",
//							 null,
//							 LocaleContextHolder.getLocale()),
//					null);
//		}
//	}
//}
