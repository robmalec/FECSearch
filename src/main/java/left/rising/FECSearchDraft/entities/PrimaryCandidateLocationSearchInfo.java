package left.rising.FECSearchDraft.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="primary_candidate_location_search_info")
public class PrimaryCandidateLocationSearchInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	private String candidateName;

	private String city;

	private String state;

	private double totalSumDonations;

	private double totalNumDonations;

	private double avgDonation;

	private double largestDonation;

	private double percentDonationsForState;
	
	private double percentTotalDonations;
	
	@Column(length=6000)
	private String donationScatterData;

	@ManyToOne
	private PrimaryLocationSearchResult plsr;

	public PrimaryCandidateLocationSearchInfo() {
		super();
	}

	public PrimaryCandidateLocationSearchInfo(String candidateName, String city,
			String state, double totalSumDonations, double totalNumDonations, double avgDonation,
			double largestDonation, double percentDonationsForState, double percentTotalDonations, String donationScatterData) {
		super();
		this.candidateName = candidateName;
		this.city = city;
		this.state = state;
		this.totalSumDonations = totalSumDonations;
		this.totalNumDonations = totalNumDonations;
		this.avgDonation = avgDonation;
		this.largestDonation = largestDonation;
		this.percentDonationsForState = percentDonationsForState;
		this.percentTotalDonations = percentTotalDonations;
		this.donationScatterData = donationScatterData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
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

	public double getTotalSumDonations() {
		return totalSumDonations;
	}

	public void setTotalSumDonations(double totalSumDonations) {
		this.totalSumDonations = totalSumDonations;
	}

	public double getTotalNumDonations() {
		return totalNumDonations;
	}

	public void setTotalNumDonations(double totalNumDonations) {
		this.totalNumDonations = totalNumDonations;
	}

	public double getAvgDonation() {
		return avgDonation;
	}

	public void setAvgDonation(double avgDonation) {
		this.avgDonation = avgDonation;
	}

	public double getLargestDonation() {
		return largestDonation;
	}

	public void setLargestDonation(double largestDonation) {
		this.largestDonation = largestDonation;
	}

	public double getPercentDonationsForState() {
		return percentDonationsForState;
	}

	public void setPercentDonationsForState(double percentDonationsForState) {
		this.percentDonationsForState = percentDonationsForState;
	}

	public double getPercentTotalDonations() {
		return percentTotalDonations;
	}

	public void setPercentTotalDonations(double percentTotalDonations) {
		this.percentTotalDonations = percentTotalDonations;
	}
	
	public String getDonationScatterData() {
		return donationScatterData;
	}

	public void setDonationScatterData(String donationScatterData) {
		this.donationScatterData = donationScatterData;
	}

	public PrimaryLocationSearchResult getPlsr() {
		return plsr;
	}

	public void setPlsr(PrimaryLocationSearchResult plsr) {
		this.plsr = plsr;
	}

}
