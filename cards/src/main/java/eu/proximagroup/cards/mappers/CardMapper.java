package eu.proximagroup.cards.mappers;

import eu.proximagroup.cards.dto.CardRequestDto;
import eu.proximagroup.cards.dto.CardResponseDto;
import eu.proximagroup.cards.entities.Card;

public class CardMapper {
	
	public static Card toEntity(CardRequestDto cardRequestDto) {
		
		Card card = new Card();
		card.setMobileNumber(cardRequestDto.getMobileNumber());
		card.setCardNumber(cardRequestDto.getCardNumber());
		card.setCardType(cardRequestDto.getCardType());
		card.setTotalLimit(cardRequestDto.getTotalLimit());
		card.setAmountPaid(cardRequestDto.getAmountPaid());
		card.setAvailableAmount(cardRequestDto.getAvailableAmount());
		
		return card;
	}
	
	public static CardResponseDto toResponseDto(Card card) {
		CardResponseDto cardResponseDto = new CardResponseDto();
		cardResponseDto.setId(card.getId());
		cardResponseDto.setMobileNumber(card.getMobileNumber());
		cardResponseDto.setCardNumber(card.getCardNumber());
		cardResponseDto.setCardType(card.getCardType());
		cardResponseDto.setTotalLimit(card.getTotalLimit());
		cardResponseDto.setAmountPaid(card.getAmountPaid());
		cardResponseDto.setAvailableAmount(card.getAvailableAmount());
		
		return cardResponseDto;
	}

}
