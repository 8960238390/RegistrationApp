package in.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.StateMasterEntity;

public interface StateMasterRepo extends JpaRepository<StateMasterEntity, Integer> {

//	@Query("from StateMasterEntity where countryId=:countryId")
	public List<StateMasterEntity> findByCountryId(Integer countryId);
}
