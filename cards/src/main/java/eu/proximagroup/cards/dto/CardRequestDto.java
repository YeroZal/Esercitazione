package eu.proximagroup.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDto {
	
	@NotEmpty(message = "mobile number is required")
	private String mobileNumber;
	
	@NotEmpty(message = "card number is required")
	private String cardNumber;
	
	@NotEmpty(message = "card type is required")
	private String cardType;
	
	@NotNull(message = "total limit is null")
	private int totalLimit;
	
	@NotNull(message = "amount paid is null")
	private int amountPaid;
	
	@NotNull(message = "available amount is null")
	private int availableAmount;

}
