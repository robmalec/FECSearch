package left.rising.FECSearchDraft.dbrepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import left.rising.FECSearchDraft.entities.PrimaryLocationSearchResult;

public interface PrimaryLocationSearchResultRepo extends JpaRepository<PrimaryLocationSearchResult, Integer> {

	@Query("FIND p FROM PrimaryLocationSearchResult p WHERE p.city=:city AND p.state=:state")
	PrimaryLocationSearchResult findByCityAndState(@Param("city") String city, @Param("state") String state);
}
