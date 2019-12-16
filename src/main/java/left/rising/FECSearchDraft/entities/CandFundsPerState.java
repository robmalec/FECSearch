package left.rising.FECSearchDraft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import left.rising.FECSearchDraft.dbrepos.CandidateData;
import left.rising.FECSearchDraft.dbrepos.PerStateCandData;

@Entity
public class CandFundsPerState {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //
	private Integer id;
	
	@ManyToOne
	private PerStateCandData linkedState;
	private int candId;
	private double funds;
	public CandFundsPerState() {}
	public CandFundsPerState(int candId, double funds) {
		super();
		this.candId = candId;
		this.funds = funds;
	}
	public CandFundsPerState(PerStateCandData linkedState, CandidateData candidate, double funds) {
		super();
		this.linkedState = linkedState;
		this.candId = candId;
		this.funds = funds;
	}
	public CandFundsPerState(Integer id, PerStateCandData linkedState, CandidateData candidate, double funds) {
		super();
		this.id = id;
		this.linkedState = linkedState;
		this.candId = candId;
		this.funds = funds;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public PerStateCandData getLinkedState() {
		return linkedState;
	}
	public void setLinkedState(PerStateCandData linkedState) {
		this.linkedState = linkedState;
	}
	public int getCandId() {
		return candId;
	}
	public void setCandId(CandidateData candidate) {
		this.candId = candId;
	}
	public double getFunds() {
		return funds;
	}
	public void setFunds(double funds) {
		this.funds = funds;
	}
	
	@Override
	public String toString() {
		return "CandFundsPerState [id=" + id + ", linkedState=" + linkedState + ", candId=" + candId + ", funds="
				+ funds + "]";
	}
	
}
