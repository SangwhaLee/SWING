package com.swing.note.controller;

import com.swing.note.model.service.NoteService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/note")
@Api(tags = {"회원 관리 API"})
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
}
