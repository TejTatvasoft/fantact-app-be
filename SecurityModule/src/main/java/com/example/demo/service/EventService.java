//package com.example.demo.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//
//import org.springframework.stereotype.Service;
//
//import com.example.demo.dto.response.ResponseEntity;
//import com.example.demo.entity.Event;
//import com.example.demo.repository.EventRepository;
//
//@Service
//public class EventService {
//
//	@Autowired
//	private EventRepository eventRepository;
//
//	@Autowired
//	private MessageSource messageSource;
//
//	public ResponseEntity getAllEvnets() {
//		// TODO Auto-generated method stub
//
//		List<Event> events= eventRepository.findAll();
//
//		return new ResponseEntity(true,
//				messageSource.getMessage("MESSAGE_VIEW_BOOK_SUCCESS", null, LocaleContextHolder.getLocale()),
//				events);
//	}
//}
