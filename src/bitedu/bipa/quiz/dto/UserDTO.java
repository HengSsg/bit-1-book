package bitedu.bipa.quiz.dto;

import java.sql.Date;

public class UserDTO {
	private String status;
	private int maxBook;
	private Date serviceStop;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getMaxBook() {
		return maxBook;
	}
	public void setMaxBook(int maxBook) {
		this.maxBook = maxBook;
	}
	public Date getServiceStop() {
		return serviceStop;
	}
	public void setServiceStop(Date serviceStop) {
		this.serviceStop = serviceStop;
	}
	
	
}
