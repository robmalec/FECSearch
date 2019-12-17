package left.rising.FECSearchDraft.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "primary_location_search_results")
public class PrimaryLocationSearchResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "plsr")
	private List<PrimaryCandidateLocationSearchInfo> primaryCandidateLocationSearchData;

	private BigDecimal totalSumDonations;
	private String topFundraiser;
	private String worstFundraiser;
	private String city;
	private String state;

	public PrimaryLocationSearchResult() {

	}

	public PrimaryLocationSearchResult(List<PrimaryCandidateLocationSearchInfo> primaryCandidateLocationSearchData,
			BigDecimal totalSumDonations, String topFundraiser, String worstFundraiser, String city, String state) {
		super();
		this.primaryCandidateLocationSearchData = primaryCandidateLocationSearchData;
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

	public List<PrimaryCandidateLocationSearchInfo> getPrimaryCandidateLocationSearchData() {
		return primaryCandidateLocationSearchData;
	}

	public void setPrimaryCandidateLocationSearchData(
			List<PrimaryCandidateLocationSearchInfo> primaryCandidateLocationSearchData) {
		this.primaryCandidateLocationSearchData = primaryCandidateLocationSearchData;
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