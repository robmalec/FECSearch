package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import left.rising.FECSearchDraft.entities.CandFundsPerState;

@Entity
public class PerStateCandData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	private String stateCode;
	@OneToMany(mappedBy = "linkedState", orphanRemoval = true)
	private List<CandFundsPerState> candidateHierarchy;
	
	public PerStateCandData() {
		super();
	}

	public PerStateCandData(String stateCode, List<CandFundsPerState> candidateHierarchy) {
		super();
		this.stateCode = stateCode;
		this.candidateHierarchy = candidateHierarchy;
	}

	public PerStateCandData(Integer id, String stateCode, List<CandFundsPerState> candidateHierarchy) {
		super();
		this.id = id;
		this.stateCode = stateCode;
		this.candidateHierarchy = candidateHierarchy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public List<CandFundsPerState> getCandidateHierarchy() {
		return candidateHierarchy;
	}

	public void setCandidateHierarchy(List<CandFundsPerState> candidateHierarchy) {
		this.candidateHierarchy = candidateHierarchy;
	}
}
