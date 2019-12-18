package left.rising.FECSearchDraft.dbrepos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomStateProperty {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String stateCode = "";
	private String category = "";
	private Double value = 0.0;
	
	CustomStateProperty(){}
	
	public CustomStateProperty(String stateCode, String category, Double value) {
		super();
		this.stateCode = stateCode;
		this.category = category;
		this.value = value;
	}
	
	public CustomStateProperty(Integer id, String stateCode, String category, Double value) {
		super();
		this.id = id;
		this.stateCode = stateCode;
		this.category = category;
		this.value = value;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
