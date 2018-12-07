package trello;

import java.util.Date;

public class Card {
	
	private double estimatedTime;
	private String CardNumber;
	private String DependsOn;
	private double actualTime;
	private Date dueDate;
	public Card(double esTime,String cNumber, String depOn, double actTime, Date dueDate) {
		estimatedTime = esTime;
		CardNumber = cNumber;
		DependsOn = depOn;
		actualTime = actTime;
		this.dueDate = dueDate;
	}
	
	public Card(double esTime, String q, String r, Date dueDate) {
		estimatedTime = esTime;
		CardNumber = q;
		DependsOn = r;
		this.dueDate = dueDate;
	}

	public double getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(double estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public String getCardNumber() {
		return CardNumber;
	}
	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}
	public String getDependsOn() {
		return DependsOn;
	}
	public void setDependsOn(String dependsOn) {
		DependsOn = dependsOn;
	}
	public double getActualTime() {
		return actualTime;
	}
	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
}
