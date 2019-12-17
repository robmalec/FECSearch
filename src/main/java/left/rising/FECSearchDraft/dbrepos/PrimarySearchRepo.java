package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrimarySearchRepo extends JpaRepository<PrimaryStateSearch, Integer>{
	@Query("SELECT p FROM PrimaryStateSearch p WHERE p.candidate=:iCandidateId AND p.state=:iState")
	PrimaryStateSearch findByIdAndState(@Param("iCandidateId") CandidateData candidate, @Param("iState") String state);
	
	@Query("SELECT p FROM PrimaryStateSearch p WHERE p.state=:iState")
	List<PrimaryStateSearch> findByState(@Param("iState") String state);
	
}
