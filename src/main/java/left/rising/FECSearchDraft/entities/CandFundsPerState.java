package left.rising.FECSearchDraft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import left.rising.FECSearchDraft.dbrepos.CandidateData;

@Entity
public class CandFundsPerState {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //
	private Integer id;
	
	private String stateCode;
	private int candId;
	private int year;
	private double funds;
	
	public CandFundsPerState() {}

	public CandFundsPerState(String stateCode, int candId, int year, double funds) {
		super();
		this.stateCode = stateCode;
		this.candId = candId;
		this.year = year;
		this.funds = funds;
	}

	public CandFundsPerState(Integer id, String stateCode, int candId, int year, double funds) {
		super();
		this.id = id;
		this.stateCode = stateCode;
		this.candId = candId;
		this.year = year;
		this.funds = funds;
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

	public int getCandId() {
		return candId;
	}

	public void setCandId(int candId) {
		this.candId = candId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getFunds() {
		return funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}
	
	public void addFunds(double amount) {
		funds += amount;
	}

	@Override
	public String toString() {
		return "CandFundsPerState [id=" + id + ", stateCode=" + stateCode + ", candId=" + candId + ", year=" + year
				+ ", funds=" + funds + "]";
	}
}
