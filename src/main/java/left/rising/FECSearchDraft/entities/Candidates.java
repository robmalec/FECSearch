package left.rising.FECSearchDraft.entities;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidates {

	
	ArrayList<CandidateResults> results;
}
