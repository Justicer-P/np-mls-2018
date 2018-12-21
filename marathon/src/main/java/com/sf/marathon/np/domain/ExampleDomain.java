package com.sf.marathon.np.domain;

public class ExampleDomain {
	
	private Long groupId;
	
	private String userName;
	
	private Integer numPerDay;

	private Double weightPerDay;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getNumPerDay() {
		return numPerDay;
	}

	public void setNumPerDay(Integer numPerDay) {
		this.numPerDay = numPerDay;
	}

	public Double getWeightPerDay() {
		return weightPerDay;
	}

	public void setWeightPerDay(Double weightPerDay) {
		this.weightPerDay = weightPerDay;
	}

	@Override
	public String toString() {
		return "ExampleDomain [groupId=" + groupId + ", userName=" + userName + ", numPerDay=" + numPerDay
				+ ", weightPerDay=" + weightPerDay + "]";
	}
	
}
