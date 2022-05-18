package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {

	//@Query("from UserDtlsEntity where userEmail=:empName")
	UserDtlsEntity findByUserEmailAndUserPwd(String userEmail, String userPwd);
	
	UserDtlsEntity findByUserEmail(String userEmail);
}
