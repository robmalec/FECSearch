package left.rising.FECSearchDraft.entities;

public class DBDonationResult {
	private String state;
	private Double total;
	private String committee_id;
	private int count;
	private String state_full;
	private int cycle;
		
	public DBDonationResult() {}

	public DBDonationResult(String state, Double total, String committee_id, int count, String state_full, int cycle) {
		super();
		this.state = state;
		this.total = total;
		this.committee_id = committee_id;
		this.count = count;
		this.state_full = state_full;
		this.cycle = cycle;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getCommittee_id() {
		return committee_id;
	}

	public void setCommittee_id(String committee_id) {
		this.committee_id = committee_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getState_full() {
		return state_full;
	}

	public void setState_full(String state_full) {
		this.state_full = state_full;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "DBDonationResult [state=" + state + ", total=" + total + ", committee_id=" + committee_id + ", count="
				+ count + ", state_full=" + state_full + ", cycle=" + cycle + "]";
	}
	
}
