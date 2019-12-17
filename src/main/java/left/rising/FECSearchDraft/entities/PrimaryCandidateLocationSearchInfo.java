package left.rising.FECSearchDraft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
public class PrimaryCandidateLocationSearchInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotNull
	@Size(max = 100)
	private String candidateName;

	@NotNull
	@Size(max = 100)
	private String city;

	@NotNull
	@Size(max = 100)
	private String state;

	@NotNull
	private double totalSumDonations;

	@NotNull
	private double totalNumDonations;

	@NotNull
	private double avgDonation;

	@NotNull
	private double largestDonation;

	@NotNull
	private double percentDonationsForState;

	@ManyToOne
	private PrimaryLocationSearchResult plsr;

	public PrimaryCandidateLocationSearchInfo() {
		super();
	}

	public PrimaryCandidateLocationSearchInfo(@Size(max = 100) String candidateName, @Size(max = 100) String city,
			@Size(max = 100) String state, double totalSumDonations, double totalNumDonations, double avgDonation,
			double largestDonation, double percentDonationsForState) {
		super();
		this.candidateName = candidateName;
		this.city = city;
		this.state = state;
		this.totalSumDonations = totalSumDonations;
		this.totalNumDonations = totalNumDonations;
		this.avgDonation = avgDonation;
		this.largestDonation = largestDonation;
		this.percentDonationsForState = percentDonationsForState;
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

	public PrimaryLocationSearchResult getPlsr() {
		return plsr;
	}

	public void setPlsr(PrimaryLocationSearchResult plsr) {
		this.plsr = plsr;
	}

}
