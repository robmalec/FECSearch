package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ElResultRepo extends JpaRepository<ElResult, Integer> {
	List<ElResult> findByElectionYear(Integer year);
}
