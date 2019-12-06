package left.rising.FECSearchDraft.dbrepos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import left.rising.FECSearchDraft.entities.PoliticalParty;

@Entity
@Table(name = "candidates")
public class CandidateData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //
	private Integer id;

	String name;
	PoliticalParty afiliatedParty;
	
	@OneToMany(mappedBy = "candidate_assigned", orphanRemoval = true)
	List<CandidateCommitteeId> committee;
	
	public CandidateData() {}
	
	public CandidateData(String name, PoliticalParty afiliatedParty) {
		super();
		this.name = name;
		this.afiliatedParty = afiliatedParty;
	}

	public CandidateData(Integer id, String name, PoliticalParty afiliatedParty) {
		super();
		this.id = id;
		this.name = name;
		this.afiliatedParty = afiliatedParty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PoliticalParty getAfiliatedParty() {
		return afiliatedParty;
	}

	public void setAfiliatedParty(PoliticalParty afiliatedParty) {
		this.afiliatedParty = afiliatedParty;
	}
}
