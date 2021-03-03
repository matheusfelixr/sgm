package com.matheusfelixr.sgm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "MAILING")
@SequenceGenerator(name = "SEQ_MAILING", sequenceName = "SEQ_MAILING", allocationSize = 1)
public class Mailing implements Serializable {

    private static final long serialVersionUID = -367078151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAILING")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILING_STATUS", referencedColumnName = "ID")
    private MailingStatus mailingStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = true)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_SENT_TO_USER", nullable = true)
    private Date dateSentToUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENT_TO_USER", referencedColumnName = "ID", nullable = true)
    private UserAuthentication sentToUser;


    //Layouts vão aqui cada layout novo deve ser adicionado aqui
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILING_LAYOUT_1", referencedColumnName = "ID")
    private MailingLayout1 mailingLayout1;

    @Embedded
    private CancellationImpl cancellation;

    @Embedded
    private DataControlImpl dataControl;


    public CancellationImpl getCancellation() {
        if(this.cancellation == null){
            cancellation = new CancellationImpl();
        }
        return cancellation;
    }

    public DataControlImpl getDataControl() {
        if(this.dataControl== null){
            dataControl = new DataControlImpl();
        }
        return dataControl;
    }
}
