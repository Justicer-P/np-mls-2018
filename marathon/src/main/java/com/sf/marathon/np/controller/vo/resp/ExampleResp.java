package com.sf.marathon.np.controller.vo.resp;

import java.io.Serializable;
import java.util.Date;

public class ExampleResp implements Serializable{
	
	private static final long serialVersionUID = -7656744014391543309L;
	
	private Long id;

	private String marketId;

	private Date createTime;

	private String periodNum;

	private String picture;

	private String groupType;

	private Integer minBagNum;

	private Double itemWeight;

	private Integer promisePeriod;

	private Date endTime;

	private Double minPrice;
	
	private Integer signedNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Integer getMinBagNum() {
		return minBagNum;
	}

	public void setMinBagNum(Integer minBagNum) {
		this.minBagNum = minBagNum;
	}

	public Double getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(Double itemWeight) {
		this.itemWeight = itemWeight;
	}

	public Integer getPromisePeriod() {
		return promisePeriod;
	}

	public void setPromisePeriod(Integer promisePeriod) {
		this.promisePeriod = promisePeriod;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getSignedNum() {
		return signedNum;
	}

	public void setSignedNum(Integer signedNum) {
		this.signedNum = signedNum;
	}
	
}
