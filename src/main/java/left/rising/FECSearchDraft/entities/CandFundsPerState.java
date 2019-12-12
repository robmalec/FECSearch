package left.rising.FECSearchDraft.entities;

import left.rising.FECSearchDraft.dbrepos.CandidateData;

public class CandFundsPerState {
	private CandidateData candidate;
	private double funds;
	public CandFundsPerState() {}
	public CandFundsPerState(CandidateData candidate, double funds) {
		super();
		this.candidate = candidate;
		this.funds = funds;
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
	@Override
	public String toString() {
		return "CandFundsPerState [candidate=" + candidate + ", funds=" + funds + "]";
	}
}
