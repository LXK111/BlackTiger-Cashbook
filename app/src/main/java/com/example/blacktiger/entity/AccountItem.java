package com.example.blacktiger.entity;

/**
 * 实体类
 *
 */
public class AccountItem implements java.io.Serializable{  //可以序列化
	private int id;
	private String category;  //类别
	private String remark;  //备注
	private double money;   //金额
	private String date;   //日期

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public AccountItem(int id, String category, String remark, double money,
					   String date) {
		super();
		this.id = id;
		this.category = category;
		this.remark = remark;
		this.money = money;
		this.date = date;
	}

	public AccountItem(){

	}

	@Override
	public String toString() {
		return this.category + "   " + this.date +  "   " + this.money;
	}

}
