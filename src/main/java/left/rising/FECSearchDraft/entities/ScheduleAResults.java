package left.rising.FECSearchDraft.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ScheduleAResults {
	
	private Pagination pagination;
	private List<DBDonation> results;
	
	public ScheduleAResults() {
		super();
	}

	public ScheduleAResults(Pagination pagination, List<DBDonation> results) {
		super();
		this.pagination = pagination;
		this.results = results;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<DBDonation> getResults() {
		return results;
	}

	public void setResults(List<DBDonation> results) {
		this.results = results;
	}
	
	

}
