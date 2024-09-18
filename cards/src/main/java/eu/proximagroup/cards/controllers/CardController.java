package eu.proximagroup.cards.controllers;

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

import eu.proximagroup.cards.costants.CardCostants;
import eu.proximagroup.cards.dto.ResponseErrorDto;
import eu.proximagroup.cards.dto.ResponseSuccesDto;
import eu.proximagroup.cards.entities.Card;
import eu.proximagroup.cards.services.CardService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cards")
public class CardController {

	private EntityManager entityManager;
	private CardService cardService;

	public CardController(CardService cardService, EntityManager entityManager) {
		this.cardService = cardService;
		this.entityManager = entityManager;
	}

	@GetMapping
	public ResponseEntity<ResponseSuccesDto<List<Card>>> index() {
		List<Card> card = this.cardService.getAll();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<List<Card>>(HttpStatus.OK, CardCostants.MESSAGE_200, card));
	}

	@GetMapping("/{pathId}")
	public ResponseEntity<?> show(@PathVariable String pathId, HttpServletRequest request) {
		if (!pathId.matches("\\d+")) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto<String>(request.getRequestURI(),
					request.getMethod(), HttpStatus.BAD_REQUEST, CardCostants.ERROR_ID_NUMERIC));
		}

		Long id = Long.parseLong(pathId);

		Card card = this.cardService.getById(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<Card>(HttpStatus.OK, CardCostants.MESSAGE_200, card));

	}

	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody Card card, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			List<String> errorMessages = new ArrayList<>();
			result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDto<List<String>>(
					request.getRequestURI(), request.getMethod(), HttpStatus.BAD_REQUEST, errorMessages));
		}

		Optional<Card> existingLoan = cardService.findByMobileNumberAndCardNumber(card.getMobileNumber(),
				card.getCardNumber());
		if (existingLoan.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseErrorDto<String>(request.getRequestURI(), request.getMethod(),
							HttpStatus.BAD_REQUEST, CardCostants.MESSAGE_ALREADY_PHONE_CARD));
		}

		Card cardInserted = this.cardService.store(card);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseSuccesDto<Card>(HttpStatus.CREATED, CardCostants.MESSAGE_201, cardInserted));

	}

	@PutMapping("/{pathId}")
	public ResponseEntity<?> update(@PathVariable String pathId, @Valid @RequestBody Card card, BindingResult result,
			HttpServletRequest request) {

		if (!pathId.matches("\\d+")) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto<String>(request.getRequestURI(),
					request.getMethod(), HttpStatus.BAD_REQUEST, CardCostants.ERROR_ID_NUMERIC));
		}

		Long id = Long.parseLong(pathId);

		if (result.hasErrors()) {
			List<String> errorMessages = new ArrayList<>();
			result.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorDto<List<String>>(
					request.getRequestURI(), request.getMethod(), HttpStatus.BAD_REQUEST, errorMessages));
		}

		this.cardService.getById(id);
		this.cardService.update(card, id);
		this.entityManager.clear();

		Card cardUpdated = this.cardService.getById(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<Card>(HttpStatus.CREATED, CardCostants.MESSAGE_201, cardUpdated));

	}

	@DeleteMapping("/{pathId}")
	public ResponseEntity<?> destroy(@PathVariable String pathId, HttpServletRequest request) {

		if (!pathId.matches("\\d+")) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto<String>(request.getRequestURI(),
					request.getMethod(), HttpStatus.BAD_REQUEST, CardCostants.ERROR_ID_NUMERIC));
		}

		Long id = Long.parseLong(pathId);

		this.cardService.deleteById(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseSuccesDto<String>(HttpStatus.NO_CONTENT, CardCostants.MESSAGE_204, null));

	}

}
