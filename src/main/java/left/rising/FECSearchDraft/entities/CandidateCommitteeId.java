package left.rising.FECSearchDraft.entities;

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
	private String committee_id;
	
	//@ManyToOne
	//private Candidate candidate_assigned;
	
	public CandidateCommitteeId() {
		super();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommittee_id() {
		return committee_id;
	}

	public void setCommittee_id(String committee_id) {
		this.committee_id = committee_id;
	}
	
	

}
