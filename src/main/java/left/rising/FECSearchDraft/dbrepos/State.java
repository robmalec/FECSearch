package left.rising.FECSearchDraft.dbrepos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "states")
public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //
	private Integer id;
	
	private double opacity;
	private Integer OSMStateId;
	private String stateName;
	private String stateCode;
	
	public State() {}
	
	public State(double opacity, Integer oSMStateId, String stateName, String stateCode) {
		super();
		this.opacity = opacity;
		OSMStateId = oSMStateId;
		this.stateName = stateName;
		this.stateCode = stateCode;
	}

	public State(Integer id, double opacity, Integer oSMStateId, String stateName, String stateCode) {
		super();
		this.id = id;
		this.opacity = opacity;
		OSMStateId = oSMStateId;
		this.stateName = stateName;
		this.stateCode = stateCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public Integer getOSMStateId() {
		return OSMStateId;
	}

	public void setOSMStateId(Integer oSMStateId) {
		OSMStateId = oSMStateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Override
	public String toString() {
		return "State [id=" + id + ", opacity=" + opacity + ", OSMStateId=" + OSMStateId + ", stateName=" + stateName
				+ ", stateCode=" + stateCode + "]";
	}
}
