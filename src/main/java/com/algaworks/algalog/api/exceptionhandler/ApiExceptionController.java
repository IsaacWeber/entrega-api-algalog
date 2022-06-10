package com.algaworks.algalog.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.domain.exception.EntityNotFoundException;
import com.algaworks.algalog.domain.exception.NegotiationException;


@ControllerAdvice
public class ApiExceptionController 
	extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource msgSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Algum campo não foi preenchido corretamente."
				+ " Preencha-os corretamente e tente novamente");
		
		List<Problema.Campo> campos = new ArrayList<>(5); //create a 'campos' list 
		String cmpNome = ""; //nome a ser usado no loop de objs com erros
		String cmpMsg = ""; //msg '''''''''
		
		for(ObjectError objError: 
			ex.getBindingResult().getAllErrors()) { //get all objs errors
			
			cmpNome = ((FieldError) objError).getField(); //get the field error
			cmpMsg = msgSource.getMessage(objError, 
					LocaleContextHolder.getLocale());//objError.getDefaultMessage(); //get default msg retrieved by some exception
			
			Problema.Campo cmp = new Problema.Campo(); //instancia campo
			cmp.setNome(cmpNome); //add name
			cmp.setMensagem(cmpMsg); //add msg
			
			campos.add(cmp); //add campo 
		}
		
		problema.setCampos(campos); //add campos list to campos problema list

		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	
	@ExceptionHandler(NegotiationException.class)
	public ResponseEntity<Object> handleNegotiationException(
			NegotiationException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, 
				new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(
			NegotiationException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, 
				new HttpHeaders(), status, request);
	}
	

}
