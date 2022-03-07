package com.perscholas.scorekeeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@Slf4j
@Controller
public class ErrorController {
	@GetMapping("/error/404")
	public String error404(HttpServletRequest request){
		String origialUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
		log.error("Requested URL not found : " + request.getMethod() + " " + origialUri);
		return "/error/404";
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ModelAndView accessDenied(HttpServletRequest request, Exception ex){
		ModelAndView response = new ModelAndView("/error/404");
		log.error(ex.getMessage());
		return response;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, Exception ex){
		ModelAndView response = new ModelAndView("error/500");

		return response;
	}
}
