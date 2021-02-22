package com.wise.sgm.model.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DataControlImpl implements Serializable {

	private static final long serialVersionUID = -1107231151490523860L;

	@Column(name = "CREATE_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "CREATE_USER", referencedColumnName = "ID", nullable = false)
//	private User createUser;

	@Column(name = "UPDATE_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "UPDATE_USER", referencedColumnName = "ID", nullable = false)
//	private User updateUser;

	public void markModified() {
		this.setUpdateDate(new Date());
	}

	public void markCreate() {
		this.setCreateDate(new Date());
		this.markModified();
	}


}