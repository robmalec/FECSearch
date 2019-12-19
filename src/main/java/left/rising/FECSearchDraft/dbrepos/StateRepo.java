package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepo extends JpaRepository<State, Integer>{
	public List<State> findByStateCode(String code);
	public List<State> findByStateName(String name);
}
