package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CanCommitteeRepo extends JpaRepository<CandidateCommitteeId, Integer>{
	List<CandidateCommitteeId> findByCandidateAssigned(CandidateData can);
}
