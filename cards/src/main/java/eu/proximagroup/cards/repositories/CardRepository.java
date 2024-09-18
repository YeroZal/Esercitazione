package eu.proximagroup.cards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.proximagroup.cards.entities.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	
	Optional<Card> findByMobileNumberAndCardNumber(String mobileNumber, String cardNumber);

}
