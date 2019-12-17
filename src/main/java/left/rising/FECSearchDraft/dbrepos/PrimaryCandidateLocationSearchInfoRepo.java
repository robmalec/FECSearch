package left.rising.FECSearchDraft.dbrepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import left.rising.FECSearchDraft.entities.PrimaryCandidateLocationSearchInfo;

public interface PrimaryCandidateLocationSearchInfoRepo extends JpaRepository<PrimaryCandidateLocationSearchInfo, Integer>{
	@Query(value="FIND p FROM PrimaryLocationSearchResult p WHERE p.candidateName=:candidateName AND p.city=:city AND p.state=:state", nativeQuery = true)
	PrimaryCandidateLocationSearchInfo findByCandidateNameAndCityAndState(@Param("candidateName") String candidateName, @Param("city") String city, @Param("state") String state);

}
