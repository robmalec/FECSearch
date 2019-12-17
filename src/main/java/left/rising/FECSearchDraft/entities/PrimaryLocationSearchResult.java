package left.rising.FECSearchDraft.entities;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "primary_location_search_results")
public class PrimaryLocationSearchResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String primaryCandidateName;

	@ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="cand_total_sum_donations", joinColumns=@JoinColumn(name="id"))
	private Map<String, Double> candidateTotalSumDonations;
	// Total amt of donations from city
	// String: candidate name, Double: total donations from that city

	@Column
	@ElementCollection
	private Map<String, Integer> candidateTotalNumDonations;
	// Total number of donations from city

	@Column
	@ElementCollection
	private Map<String, Double> candidateAvgDonations;

	@Column
	@ElementCollection
	private Map<String, Double> candidateLargestDonations;

	@Column
	@ElementCollection
	private Map<String, Double> candidatePercentFundsPerState;

	@Column
	@ElementCollection
	private Map<String, Double> candidatePercentTotalFunds;

	@Column
	@ElementCollection
	private Map<String, String> candidateScatterData;

	private BigDecimal totalSumDonations;
	private String topFundraiser;
	private String worstFundraiser;
	private String city;
	private String state;

	public PrimaryLocationSearchResult() {

	}

	public PrimaryLocationSearchResult(String primaryCandidateName, Map<String, Double> candidateTotalSumDonations,
			Map<String, Integer> candidateTotalNumDonations, Map<String, Double> candidateAvgDonations,
			Map<String, Double> candidateLargestDonations, Map<String, Double> candidatePercentFundsPerState,
			Map<String, Double> candidatePercentTotalFunds, Map<String, String> candidateScatterData,
			BigDecimal totalSumDonations, String topFundraiser, String worstFundraiser, String city, String state) {
		super();
		this.primaryCandidateName = primaryCandidateName;
		this.candidateTotalSumDonations = candidateTotalSumDonations;
		this.candidateTotalNumDonations = candidateTotalNumDonations;
		this.candidateAvgDonations = candidateAvgDonations;
		this.candidateLargestDonations = candidateLargestDonations;
		this.candidatePercentFundsPerState = candidatePercentFundsPerState;
		this.candidatePercentTotalFunds = candidatePercentTotalFunds;
		this.candidateScatterData = candidateScatterData;
		this.totalSumDonations = totalSumDonations;
		this.topFundraiser = topFundraiser;
		this.worstFundraiser = worstFundraiser;
		this.city = city;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrimaryCandidateName() {
		return primaryCandidateName;
	}

	public void setPrimaryCandidateName(String primaryCandidateName) {
		this.primaryCandidateName = primaryCandidateName;
	}

	public Map<String, Double> getCandidateTotalSumDonations() {
		return candidateTotalSumDonations;
	}

	public void setCandidateTotalSumDonations(Map<String, Double> candidateTotalSumDonations) {
		this.candidateTotalSumDonations = candidateTotalSumDonations;
	}

	public Map<String, Integer> getCandidateTotalNumDonations() {
		return candidateTotalNumDonations;
	}

	public void setCandidateTotalNumDonations(Map<String, Integer> candidateTotalNumDonations) {
		this.candidateTotalNumDonations = candidateTotalNumDonations;
	}

	public Map<String, Double> getCandidateAvgDonations() {
		return candidateAvgDonations;
	}

	public void setCandidateAvgDonations(Map<String, Double> candidateAvgDonations) {
		this.candidateAvgDonations = candidateAvgDonations;
	}

	public Map<String, Double> getCandidateLargestDonations() {
		return candidateLargestDonations;
	}

	public void setCandidateLargestDonations(Map<String, Double> candidateLargestDonations) {
		this.candidateLargestDonations = candidateLargestDonations;
	}

	public Map<String, Double> getCandidatePercentFundsPerState() {
		return candidatePercentFundsPerState;
	}

	public void setCandidatePercentFundsPerState(Map<String, Double> candidatePercentFundsPerState) {
		this.candidatePercentFundsPerState = candidatePercentFundsPerState;
	}

	public Map<String, Double> getCandidatePercentTotalFunds() {
		return candidatePercentTotalFunds;
	}

	public void setCandidatePercentTotalFunds(Map<String, Double> candidatePercentTotalFunds) {
		this.candidatePercentTotalFunds = candidatePercentTotalFunds;
	}

	public Map<String, String> getCandidateScatterData() {
		return candidateScatterData;
	}

	public void setCandidateScatterData(Map<String, String> candidateScatterData) {
		this.candidateScatterData = candidateScatterData;
	}

	public BigDecimal getTotalSumDonations() {
		return totalSumDonations;
	}

	public void setTotalSumDonations(BigDecimal totalSumDonations) {
		this.totalSumDonations = totalSumDonations;
	}

	public String getTopFundraiser() {
		return topFundraiser;
	}

	public void setTopFundraiser(String topFundraiser) {
		this.topFundraiser = topFundraiser;
	}

	public String getWorstFundraiser() {
		return worstFundraiser;
	}

	public void setWorstFundraiser(String worstFundraiser) {
		this.worstFundraiser = worstFundraiser;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}