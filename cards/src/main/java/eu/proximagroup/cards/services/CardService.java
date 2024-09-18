package eu.proximagroup.cards.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eu.proximagroup.cards.entities.Card;
import eu.proximagroup.cards.exceptions.ResourceNotFoundException;
import eu.proximagroup.cards.repositories.CardRepository;

@Service
public class CardService {

	private CardRepository cardRepository;

	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public List<Card> getAll() {
		return this.cardRepository.findAll();
	}

	public Card getById(Long id) {
		Card card = this.cardRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Card", "Id", id.toString()));

		return card;

	}

	public Card store(Card card) {
		return this.cardRepository.save(card);
	}

	public Optional<Card> findByMobileNumberAndCardNumber(String mobileNumber, String cardNumber) {
		return this.cardRepository.findByMobileNumberAndCardNumber(mobileNumber, cardNumber);
	}

	public Card update(Card card, Long id) {
		card.setId(id);
		return this.cardRepository.save(card);
	}

	public void deleteById(Long id) {
		this.cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card", "Id", id.toString()));

		this.cardRepository.deleteById(id);
	}
}
