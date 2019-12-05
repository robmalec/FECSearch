package left.rising.FECSearchDraft.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Donor {
	
	private String amendment_indicator;
	private String amendment_indicator_desc;
	private String back_reference_schedule_name;
	private String back_reference_transaction_id;
	private String candidate_first_name;
	private String candidate_id;
	private String candidate_last_name;
	private String candidate_middle_name;
	private String candidate_name;
	private String candidate_office;
	private String candidate_office_district;
	private String candidate_office_full;
	private String candidate_office_state;
	private String candidate_office_state_full;
	private String candidate_prefix;
	private String candidate_suffix;
	//private Object[] committee;
	private String committee_id;
	private String committee_name;
	private String conduit_committee_city;
	private String conduit_committee_id;
	private String conduit_committee_name;
	private String conduit_committee_state;
	private String conduit_committee_street1;
	private String conduit_committee_street2;
	private String conduit_committee_zip;
	private int contribution_receipt_amount;
	private String contribution_receipt_date;
	private String contributor;
	private double contributor_aggregate_ytd;
	private String contributor_city;
	private String contributor_employer;
	private String contributor_first_name;
	private String contributor_id;
	private String contributor_last_name;
	private String contributor_middle_name;
	private String contributor_name;
	private String contributor_occupation;
	private String contributor_prefix;
	private String contributor_state;
	private String contributor_street_1;
	private String contributor_street_2;
	private String contributor_suffix;
	private String contributor_zip;
	private String donor_committee_name;
	private String election_type;
	private String election_type_full;
	private String entity_type;
	private String entity_type_desc;
	private String fec_election_type_desc;
	private String fec_election_year;
	private String file_number;
	private String filing_form;
	private String image_number;
	private String increased_limit;
	private String is_individual;
	private String line_number;
	private String line_number_labell;
	private Long link_id;
	private String load_date;
	private String memoed_subtotal;
	private String memo_code;
	private String memo_code_full;
	private String memo_text;
	private String national_committee_nonfederal_account;
	private String original_sub_id;
	private String pdf_url;
	private String receipt_type;
	private String receipt_type_desc;
	private String receipt_type_full;
	private String recipient_committee_designation;
	private String recipient_committee_org_type;
	private String recipient_committee_type;
	private String report_type;
	private int report_year;
	private String schedule_type;
	private String schedule_type_full;
	private String sub_id;
	private String transaction_id;
	private int two_year_transaction_period;
	private String unused_contbr_id;
	
	public Donor() {
		
	}

	public String getAmendment_indicator() {
		return amendment_indicator;
	}

	public void setAmendment_indicator(String amendment_indicator) {
		this.amendment_indicator = amendment_indicator;
	}

	public String getAmendment_indicator_desc() {
		return amendment_indicator_desc;
	}

	public void setAmendment_indicator_desc(String amendment_indicator_desc) {
		this.amendment_indicator_desc = amendment_indicator_desc;
	}

	public String getBack_reference_schedule_name() {
		return back_reference_schedule_name;
	}

	public void setBack_reference_schedule_name(String back_reference_schedule_name) {
		this.back_reference_schedule_name = back_reference_schedule_name;
	}

	public String getBack_reference_transaction_id() {
		return back_reference_transaction_id;
	}

	public void setBack_reference_transaction_id(String back_reference_transaction_id) {
		this.back_reference_transaction_id = back_reference_transaction_id;
	}

	public String getCandidate_first_name() {
		return candidate_first_name;
	}

	public void setCandidate_first_name(String candidate_first_name) {
		this.candidate_first_name = candidate_first_name;
	}

	public String getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(String candidate_id) {
		this.candidate_id = candidate_id;
	}

	public String getCandidate_last_name() {
		return candidate_last_name;
	}

	public void setCandidate_last_name(String candidate_last_name) {
		this.candidate_last_name = candidate_last_name;
	}

	public String getCandidate_middle_name() {
		return candidate_middle_name;
	}

	public void setCandidate_middle_name(String candidate_middle_name) {
		this.candidate_middle_name = candidate_middle_name;
	}

	public String getCandidate_name() {
		return candidate_name;
	}

	public void setCandidate_name(String candidate_name) {
		this.candidate_name = candidate_name;
	}

	public String getCandidate_office() {
		return candidate_office;
	}

	public void setCandidate_office(String candidate_office) {
		this.candidate_office = candidate_office;
	}

	public String getCandidate_office_district() {
		return candidate_office_district;
	}

	public void setCandidate_office_district(String candidate_office_district) {
		this.candidate_office_district = candidate_office_district;
	}

	public String getCandidate_office_full() {
		return candidate_office_full;
	}

	public void setCandidate_office_full(String candidate_office_full) {
		this.candidate_office_full = candidate_office_full;
	}

	public String getCandidate_office_state() {
		return candidate_office_state;
	}

	public void setCandidate_office_state(String candidate_office_state) {
		this.candidate_office_state = candidate_office_state;
	}

	public String getCandidate_office_state_full() {
		return candidate_office_state_full;
	}

	public void setCandidate_office_state_full(String candidate_office_state_full) {
		this.candidate_office_state_full = candidate_office_state_full;
	}

	public String getCandidate_prefix() {
		return candidate_prefix;
	}

	public void setCandidate_prefix(String candidate_prefix) {
		this.candidate_prefix = candidate_prefix;
	}

	public String getCandidate_suffix() {
		return candidate_suffix;
	}

	public void setCandidate_suffix(String candidate_suffix) {
		this.candidate_suffix = candidate_suffix;
	}

	/*
	public Object[] getCommittee() {
		return committee;
	}

	public void setCommittee(Object[] committee) {
		this.committee = committee;
	}
	*/

	public String getCommittee_id() {
		return committee_id;
	}

	public void setCommittee_id(String committee_id) {
		this.committee_id = committee_id;
	}

	public String getCommittee_name() {
		return committee_name;
	}

	public void setCommittee_name(String committee_name) {
		this.committee_name = committee_name;
	}

	public String getConduit_committee_city() {
		return conduit_committee_city;
	}

	public void setConduit_committee_city(String conduit_committee_city) {
		this.conduit_committee_city = conduit_committee_city;
	}

	public String getConduit_committee_id() {
		return conduit_committee_id;
	}

	public void setConduit_committee_id(String conduit_committee_id) {
		this.conduit_committee_id = conduit_committee_id;
	}

	public String getConduit_committee_name() {
		return conduit_committee_name;
	}

	public void setConduit_committee_name(String conduit_committee_name) {
		this.conduit_committee_name = conduit_committee_name;
	}

	public String getConduit_committee_state() {
		return conduit_committee_state;
	}

	public void setConduit_committee_state(String conduit_committee_state) {
		this.conduit_committee_state = conduit_committee_state;
	}

	public String getConduit_committee_street1() {
		return conduit_committee_street1;
	}

	public void setConduit_committee_street1(String conduit_committee_street1) {
		this.conduit_committee_street1 = conduit_committee_street1;
	}

	public String getConduit_committee_street2() {
		return conduit_committee_street2;
	}

	public void setConduit_committee_street2(String conduit_committee_street2) {
		this.conduit_committee_street2 = conduit_committee_street2;
	}

	public String getConduit_committee_zip() {
		return conduit_committee_zip;
	}

	public void setConduit_committee_zip(String conduit_committee_zip) {
		this.conduit_committee_zip = conduit_committee_zip;
	}

	public int getContribution_receipt_amount() {
		return contribution_receipt_amount;
	}

	public void setContribution_receipt_amount(int contribution_receipt_amount) {
		this.contribution_receipt_amount = contribution_receipt_amount;
	}

	public String getContribution_receipt_date() {
		return contribution_receipt_date;
	}

	public void setContribution_receipt_date(String contribution_receipt_date) {
		this.contribution_receipt_date = contribution_receipt_date;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public double getContributor_aggregate_ytd() {
		return contributor_aggregate_ytd;
	}

	public void setContributor_aggregate_ytd(double contributor_aggregate_ytd) {
		this.contributor_aggregate_ytd = contributor_aggregate_ytd;
	}

	public String getContributor_city() {
		return contributor_city;
	}

	public void setContributor_city(String contributor_city) {
		this.contributor_city = contributor_city;
	}

	public String getContributor_employer() {
		return contributor_employer;
	}

	public void setContributor_employer(String contributor_employer) {
		this.contributor_employer = contributor_employer;
	}

	public String getContributor_first_name() {
		return contributor_first_name;
	}

	public void setContributor_first_name(String contributor_first_name) {
		this.contributor_first_name = contributor_first_name;
	}

	public String getContributor_id() {
		return contributor_id;
	}

	public void setContributor_id(String contributor_id) {
		this.contributor_id = contributor_id;
	}

	public String getContributor_last_name() {
		return contributor_last_name;
	}

	public void setContributor_last_name(String contributor_last_name) {
		this.contributor_last_name = contributor_last_name;
	}

	public String getContributor_middle_name() {
		return contributor_middle_name;
	}

	public void setContributor_middle_name(String contributor_middle_name) {
		this.contributor_middle_name = contributor_middle_name;
	}

	public String getContributor_name() {
		return contributor_name;
	}

	public void setContributor_name(String contributor_name) {
		this.contributor_name = contributor_name;
	}

	public String getContributor_occupation() {
		return contributor_occupation;
	}

	public void setContributor_occupation(String contributor_occupation) {
		this.contributor_occupation = contributor_occupation;
	}

	public String getContributor_prefix() {
		return contributor_prefix;
	}

	public void setContributor_prefix(String contributor_prefix) {
		this.contributor_prefix = contributor_prefix;
	}

	public String getContributor_state() {
		return contributor_state;
	}

	public void setContributor_state(String contributor_state) {
		this.contributor_state = contributor_state;
	}

	public String getContributor_street_1() {
		return contributor_street_1;
	}

	public void setContributor_street_1(String contributor_street_1) {
		this.contributor_street_1 = contributor_street_1;
	}

	public String getContributor_street_2() {
		return contributor_street_2;
	}

	public void setContributor_street_2(String contributor_street_2) {
		this.contributor_street_2 = contributor_street_2;
	}

	public String getContributor_suffix() {
		return contributor_suffix;
	}

	public void setContributor_suffix(String contributor_suffix) {
		this.contributor_suffix = contributor_suffix;
	}

	public String getContributor_zip() {
		return contributor_zip;
	}

	public void setContributor_zip(String contributor_zip) {
		this.contributor_zip = contributor_zip;
	}

	public String getDonor_committee_name() {
		return donor_committee_name;
	}

	public void setDonor_committee_name(String donor_committee_name) {
		this.donor_committee_name = donor_committee_name;
	}

	public String getElection_type() {
		return election_type;
	}

	public void setElection_type(String election_type) {
		this.election_type = election_type;
	}

	public String getElection_type_full() {
		return election_type_full;
	}

	public void setElection_type_full(String election_type_full) {
		this.election_type_full = election_type_full;
	}

	public String getEntity_type() {
		return entity_type;
	}

	public void setEntity_type(String entity_type) {
		this.entity_type = entity_type;
	}

	public String getEntity_type_desc() {
		return entity_type_desc;
	}

	public void setEntity_type_desc(String entity_type_desc) {
		this.entity_type_desc = entity_type_desc;
	}

	public String getFec_election_type_desc() {
		return fec_election_type_desc;
	}

	public void setFec_election_type_desc(String fec_election_type_desc) {
		this.fec_election_type_desc = fec_election_type_desc;
	}

	public String getFec_election_year() {
		return fec_election_year;
	}

	public void setFec_election_year(String fec_election_year) {
		this.fec_election_year = fec_election_year;
	}

	public String getFile_number() {
		return file_number;
	}

	public void setFile_number(String file_number) {
		this.file_number = file_number;
	}

	public String getFiling_form() {
		return filing_form;
	}

	public void setFiling_form(String filing_form) {
		this.filing_form = filing_form;
	}

	public String getImage_number() {
		return image_number;
	}

	public void setImage_number(String image_number) {
		this.image_number = image_number;
	}

	public String getIncreased_limit() {
		return increased_limit;
	}

	public void setIncreased_limit(String increased_limit) {
		this.increased_limit = increased_limit;
	}

	public String getIs_individual() {
		return is_individual;
	}

	public void setIs_individual(String is_individual) {
		this.is_individual = is_individual;
	}

	public String getLine_number() {
		return line_number;
	}

	public void setLine_number(String line_number) {
		this.line_number = line_number;
	}

	public String getLine_number_labell() {
		return line_number_labell;
	}

	public void setLine_number_labell(String line_number_labell) {
		this.line_number_labell = line_number_labell;
	}

	public Long getLink_id() {
		return link_id;
	}

	public void setLink_id(Long link_id) {
		this.link_id = link_id;
	}

	public String getLoad_date() {
		return load_date;
	}

	public void setLoad_date(String load_date) {
		this.load_date = load_date;
	}

	public String getMemoed_subtotal() {
		return memoed_subtotal;
	}

	public void setMemoed_subtotal(String memoed_subtotal) {
		this.memoed_subtotal = memoed_subtotal;
	}

	public String getMemo_code() {
		return memo_code;
	}

	public void setMemo_code(String memo_code) {
		this.memo_code = memo_code;
	}

	public String getMemo_code_full() {
		return memo_code_full;
	}

	public void setMemo_code_full(String memo_code_full) {
		this.memo_code_full = memo_code_full;
	}

	public String getMemo_text() {
		return memo_text;
	}

	public void setMemo_text(String memo_text) {
		this.memo_text = memo_text;
	}

	public String getNational_committee_nonfederal_account() {
		return national_committee_nonfederal_account;
	}

	public void setNational_committee_nonfederal_account(String national_committee_nonfederal_account) {
		this.national_committee_nonfederal_account = national_committee_nonfederal_account;
	}

	public String getOriginal_sub_id() {
		return original_sub_id;
	}

	public void setOriginal_sub_id(String original_sub_id) {
		this.original_sub_id = original_sub_id;
	}

	public String getPdf_url() {
		return pdf_url;
	}

	public void setPdf_url(String pdf_url) {
		this.pdf_url = pdf_url;
	}

	public String getReceipt_type() {
		return receipt_type;
	}

	public void setReceipt_type(String receipt_type) {
		this.receipt_type = receipt_type;
	}

	public String getReceipt_type_desc() {
		return receipt_type_desc;
	}

	public void setReceipt_type_desc(String receipt_type_desc) {
		this.receipt_type_desc = receipt_type_desc;
	}

	public String getReceipt_type_full() {
		return receipt_type_full;
	}

	public void setReceipt_type_full(String receipt_type_full) {
		this.receipt_type_full = receipt_type_full;
	}

	public String getRecipient_committee_designation() {
		return recipient_committee_designation;
	}

	public void setRecipient_committee_designation(String recipient_committee_designation) {
		this.recipient_committee_designation = recipient_committee_designation;
	}

	public String getRecipient_committee_org_type() {
		return recipient_committee_org_type;
	}

	public void setRecipient_committee_org_type(String recipient_committee_org_type) {
		this.recipient_committee_org_type = recipient_committee_org_type;
	}

	public String getRecipient_committee_type() {
		return recipient_committee_type;
	}

	public void setRecipient_committee_type(String recipient_committee_type) {
		this.recipient_committee_type = recipient_committee_type;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public int getReport_year() {
		return report_year;
	}

	public void setReport_year(int report_year) {
		this.report_year = report_year;
	}

	public String getSchedule_type() {
		return schedule_type;
	}

	public void setSchedule_type(String schedule_type) {
		this.schedule_type = schedule_type;
	}

	public String getSchedule_type_full() {
		return schedule_type_full;
	}

	public void setSchedule_type_full(String schedule_type_full) {
		this.schedule_type_full = schedule_type_full;
	}

	public String getSub_id() {
		return sub_id;
	}

	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getTwo_year_transaction_period() {
		return two_year_transaction_period;
	}

	public void setTwo_year_transaction_period(int two_year_transaction_period) {
		this.two_year_transaction_period = two_year_transaction_period;
	}

	public String getUnused_contbr_id() {
		return unused_contbr_id;
	}

	public void setUnused_contbr_id(String unused_contbr_id) {
		this.unused_contbr_id = unused_contbr_id;
	}

	

	
	
	
}
