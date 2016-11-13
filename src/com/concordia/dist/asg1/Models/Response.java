package com.concordia.dist.asg1.Models;

/**
 * Response Model, use as return type
 * @author SajjadAshrafCan
 *
 */
public class Response {

	public int returnID = -1 ;
	public boolean status = false;
	public String message = "";
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [returnID=" + returnID + ", status=" + status + ", message=" + message + "]";
	}
	
	
}
