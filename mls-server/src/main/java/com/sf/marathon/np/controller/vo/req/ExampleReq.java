package com.sf.marathon.np.controller.vo.req;

import java.io.Serializable;

public class ExampleReq implements Serializable {

	private static final long serialVersionUID = -5114187785032952299L;

	// groupId
	private Long groupId;
	// 姓名
	private String userName;

	// 手机号码
	private String userCellphone;

	// 地址
	private String userAddress;

	// 每日寄件量
	private Integer numPerDay;

	// 平均寄件重量
	private Double weightPerDay;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCellphone() {
		return userCellphone;
	}

	public void setUserCellphone(String userCellphone) {
		this.userCellphone = userCellphone;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
