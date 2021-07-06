package guru.sprfwk.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


/**
 * The type Controller exception handler.
 * Uses ControllerAdvice to create a global exception handler.
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
	/**
	 * Handle number format ModelAndView.
	 *
	 * @param exception the exception
	 * @return the model and view
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormat(Exception exception) {
		log.error("Handling Number Format Exception: " + exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setStatus(HttpStatus.BAD_REQUEST);
		modelAndView.setViewName("error");
		modelAndView.addObject("exception", exception);
		modelAndView.addObject("status", 400);
		modelAndView.addObject("titleMsg", "400 Bad Request Error");
		
		return modelAndView;
	}
}
