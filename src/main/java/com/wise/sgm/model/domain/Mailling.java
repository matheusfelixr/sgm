package com.wise.sgm.model.domain;

import com.wise.sgm.model.domain.maillingLayouts.MaillingLayout1;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "MAILLING")
@SequenceGenerator(name = "SEQ_MAILLING", sequenceName = "SEQ_MAILLING")
public class Mailling implements Serializable {

    private static final long serialVersionUID = -367078151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILLING")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILLING_STATUS", referencedColumnName = "ID")
    private MaillingStatus maillingStatus;

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
    @JoinColumn(name = "MAILLING_LAYOUT_1", referencedColumnName = "ID")
    private MaillingLayout1 maillingLayout1;

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
