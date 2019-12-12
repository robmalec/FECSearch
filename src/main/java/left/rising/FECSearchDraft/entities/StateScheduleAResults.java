package left.rising.FECSearchDraft.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class StateScheduleAResults {
	
	private double api_version;
	private Pagination pagination;
	private ArrayList<DBDonationResult> results;
	
	public StateScheduleAResults() {
		super();
	}

	public StateScheduleAResults(double api_version, Pagination pagination, ArrayList<DBDonationResult> results) {
		super();
		this.api_version = api_version;
		this.pagination = pagination;
		this.results = results;
	}

	public double getApi_version() {
		return api_version;
	}

	public void setApi_version(double api_version) {
		this.api_version = api_version;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public ArrayList<DBDonationResult> getResults() {
		return results;
	}

	public void setResults(ArrayList<DBDonationResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "StateScheduleAResults [api_version=" + api_version + ", pagination=" + pagination + ", results="
				+ results + "]";
	}
}
