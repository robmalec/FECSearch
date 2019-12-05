package left.rising.FECSearchDraft.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipResults {

	private Integer total;
	private String state_full;
	private String zip;
	private String committee_id;
	private String state;
	private Integer cycle;
	private Integer count;
	
	
	public ZipResults() {
		
	}


	public ZipResults(Integer total, String state_full, String zip, String committee_id, String state, Integer cycle,
			Integer count) {
		super();
		this.total = total;
		this.state_full = state_full;
		this.zip = zip;
		this.committee_id = committee_id;
		this.state = state;
		this.cycle = cycle;
		this.count = count;
	}


	public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}


	public String getState_full() {
		return state_full;
	}


	public void setState_full(String state_full) {
		this.state_full = state_full;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getCommittee_id() {
		return committee_id;
	}


	public void setCommittee_id(String committee_id) {
		this.committee_id = committee_id;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Integer getCycle() {
		return cycle;
	}


	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "ZipResult [total=" + total + ", state_full=" + state_full + ", zip=" + zip + ", committee_id="
				+ committee_id + ", state=" + state + ", cycle=" + cycle + ", count=" + count + "]";
	}
	
	

	
	
}
