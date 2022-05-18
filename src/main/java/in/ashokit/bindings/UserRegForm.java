package in.ashokit.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRegForm {

	private String firstName;
	private String lastName;
	private String userEmail;
	private long phno;
	private LocalDate dob;
	private String gender;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
}
