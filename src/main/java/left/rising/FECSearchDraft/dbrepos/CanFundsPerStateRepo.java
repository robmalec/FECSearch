package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import left.rising.FECSearchDraft.entities.CandFundsPerState;

public interface CanFundsPerStateRepo extends JpaRepository<CandFundsPerState, Integer> {

	public List<CandFundsPerState> findByStateCode(String id);
}
