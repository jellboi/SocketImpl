package com.socketimpl.beans;

import java.io.Serializable;
import java.util.Date;

import com.socketimpl.api.IEntity;

public class BaseEntity implements IEntity, Serializable {
	
	private Integer pkey;
	private Date createdOn;

	/* Getters and Setters */
	
	public Integer getPkey() {
		return pkey;
	}

	public void setPkey(Integer pkey) {
		this.pkey = pkey;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
