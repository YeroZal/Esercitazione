package eu.proximagroup.cards.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "card_number")
	private String cardNumber;
	
	@Column(name = "card_type")
	private String cardType;
	
	@Column(name = "total_limit")
	private int totalLimit;
	
	@Column(name = "amount_paid")
	private int amountPaid;
	
	@Column(name = "available_amount")
	private int availableAmount;

}
