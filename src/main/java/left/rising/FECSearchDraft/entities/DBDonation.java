package left.rising.FECSearchDraft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
@Entity @Table(name="donations")
public class DBDonation {
	
	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private LocationSearchResult locationSearchResultAssigned;
	@JsonProperty("candidate_first_name")
	private String candidateFirstName;
	@JsonProperty("candidate_id")
	private String candidateId;
	@JsonProperty("candidate_last_nam")
	private String candidateLastName;
	@JsonProperty("candidate_middle_name")
	private String candidateMiddleName;
	@JsonProperty("candidate_name")
	private String candidateName;
	@JsonProperty("candidate_office")
	private String candidateOffice;
	@JsonProperty("candidate_office_district")
	private String candidateOfficeDistrict;
	@JsonProperty("candidate_office_full")
	private String candidateOfficeFull;
	@JsonProperty("candidate_office_state")
	private String candidateOfficeState;
	@JsonProperty("candidate_office_state_full")
	private String candidateOfficeStateFull;
	@JsonProperty("candidate_prefix")
	private String candidatePrefix;
	@JsonProperty("candidate_suffix")
	private String candidateSuffix;
	//private String committee;
	@JsonProperty("committee_id")
	private String committeeId;
	@JsonProperty("committee_name")
	private String committeeName;
	@JsonProperty("contribution_receipt_amount")
	private Double contributionReceiptAmount;
	@JsonProperty("contribution_receipt_date")
	private String contributionReceiptDate;
	/*
	@JsonProperty("contributor")
	private Donor contributor;
	*/
	@JsonProperty("contributor_aggregate_ytd")
	private Double contributorAggregateYtd;
	@JsonProperty("contributor_city")
	private String contributorCity;
	@JsonProperty("contributor_employer")
	private String contributorEmployer;
	@JsonProperty("contributor_first_name")
	private String contributorFirstName;
	@JsonProperty("contributor_id")
	private String contributorId;
	@JsonProperty("contributor_last_name")
	private String contributorLastName;
	@JsonProperty("contributor_middleName")
	private String contributorMiddleName;
	@JsonProperty("contributor_name")
	private String contributorName;
	@JsonProperty("contributor_occupation")
	private String contributorOccupation;
	@JsonProperty("contributor_prefix")
	private String contributorPrefix;
	@JsonProperty("contributor_state")
	private String contributorState;
	@JsonProperty("contributor_street_1")
	private String contributorStreet1;
	@JsonProperty("contributor_street_2")
	private String contributorStreet2;
	@JsonProperty("contributor_suffix")
	private String contributorSuffix;
	@JsonProperty("contributor_zip")
	private String contributorZip;
	@JsonProperty("donor_committee_name")
	private String donorCommitteeName;
	@JsonProperty("election_type")
	private String electionType;
	@JsonProperty("election_type_full")
	private String electionTypeFull;
	@JsonProperty("entity_type")
	private String entityType;
	@JsonProperty("entity_type_desc")
	private String entityTypeDesc;
	@JsonProperty("fec_election_type_desc")
	private String fecElectionTypeDesc;
	@JsonProperty("fec_election_year")
	private String fecElectionYear;
	@JsonProperty("is_individual")
	private String isIndividual;
	@JsonProperty("report_year")
	private Integer reportYear;
	@JsonProperty("sub_id")
	private String subId;
	@JsonProperty("transaction_id")
	private String transactionId;
	@JsonProperty("two_year_transaction_period")
	private Integer twoYearTransactionPeriod;
	
	public DBDonation () {
		super();
	}

	public DBDonation(String candidate_first_name, String candidate_id,
			String candidate_last_name, String candidate_middle_name, String candidate_name, String candidate_office,
			String candidate_office_district, String candidate_office_full, String candidate_office_state,
			String candidate_office_state_full, String candidate_prefix, String candidate_suffix,
			String committee_id, String committee_name, Double contribution_receipt_amount,
			String contribution_receipt_date, Double contributor_aggregate_ytd,
			String contributor_city, String contributor_employer, String contributor_first_name, String contributor_id,
			String contributor_last_name, String contributor_middle_name, String contributor_name,
			String contributor_occupation, String contributor_prefix, String contributor_state,
			String contributor_street_1, String contributor_street_2, String contributor_suffix, String contributor_zip,
			String donor_committee_name, String election_type, String election_type_full, String entity_type,
			String entity_type_desc, String fec_election_type_desc, String fec_election_year, Long file_number,
			String filing_form, String image_number, String increased_limit, String is_individual, String line_number,
			String line_number_label, Long link_id, String load_date, Integer report_year, String sub_id,
			String transaction_id, Integer two_year_transaction_period, String unused_contbr_id) {
		super();
		this.candidateFirstName = candidate_first_name;
		this.candidateId = candidate_id;
		this.candidateLastName = candidate_last_name;
		this.candidateMiddleName = candidate_middle_name;
		this.candidateName = candidate_name;
		this.candidateOffice = candidate_office;
		this.candidateOfficeDistrict = candidate_office_district;
		this.candidateOfficeFull = candidate_office_full;
		this.candidateOfficeState = candidate_office_state;
		this.candidateOfficeStateFull = candidate_office_state_full;
		this.candidatePrefix = candidate_prefix;
		this.candidateSuffix = candidate_suffix;
		this.committeeId = committee_id;
		this.committeeName = committee_name;
		this.contributionReceiptAmount = contribution_receipt_amount;
		this.contributionReceiptDate = contribution_receipt_date;
		this.contributorAggregateYtd = contributor_aggregate_ytd;
		this.contributorCity = contributor_city;
		this.contributorEmployer = contributor_employer;
		this.contributorFirstName = contributor_first_name;
		this.contributorId = contributor_id;
		this.contributorLastName = contributor_last_name;
		this.contributorMiddleName = contributor_middle_name;
		this.contributorName = contributor_name;
		this.contributorOccupation = contributor_occupation;
		this.contributorPrefix = contributor_prefix;
		this.contributorState = contributor_state;
		this.contributorStreet1 = contributor_street_1;
		this.contributorStreet2 = contributor_street_2;
		this.contributorSuffix = contributor_suffix;
		this.contributorZip = contributor_zip;
		this.donorCommitteeName = donor_committee_name;
		this.electionType = election_type;
		this.electionTypeFull = election_type_full;
		this.entityType = entity_type;
		this.entityTypeDesc = entity_type_desc;
		this.fecElectionTypeDesc = fec_election_type_desc;
		this.fecElectionYear = fec_election_year;
		this.isIndividual = is_individual;
		this.reportYear = report_year;
		this.subId = sub_id;
		this.transactionId = transaction_id;
		this.twoYearTransactionPeriod = two_year_transaction_period;
	}
	
	public DBDonation(String candidate_first_name, String candidate_id,
			String candidate_last_name, String candidate_middle_name, String candidate_name, String candidate_office,
			String candidate_office_district, String candidate_office_full, String candidate_office_state,
			String candidate_office_state_full, String candidate_prefix, String candidate_suffix,
			String committee_id, String committee_name, Double contribution_receipt_amount, Double contributor_aggregate_ytd,
			String contributor_city, String contributor_employer, String contributor_first_name, String contributor_id,
			String contributor_last_name, String contributor_middle_name, String contributor_name,
			String contributor_occupation, String contributor_prefix, String contributor_state,
			String contributor_street_1, String contributor_street_2, String contributor_suffix, String contributor_zip,
			String donor_committee_name, String election_type, String election_type_full, String entity_type,
			String entity_type_desc, String fec_election_type_desc, String fec_election_year, Long file_number,
			String filing_form, String image_number, String increased_limit, String is_individual, String line_number,
			String line_number_label, Long link_id, String load_date, Integer report_year, String sub_id,
			String transaction_id, Integer two_year_transaction_period, String unused_contbr_id) {
		super();
		this.candidateFirstName = candidate_first_name;
		this.candidateId = candidate_id;
		this.candidateLastName = candidate_last_name;
		this.candidateMiddleName = candidate_middle_name;
		this.candidateName = candidate_name;
		this.candidateOffice = candidate_office;
		this.candidateOfficeDistrict = candidate_office_district;
		this.candidateOfficeFull = candidate_office_full;
		this.candidateOfficeState = candidate_office_state;
		this.candidateOfficeStateFull = candidate_office_state_full;
		this.candidatePrefix = candidate_prefix;
		this.candidateSuffix = candidate_suffix;
		this.committeeId = committee_id;
		this.committeeName = committee_name;
		this.contributionReceiptAmount = contribution_receipt_amount;
		this.contributorAggregateYtd = contributor_aggregate_ytd;
		this.contributorCity = contributor_city;
		this.contributorEmployer = contributor_employer;
		this.contributorFirstName = contributor_first_name;
		this.contributorId = contributor_id;
		this.contributorLastName = contributor_last_name;
		this.contributorMiddleName = contributor_middle_name;
		this.contributorName = contributor_name;
		this.contributorOccupation = contributor_occupation;
		this.contributorPrefix = contributor_prefix;
		this.contributorState = contributor_state;
		this.contributorStreet1 = contributor_street_1;
		this.contributorStreet2 = contributor_street_2;
		this.contributorSuffix = contributor_suffix;
		this.contributorZip = contributor_zip;
		this.donorCommitteeName = donor_committee_name;
		this.electionType = election_type;
		this.electionTypeFull = election_type_full;
		this.entityType = entity_type;
		this.entityTypeDesc = entity_type_desc;
		this.fecElectionTypeDesc = fec_election_type_desc;
		this.fecElectionYear = fec_election_year;
		this.isIndividual = is_individual;
		this.reportYear = report_year;
		this.subId = sub_id;
		this.transactionId = transaction_id;
		this.twoYearTransactionPeriod = two_year_transaction_period;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCandidateFirstName() {
		return candidateFirstName;
	}

	public void setCandidateFirstName(String candidateFirstName) {
		this.candidateFirstName = candidateFirstName;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateLastName() {
		return candidateLastName;
	}

	public void setCandidateLastName(String candidateLastName) {
		this.candidateLastName = candidateLastName;
	}

	public String getCandidateMiddleName() {
		return candidateMiddleName;
	}

	public void setCandidateMiddleName(String candidateMiddleName) {
		this.candidateMiddleName = candidateMiddleName;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateOffice() {
		return candidateOffice;
	}

	public void setCandidateOffice(String candidateOffice) {
		this.candidateOffice = candidateOffice;
	}

	public String getCandidateOfficeDistrict() {
		return candidateOfficeDistrict;
	}

	public void setCandidateOfficeDistrict(String candidateOfficeDistrict) {
		this.candidateOfficeDistrict = candidateOfficeDistrict;
	}

	public String getCandidateOfficeFull() {
		return candidateOfficeFull;
	}

	public void setCandidateOfficeFull(String candidateOfficeFull) {
		this.candidateOfficeFull = candidateOfficeFull;
	}

	public String getCandidateOfficeState() {
		return candidateOfficeState;
	}

	public void setCandidateOfficeState(String candidateOfficeState) {
		this.candidateOfficeState = candidateOfficeState;
	}

	public String getCandidateOfficeStateFull() {
		return candidateOfficeStateFull;
	}

	public void setCandidateOfficeStateFull(String candidateOfficeStateFull) {
		this.candidateOfficeStateFull = candidateOfficeStateFull;
	}

	public String getCandidatePrefix() {
		return candidatePrefix;
	}

	public void setCandidatePrefix(String candidatePrefix) {
		this.candidatePrefix = candidatePrefix;
	}

	public String getCandidateSuffix() {
		return candidateSuffix;
	}

	public void setCandidateSuffix(String candidateSuffix) {
		this.candidateSuffix = candidateSuffix;
	}

	public String getCommitteeId() {
		return committeeId;
	}

	public void setCommitteeId(String committeeId) {
		this.committeeId = committeeId;
	}

	public String getCommitteeName() {
		return committeeName;
	}

	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}

	public Double getContributionReceiptAmount() {
		return contributionReceiptAmount;
	}

	public void setContributionReceiptAmount(Double contributionReceiptAmount) {
		this.contributionReceiptAmount = contributionReceiptAmount;
	}

	public String getContributionReceiptDate() {
		return contributionReceiptDate;
	}

	public void setContributionReceiptDate(String contributionReceiptDate) {
		this.contributionReceiptDate = contributionReceiptDate;
	}

	public Double getContributorAggregateYtd() {
		return contributorAggregateYtd;
	}

	public void setContributorAggregateYtd(Double contributorAggregateYtd) {
		this.contributorAggregateYtd = contributorAggregateYtd;
	}

	public String getContributorCity() {
		return contributorCity;
	}

	public void setContributorCity(String contributorCity) {
		this.contributorCity = contributorCity;
	}

	public String getContributorEmployer() {
		return contributorEmployer;
	}

	public void setContributorEmployer(String contributorEmployer) {
		this.contributorEmployer = contributorEmployer;
	}

	public String getContributorFirstName() {
		return contributorFirstName;
	}

	public void setContributorFirstName(String contributorFirstName) {
		this.contributorFirstName = contributorFirstName;
	}

	public String getContributorId() {
		return contributorId;
	}

	public void setContributorId(String contributorId) {
		this.contributorId = contributorId;
	}

	public String getContributorLastName() {
		return contributorLastName;
	}

	public void setContributorLastName(String contributorLastName) {
		this.contributorLastName = contributorLastName;
	}

	public String getContributorMiddleName() {
		return contributorMiddleName;
	}

	public void setContributorMiddleName(String contributorMiddleName) {
		this.contributorMiddleName = contributorMiddleName;
	}

	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	public String getContributorOccupation() {
		return contributorOccupation;
	}

	public void setContributorOccupation(String contributorOccupation) {
		this.contributorOccupation = contributorOccupation;
	}

	public String getContributorPrefix() {
		return contributorPrefix;
	}

	public void setContributorPrefix(String contributorPrefix) {
		this.contributorPrefix = contributorPrefix;
	}

	public String getContributorState() {
		return contributorState;
	}

	public void setContributorState(String contributorState) {
		this.contributorState = contributorState;
	}

	public String getContributorStreet1() {
		return contributorStreet1;
	}

	public void setContributorStreet1(String contributorStreet1) {
		this.contributorStreet1 = contributorStreet1;
	}

	public String getContributorStreet2() {
		return contributorStreet2;
	}

	public void setContributorStreet2(String contributorStreet2) {
		this.contributorStreet2 = contributorStreet2;
	}

	public String getContributorSuffix() {
		return contributorSuffix;
	}

	public void setContributorSuffix(String contributorSuffix) {
		this.contributorSuffix = contributorSuffix;
	}

	public String getContributorZip() {
		return contributorZip;
	}

	public void setContributorZip(String contributorZip) {
		this.contributorZip = contributorZip;
	}

	public String getDonorCommitteeName() {
		return donorCommitteeName;
	}

	public void setDonorCommitteeName(String donorCommitteeName) {
		this.donorCommitteeName = donorCommitteeName;
	}

	public String getElectionType() {
		return electionType;
	}

	public void setElectionType(String electionType) {
		this.electionType = electionType;
	}

	public String getElectionTypeFull() {
		return electionTypeFull;
	}

	public void setElectionTypeFull(String electionTypeFull) {
		this.electionTypeFull = electionTypeFull;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityTypeDesc() {
		return entityTypeDesc;
	}

	public void setEntityTypeDesc(String entityTypeDesc) {
		this.entityTypeDesc = entityTypeDesc;
	}

	public String getFecElectionTypeDesc() {
		return fecElectionTypeDesc;
	}

	public void setFecElectionTypeDesc(String fecElectionTypeDesc) {
		this.fecElectionTypeDesc = fecElectionTypeDesc;
	}

	public String getFecElectionYear() {
		return fecElectionYear;
	}

	public void setFecElectionYear(String fecElectionYear) {
		this.fecElectionYear = fecElectionYear;
	}

	public String getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(String isIndividual) {
		this.isIndividual = isIndividual;
	}

	public Integer getReportYear() {
		return reportYear;
	}

	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getTwoYearTransactionPeriod() {
		return twoYearTransactionPeriod;
	}

	public void setTwoYearTransactionPeriod(Integer twoYearTransactionPeriod) {
		this.twoYearTransactionPeriod = twoYearTransactionPeriod;
	}

	
	

}
