package eu.proximagroup.loans.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.proximagroup.loans.entities.Loan;
import eu.proximagroup.loans.exceptions.ResourceNotFoundException;
import eu.proximagroup.loans.repositories.LoanRepository;

@Service
public class LoanService {

	private LoanRepository loanRepository;

	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	public List<Loan> getAll() {
		return this.loanRepository.findAll();
	}

	public Loan getById(Long id) {
		Loan loan = this.loanRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Loan", "Id", id.toString()));

		return loan;

	}

	public Loan store(Loan loan) {
		return this.loanRepository.save(loan);
	}

	public Optional<Loan> getByMobileNumber(String mobileNumber) {
		return this.loanRepository.findByMobileNumber(mobileNumber);
	}

	public Loan update(Loan loan, Long id) {
		loan.setId(id);
		return this.loanRepository.save(loan);
	}

	public void deleteById(Long id) {
		this.loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan", "Id", id.toString()));

		this.loanRepository.deleteById(id);
	}

}
