package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidateDataRepo extends JpaRepository<CandidateData,Integer>{
	
	@Query("SELECT id FROM CandidateData WHERE name=:iName")
	public List<Integer>  getCandidateIdFromName(@Param("iName") String name);
	
	@Query("SELECT name FROM CandidateData WHERE id=:ID")
	public List<String>  getCandidateNameFromId(@Param("ID") int ID);
}
