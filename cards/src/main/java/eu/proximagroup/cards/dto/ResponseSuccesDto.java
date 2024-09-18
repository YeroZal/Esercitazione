package eu.proximagroup.cards.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSuccesDto<T> {
	
	private HttpStatus code;
	private String message;
	private T data;
	private LocalDateTime time;
	
	public ResponseSuccesDto(HttpStatus code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.time = LocalDateTime.now();
	}

}
