package left.rising.FECSearchDraft.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pagination {

	private Integer count;
	private LastIndexes last_indexes;
	private Integer pages;
	private Integer per_page;
	
	public Pagination() {
		super();
	}

	public Pagination(Integer count, LastIndexes last_indexes, Integer pages, Integer per_page) {
		super();
		this.count = count;
		this.last_indexes = last_indexes;
		this.pages = pages;
		this.per_page = per_page;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public LastIndexes getLast_indexes() {
		return last_indexes;
	}

	public void setLast_indexes(LastIndexes last_indexes) {
		this.last_indexes = last_indexes;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getPer_page() {
		return per_page;
	}

	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}
	
}
