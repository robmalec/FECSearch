package left.rising.FECSearchDraft.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateResults {

	private String candidate_id;
	private Integer count;
	private String state_full;
	private String state;
	//Double?
	private Integer total;
	private Integer cycle;
	
	public CandidateResults() {
		
	}

	public CandidateResults(String candidate_id, Integer count, String state_full, String state, Integer total,
			Integer cycle) {
		super();
		this.candidate_id = candidate_id;
		this.count = count;
		this.state_full = state_full;
		this.state = state;
		this.total = total;
		this.cycle = cycle;
	}

	public String getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(String candidate_id) {
		this.candidate_id = candidate_id;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getState_full() {
		return state_full;
	}

	public void setState_full(String state_full) {
		this.state_full = state_full;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "CandidateResults [candidate_id=" + candidate_id + ", count=" + count + ", state_full=" + state_full
				+ ", state=" + state + ", total=" + total + ", cycle=" + cycle + "]";
	}
	
	
	
	
	
}
