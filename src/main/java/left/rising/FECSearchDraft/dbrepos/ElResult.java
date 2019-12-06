package left.rising.FECSearchDraft.dbrepos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import left.rising.FECSearchDraft.entities.PoliticalParty;

@Entity
@Table(name = "ElectionResults")
public class ElResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //
	private Integer id;

	private PoliticalParty winningParty;
	private int rCandId;
	private int dCandId;

	private int electionYear;


	public ElResult() {
	}
	
	public ElResult(Integer id, PoliticalParty winningParty, int rCandId, int dCandId, int electionYear) {
		super();
		this.id = id;
		this.winningParty = winningParty;
		this.rCandId = rCandId;
		this.dCandId = dCandId;
		this.electionYear = electionYear;
	}


	public ElResult(PoliticalParty winningParty, int rCandId, int dCandId, int electionYear) {
		super();
		this.winningParty = winningParty;
		this.rCandId = rCandId;
		this.dCandId = dCandId;
		this.electionYear = electionYear;
	}

	/*
	public ElResult(String[] Fields) throws Exception {

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
		rCandId = getIdFromCandidateList(Fields[2], PoliticalParty.REPUBLICAN);
		dCandId = getIdFromCandidateList(Fields[3], PoliticalParty.DEMOCRAT);
	}
	*/
	
	//Standard getters and setters

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

	public int getrCandId() {
		return rCandId;
	}

	public void setrCandId(int rCandId) {
		this.rCandId = rCandId;
	}

	public int getdCandId() {
		return dCandId;
	}

	public void setdCandId(int dCandId) {
		this.dCandId = dCandId;
	}

	public int getElectionYear() {
		return electionYear;
	}

	public void setElectionYear(int electionYear) {
		this.electionYear = electionYear;
	}
}
