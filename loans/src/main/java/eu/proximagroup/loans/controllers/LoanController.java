package eu.proximagroup.loans.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.proximagroup.loans.costants.LoanCostants;
import eu.proximagroup.loans.dto.ResponseErrorDto;
import eu.proximagroup.loans.dto.ResponseSuccesDto;
import eu.proximagroup.loans.entities.Loan;
import eu.proximagroup.loans.services.LoanService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	private EntityManager entityManager;
	private LoanService loanService;

	public LoanController(LoanService loanService, EntityManager entityManager) {
		this.loanService = loanService;
		this.entityManager = entityManager;
	}

	@GetMapping
	public ResponseEntity<ResponseSuccesDto<List<Loan>>> index() {
		List<Loan> loans = this.loanService.getAll();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<List<Loan>>(HttpStatus.OK, LoanCostants.MESSAGE_200, loans));
	}

	@GetMapping("/{pathId}")
	public ResponseEntity<?> show(@PathVariable String pathId, HttpServletRequest request) {
		if (!pathId.matches("\\d+")) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto<String>(request.getRequestURI(),
					request.getMethod(), HttpStatus.BAD_REQUEST, LoanCostants.ERROR_ID_NUMERIC));
		}

		Long id = Long.parseLong(pathId);

		Loan loan = this.loanService.getById(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<Loan>(HttpStatus.OK, LoanCostants.MESSAGE_200, loan));

	}

	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Loan loan, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			List<String> errorMessages = new ArrayList<>();
			result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDto<List<String>>(
					request.getRequestURI(), request.getMethod(), HttpStatus.BAD_REQUEST, errorMessages));
		}

		Optional<Loan> existingLoan = loanService.getByMobileNumber(loan.getMobileNumber());
		if (existingLoan.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseErrorDto<String>(request.getRequestURI(), request.getMethod(),
							HttpStatus.BAD_REQUEST, LoanCostants.MESSAGE_ALREADY_PHONE));
		}

		Loan loanInserted = this.loanService.store(loan);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseSuccesDto<Loan>(HttpStatus.CREATED, LoanCostants.MESSAGE_201, loanInserted));

	}

	@PutMapping("/{pathId}")
	public ResponseEntity<?> update(@PathVariable String pathId, @Valid @RequestBody Loan loan, BindingResult result,
			HttpServletRequest request) {

		if (!pathId.matches("\\d+")) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto<String>(request.getRequestURI(),
					request.getMethod(), HttpStatus.BAD_REQUEST, LoanCostants.ERROR_ID_NUMERIC));
		}

		// Convertiamo l'ID in un Long
		Long id = Long.parseLong(pathId);

		if (result.hasErrors()) {
			List<String> errorMessages = new ArrayList<>();
			result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDto<List<String>>(
					request.getRequestURI(), request.getMethod(), HttpStatus.BAD_REQUEST, errorMessages));
		}

		this.loanService.getById(id);
		this.loanService.update(loan, id);
		this.entityManager.clear();

		Loan loanUpdated = this.loanService.getById(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<Loan>(HttpStatus.CREATED, LoanCostants.MESSAGE_201, loanUpdated));

	}

	@DeleteMapping("/{pathId}")
	public ResponseEntity<?> destroy(@PathVariable String pathId, HttpServletRequest request) {

		if (!pathId.matches("\\d+")) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto<String>(request.getRequestURI(),
					request.getMethod(), HttpStatus.BAD_REQUEST, LoanCostants.ERROR_ID_NUMERIC));
		}

		Long id = Long.parseLong(pathId);

		this.loanService.deleteById(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<String>(HttpStatus.NO_CONTENT, LoanCostants.MESSAGE_204, null));

	}
}
