package com.matheusfelixr.sgm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "PERSON")
@SequenceGenerator(name = "SEQ_PERSON", sequenceName = "SEQ_PERSON", allocationSize = 1)
public class Person implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON")
	private Long id;

	@Embedded
	private DataControlImpl dataControl;

	@Embedded
	private CancellationImpl cancellation;

	public DataControlImpl getDataControl() {
		if(this.dataControl== null){
			dataControl = new DataControlImpl();
		}
		return dataControl;
	}

	public CancellationImpl getCancellation() {
		if(this.cancellation== null){
			cancellation = new CancellationImpl();
		}
		return cancellation;
	}

}
