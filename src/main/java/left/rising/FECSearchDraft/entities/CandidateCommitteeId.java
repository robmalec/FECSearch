package left.rising.FECSearchDraft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import left.rising.FECSearchDraft.dbrepos.CandidateData;

@Entity
@Table(name="committee_ids")
public class CandidateCommitteeId {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer election_year;
	@ManyToOne
	private CandidateData candidate_assigned;
	private String committee_id;
	
	public CandidateCommitteeId() {
		super();
	}

	public CandidateCommitteeId(Integer id, Integer election_year, CandidateData candidate_assigned,
			String committee_id) {
		super();
		this.id = id;
		this.election_year = election_year;
		this.candidate_assigned = candidate_assigned;
		this.committee_id = committee_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getElection_year() {
		return election_year;
	}

	public void setElection_year(Integer election_year) {
		this.election_year = election_year;
	}

	public CandidateData getCandidate_assigned() {
		return candidate_assigned;
	}

	public void setCandidate_assigned(CandidateData candidate_assigned) {
		this.candidate_assigned = candidate_assigned;
	}

	public String getCommittee_id() {
		return committee_id;
	}

	public void setCommittee_id(String committee_id) {
		this.committee_id = committee_id;
	}
	
	
	
	

}
