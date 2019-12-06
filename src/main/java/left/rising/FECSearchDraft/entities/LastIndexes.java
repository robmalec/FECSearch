package left.rising.FECSearchDraft.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LastIndexes {

	private String last_index;
	private String last_contribution_receipt_date;
	
	public LastIndexes() {
		super();
	}

	public LastIndexes(String last_index, String last_contribution_receipt_date) {
		super();
		this.last_index = last_index;
		this.last_contribution_receipt_date = last_contribution_receipt_date;
	}

	public String getLast_index() {
		return last_index;
	}

	public void setLast_index(String last_index) {
		this.last_index = last_index;
	}

	public String getLast_contribution_receipt_date() {
		return last_contribution_receipt_date;
	}

	public void setLast_contribution_receipt_date(String last_contribution_receipt_date) {
		this.last_contribution_receipt_date = last_contribution_receipt_date;
	}
	
}
