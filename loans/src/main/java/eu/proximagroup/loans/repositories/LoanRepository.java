package eu.proximagroup.loans.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.loans.entities.Loan;


public interface LoanRepository extends JpaRepository<Loan, Long> {
	
	Optional<Loan> findByMobileNumber(String mobileNumber);

}
