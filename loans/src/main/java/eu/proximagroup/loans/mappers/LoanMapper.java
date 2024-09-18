package eu.proximagroup.loans.mappers;

import eu.proximagroup.loans.dto.LoanRequestDto;
import eu.proximagroup.loans.dto.LoanResponseDto;
import eu.proximagroup.loans.entities.Loan;

public class LoanMapper {
	
	public static Loan toEntity(LoanRequestDto loanRequestDto) {
		
		Loan loan = new Loan();
		loan.setMobileNumber(loanRequestDto.getMobileNumber());
		loan.setLoanNumber(loanRequestDto.getLoanNumber());
		loan.setLoanType(loanRequestDto.getLoanType());
		loan.setTotalLoan(loanRequestDto.getTotalLoan());
		loan.setAmountPaid(loanRequestDto.getAmountPaid());
		loan.setOutstandingAmount(loanRequestDto.getOutstandingAmount());
		
		return loan;
	}
	
	public static LoanResponseDto toResponseDto(Loan loan) {
		LoanResponseDto loanResponseDto = new LoanResponseDto();
		loanResponseDto.setId(loan.getId());
		loanResponseDto.setMobileNumber(loan.getMobileNumber());
		loanResponseDto.setLoanNumber(loan.getLoanNumber());
		loanResponseDto.setLoanType(loan.getLoanType());
		loanResponseDto.setTotalLoan(loan.getTotalLoan());
		loanResponseDto.setAmountPaid(loan.getAmountPaid());
		loanResponseDto.setOutstandingAmount(loan.getOutstandingAmount());
		
		return loanResponseDto;
	}

}
