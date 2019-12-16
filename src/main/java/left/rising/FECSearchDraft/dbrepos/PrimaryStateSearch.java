package left.rising.FECSearchDraft.dbrepos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PrimaryStateSearch {
	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private CandidateData candidate;
	
	private double funds;
	
	private String state;
	
	public PrimaryStateSearch() {
		super();
	}
	
	public PrimaryStateSearch(CandidateData candidate, double funds, String state) {
		super();
		this.candidate = candidate;
		this.funds = funds;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CandidateData getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateData candidate) {
		this.candidate = candidate;
	}

	public double getFunds() {
		return funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "CandFundsPerState [candidate=" + candidate + ", funds=" + funds + "]";
	}
}
