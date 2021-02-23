package com.wise.sgm.model.domain;

import com.wise.sgm.model.enums.ReasonMailingEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MAILING_STATUS")
@SequenceGenerator(name = "SEQ_MAILING_STATUS", sequenceName = "SEQ_MAILING_STATUS")
public class MailingStatus implements Serializable {

    private static final long serialVersionUID = -3534531151490523860L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILING_STATUS")
    private Long id;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "REASON_MAILING", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReasonMailingEnum reasonMailingEnum;

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
