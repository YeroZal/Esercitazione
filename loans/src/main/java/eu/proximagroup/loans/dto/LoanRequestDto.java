package eu.proximagroup.loans.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRequestDto {
	
	@NotEmpty(message = "mobile number is required")
	private String mobileNumber;
	
	@NotEmpty(message = "loan number is required")
	private String loanNumber;
	
	@NotEmpty(message = "loan type is required")
	private String loanType;
	
	@NotNull(message = "total loan is null")
	private int totalLoan;
	
	@NotNull(message = "amount paid is null")
	private int amountPaid;
	
	@NotNull(message = "outstanding amount is null")
	private int outstandingAmount;

}
