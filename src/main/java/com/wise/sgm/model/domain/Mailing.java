package com.wise.sgm.model.domain;

import com.wise.sgm.model.domain.mailingLayouts.MailingLayout1;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "MAILING")
@SequenceGenerator(name = "SEQ_MAILING", sequenceName = "SEQ_MAILING")
public class Mailing implements Serializable {

    private static final long serialVersionUID = -367078151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILING")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILING_STATUS", referencedColumnName = "ID")
    private MailingStatus mailingStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = false)
    private Date endDate;


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
