package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomStatePropertyRepo extends JpaRepository<CustomStateProperty, Integer> {
	
	@Query("FROM CustomStateProperty WHERE category=:cat AND stateCode=:code")
	public List<CustomStateProperty> getPropertyFromState(@Param("cat")String category, @Param("code")String stateCode);
	public List<CustomStateProperty> findByStateCode(String stateCode);
	public List<CustomStateProperty> findByCategory(String category);
	
}
