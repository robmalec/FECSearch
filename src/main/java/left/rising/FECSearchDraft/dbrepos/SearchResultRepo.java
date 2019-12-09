package left.rising.FECSearchDraft.dbrepos;

import javax.naming.directory.SearchResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import left.rising.FECSearchDraft.entities.LocationSearchResult;

public interface SearchResultRepo extends JpaRepository<LocationSearchResult, Integer> {

	@Query("SELECT lsr FROM LocationSearchResult lsr WHERE lsr.city=:city AND lsr.state=:state AND lsr.electionYear=:electionYear")
	LocationSearchResult getSearchResultsFromCityStateAndElectionYear(@Param("city") String city, @Param("state") String state,
			@Param("electionYear") Integer electionYear);

}
