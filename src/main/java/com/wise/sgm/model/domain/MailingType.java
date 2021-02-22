package com.wise.sgm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MAILING_TYPE")
@SequenceGenerator(name = "SEQ_MAILING_TYPE", sequenceName = "SEQ_MAILING_TYPE")
public class MailingType implements Serializable {

    private static final long serialVersionUID = -908898951490523860L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILING_TYPE")
    private Long id;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "FORMAT", nullable = true)
    private String[] format;

    @Embedded
    private CancellationImpl cancellation;

    @Embedded
    private DataControlImpl dataControl;


    public CancellationImpl getCancellation() {
        if (this.cancellation == null) {
            cancellation = new CancellationImpl();
        }
        return cancellation;
    }

    public DataControlImpl getDataControl() {
        if (this.dataControl == null) {
            dataControl = new DataControlImpl();
        }
        return dataControl;
    }
}
