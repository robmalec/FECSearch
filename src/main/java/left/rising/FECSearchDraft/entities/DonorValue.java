package left.rising.FECSearchDraft.entities;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DonorValue {
	private String api_version;
	private ArrayList<Donor> results;

	public DonorValue() {
		super();
	}

	public String getApi_version() {
		return api_version;
	}

	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}

	public ArrayList<Donor> getResults() {
		return results;
	}

	public void setResults(ArrayList<Donor> results) {
		this.results = results;
	}

}
