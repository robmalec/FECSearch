package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerStateCandDataRepo extends JpaRepository<PerStateCandData, Integer>{
	public List<PerStateCandData> findByStateCode(String code);
}
