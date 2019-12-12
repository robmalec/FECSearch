package left.rising.FECSearchDraft.entities;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="search_results")
public class LocationSearchResult {

	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String winnerName;
	private String loserName;

	@OneToMany(mappedBy = "locationSearchResultAssigned", orphanRemoval = true)
	private List<winnerCommitteeIds> winnerCommitteeIds;
	@OneToMany(mappedBy = "locationSearchResultAssigned", orphanRemoval = true)
	private List<loserCommitteeIds> loserCommitteeIds;

	@OneToMany(mappedBy = "locationSearchResultAssigned", orphanRemoval = true)
	private List<DBDonation> winnerDonations;
	@OneToMany(mappedBy = "locationSearchResultAssigned", orphanRemoval = true)
	private List<DBDonation> loserDonations;
	
	private Integer totalWinners;
	private Integer totalLosers;
	private Double winnerTotalDonations;
	private Double loserTotalDonations;
	private Double largestWinningDonation;
	private Double largestLosingDonation;
	private Double avgWinningDonation;
	private Double avgLosingDonation;
	private String city;
	private String state;
	private Integer electionYear;
	@Column(length=6000)
	private String winnerDonationScatterData;
	@Column(length=6000)
	private String loserDonationScatterData;
	private Integer numLoserDonations;
	public Integer getNumLoserDonations() {
		return numLoserDonations;
	}

	public void setNumLoserDonations(Integer numLoserDonations) {
		this.numLoserDonations = numLoserDonations;
	}

	private Integer numWinnerDonations;
	
	public Integer getNumWinnerDonations() {
		return numWinnerDonations;
	}

	public void setNumWinnerDonations(Integer numWinnerDonations) {
		this.numWinnerDonations = numWinnerDonations;
	}

	public String getLoserDonationScatterData() {
		return loserDonationScatterData;
	}

	public void setLoserDonationScatterData(String loserDonationScatterData) {
		this.loserDonationScatterData = loserDonationScatterData;
	}

	public String getWinnerDonationScatterData() {
		return winnerDonationScatterData;
	}

	public void setWinnerDonationScatterData(String winnerDonationScatterData) {
		this.winnerDonationScatterData = winnerDonationScatterData;
	}

	public LocationSearchResult() {
		super();
	}
	
	public LocationSearchResult(String winnerName, String loserName, List<winnerCommitteeIds>winnerCommitteeIds,
			List<loserCommitteeIds>loserCommitteeIds, List<DBDonation> winnerDonations, List<DBDonation> loserDonations,
			Integer totalWinners, Integer totalLosers, Double winnerTotalDonations, Double loserTotalDonations,
			Double largestWinningDonation, Double largestLosingDonation, Double avgWinningDonation,
			Double avgLosingDonation, String city, String state, Integer electionYear, String winnerDonationScatterData, String loserDonationScatterData, Integer numLoserDonations, Integer numWinnerDonations) {
		super();
		this.winnerName = winnerName;
		this.loserName = loserName;
		this.winnerCommitteeIds = winnerCommitteeIds;
		this.loserCommitteeIds = loserCommitteeIds;
		this.winnerDonations = winnerDonations;
		this.loserDonations = loserDonations;
		this.totalWinners = totalWinners;
		this.totalLosers = totalLosers;
		this.winnerTotalDonations = winnerTotalDonations;
		this.loserTotalDonations = loserTotalDonations;
		this.largestWinningDonation = largestWinningDonation;
		this.largestLosingDonation = largestLosingDonation;
		this.avgWinningDonation = avgWinningDonation;
		this.avgLosingDonation = avgLosingDonation;
		this.city = city;
		this.state = state;
		this.electionYear = electionYear;
		this.winnerDonationScatterData = winnerDonationScatterData;
		this.loserDonationScatterData = loserDonationScatterData;
		this.numLoserDonations = numLoserDonations;
		this.numWinnerDonations = numWinnerDonations;
	}

	public LocationSearchResult(Integer id, String winnerName, String loserName,
			List<left.rising.FECSearchDraft.entities.winnerCommitteeIds> winnerCommitteeIds,
			List<left.rising.FECSearchDraft.entities.loserCommitteeIds> loserCommitteeIds,
			List<DBDonation> winnerDonations, List<DBDonation> loserDonations, Integer totalWinners,
			Integer totalLosers, Double winnerTotalDonations, Double loserTotalDonations, Double largestWinningDonation,
			Double largestLosingDonation, Double avgWinningDonation, Double avgLosingDonation, String city,
			String state, Integer electionYear, String winnerDonationScatterData, String loserDonationScatterData) {
		super();
		this.id = id;
		this.winnerName = winnerName;
		this.loserName = loserName;
		this.winnerCommitteeIds = winnerCommitteeIds;
		this.loserCommitteeIds = loserCommitteeIds;
		this.winnerDonations = winnerDonations;
		this.loserDonations = loserDonations;
		this.totalWinners = totalWinners;
		this.totalLosers = totalLosers;
		this.winnerTotalDonations = winnerTotalDonations;
		this.loserTotalDonations = loserTotalDonations;
		this.largestWinningDonation = largestWinningDonation;
		this.largestLosingDonation = largestLosingDonation;
		this.avgWinningDonation = avgWinningDonation;
		this.avgLosingDonation = avgLosingDonation;
		this.city = city;
		this.state = state;
		this.electionYear = electionYear;
		this.winnerDonationScatterData = winnerDonationScatterData;
		this.loserDonationScatterData = loserDonationScatterData;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public String getLoserName() {
		return loserName;
	}

	public void setLoserName(String loserName) {
		this.loserName = loserName;
	}

	public List<winnerCommitteeIds> getWinnerCommitteeIds() {
		return winnerCommitteeIds;
	}

	public void setWinnerCommitteeIds(List<winnerCommitteeIds> winnerCommitteeIds) {
		this.winnerCommitteeIds = winnerCommitteeIds;
	}

	public List<loserCommitteeIds> getLoserCommitteeIds() {
		return loserCommitteeIds;
	}

	public void setLoserCommitteeIds(List<loserCommitteeIds> loserCommitteeIds) {
		this.loserCommitteeIds = loserCommitteeIds;
	}

	public List<DBDonation> getWinnerDonations() {
		return winnerDonations;
	}

	public void setWinnerDonations(List<DBDonation> winnerDonations) {
		this.winnerDonations = winnerDonations;
	}

	public List<DBDonation> getLoserDonations() {
		return loserDonations;
	}

	public void setLoserDonations(List<DBDonation> loserDonations) {
		this.loserDonations = loserDonations;
	}

	public Integer getTotalWinners() {
		return totalWinners;
	}

	public void setTotalWinners(Integer totalWinners) {
		this.totalWinners = totalWinners;
	}

	public Integer getTotalLosers() {
		return totalLosers;
	}

	public void setTotalLosers(Integer totalLosers) {
		this.totalLosers = totalLosers;
	}

	public Double getWinnerTotalDonations() {
		return winnerTotalDonations;
	}

	public void setWinnerTotalDonations(Double winnerTotalDonations) {
		this.winnerTotalDonations = winnerTotalDonations;
	}

	public Double getLoserTotalDonations() {
		return loserTotalDonations;
	}

	public void setLoserTotalDonations(Double loserTotalDonations) {
		this.loserTotalDonations = loserTotalDonations;
	}

	public Double getLargestWinningDonation() {
		return largestWinningDonation;
	}

	public void setLargestWinningDonation(Double largestWinningDonation) {
		this.largestWinningDonation = largestWinningDonation;
	}

	public Double getLargestLosingDonation() {
		return largestLosingDonation;
	}

	public void setLargestLosingDonation(Double largestLosingDonation) {
		this.largestLosingDonation = largestLosingDonation;
	}

	public Double getAvgWinningDonation() {
		return avgWinningDonation;
	}

	public void setAvgWinningDonation(Double avgWinningDonation) {
		this.avgWinningDonation = avgWinningDonation;
	}

	public Double getAvgLosingDonation() {
		return avgLosingDonation;
	}

	public void setAvgLosingDonation(Double avgLosingDonation) {
		this.avgLosingDonation = avgLosingDonation;
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

	public Integer getElectionYear() {
		return electionYear;
	}

	public void setElectionYear(Integer electionYear) {
		this.electionYear = electionYear;
	}

	
	
}
