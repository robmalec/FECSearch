package left.rising.FECSearchDraft.dbrepos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="committee_ids")
public class CandidateCommitteeId {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer electionYear;
	@ManyToOne
	private CandidateData candidateAssigned;
	private String committeeId;
	
	public CandidateCommitteeId() {
		super();
	}

	public CandidateCommitteeId(Integer id, Integer election_year, CandidateData candidate_assigned,
			String committee_id) {
		super();
		this.id = id;
		this.electionYear = election_year;
		this.candidateAssigned = candidate_assigned;
		this.committeeId = committee_id;
	}

	public CandidateCommitteeId(Integer election_year, CandidateData candidate_assigned,
			String committee_id) {
		super();
		this.electionYear = election_year;
		this.candidateAssigned = candidate_assigned;
		this.committeeId = committee_id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getElection_year() {
		return electionYear;
	}

	public void setElection_year(Integer election_year) {
		this.electionYear = election_year;
	}

	public CandidateData getCandidate_assigned() {
		return candidateAssigned;
	}

	public void setCandidate_assigned(CandidateData candidate_assigned) {
		this.candidateAssigned = candidate_assigned;
	}

	public String getCommittee_id() {
		return committeeId;
	}

	public void setCommittee_id(String committee_id) {
		this.committeeId = committee_id;
	}
}
