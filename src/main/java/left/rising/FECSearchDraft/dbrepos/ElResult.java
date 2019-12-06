package left.rising.FECSearchDraft.dbrepos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import left.rising.FECSearchDraft.entities.PoliticalParty;

@Entity
@Table(name="ElectionResults")
public class ElResult {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY) // 
	private Integer id;
	
	private PoliticalParty winningParty;
	private String rCandidate;
	private String dCandidate;
	
	private int electionYear;
	
	public ElResult() {}
	
	public ElResult(String line) throws Exception {
		String[] Fields = line.split(",");
		electionYear = Integer.valueOf(Fields[0]);
		switch (Fields[1].toLowerCase().charAt(0)) {
		case 'd':
			winningParty = PoliticalParty.DEMOCRAT;
			break;
		case 'r':
			winningParty = PoliticalParty.REPUBLICAN;
			break;
			default:
				throw new Exception("Invalid party input");
		}
		dCandidate = Fields[2];
		rCandidate = Fields[3];
	}
	
	public ElResult(PoliticalParty winningParty, String winningPresident,
			String losingCandidate, int electionYear) {
		super();
		this.winningParty = winningParty;
		this.rCandidate = winningPresident;
		this.dCandidate = losingCandidate;
		this.electionYear = electionYear;
	}

	public ElResult(Integer id, PoliticalParty winningParty, String winningPresident,
			String losingCandidate, int electionYear) {
		super();
		this.id = id;
		this.winningParty = winningParty;
		this.rCandidate = winningPresident;
		this.dCandidate = losingCandidate;
		this.electionYear = electionYear;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PoliticalParty getWinningParty() {
		return winningParty;
	}

	public void setWinningParty(PoliticalParty winningParty) {
		this.winningParty = winningParty;
	}

	public String getWinningPresident() {
		return rCandidate;
	}

	public void setWinningPresident(String winningPresident) {
		this.rCandidate = winningPresident;
	}

	public String getLosingCandidate() {
		return dCandidate;
	}

	public void setLosingCandidate(String losingCandidate) {
		this.dCandidate = losingCandidate;
	}

	public int getElectionYear() {
		return electionYear;
	}

	public void setElectionYear(int electionYear) {
		this.electionYear = electionYear;
	}

	@Override
	public String toString() {
		return "ElResult [id=" + id + ", winningParty=" + winningParty + ", winningPresident=" + rCandidate
				+ ", losingCandidate=" + dCandidate + ", electionYear="
				+ electionYear + "]";
	}
	
	
}
