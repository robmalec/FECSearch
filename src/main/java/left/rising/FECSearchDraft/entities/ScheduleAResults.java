package left.rising.FECSearchDraft.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ScheduleAResults {
	
	private Pagination pagination;
	private List<Donation> results;
	
	public ScheduleAResults() {
		super();
	}

	public ScheduleAResults(Pagination pagination, List<Donation> results) {
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

	public List<Donation> getResults() {
		return results;
	}

	public void setResults(List<Donation> results) {
		this.results = results;
	}
	
	

}
