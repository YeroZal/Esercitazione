package eu.proximagroup.accounts.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name = "customers")
public class Customer extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_name")
    @NotEmpty(message = "first_name is required")
	@Size(min = 3, max = 100, message = "enter a value between 3 and 100 characters")
	private String firstName;

	@Column(name = "last_name")
    @NotEmpty(message = "last_name is required")
	@Size(min = 3, max = 100, message = "enter a value between 3 and 100 characters")
	private String lastName;

	@Column(name = "email", unique = true)
    @NotEmpty(message = "email is required")
	@Email(message = "email is not correct")
	//@Email(regexp = "[0-9._%+-]+@[0-9.-]+\\.{2,3}")
	private String email;

	@Column(name = "mobile_number", unique = true)
    @NotEmpty(message = "mobile_number is required")
	@Pattern(regexp = "^(\\+|[0]{2})[0-9]{11,14}$")
	private String mobileNumber;
	
	@Column(name = "sex")
	@NotEmpty(message = "sex is required")
	private String sex;
	
	@OneToMany(mappedBy = "customer")
	@JsonManagedReference
	private List<Account> accounts;
	
	@PrePersist
	@PreUpdate
	private void preEmail() {
		this.email = this.email.toLowerCase();
	}
	
}
