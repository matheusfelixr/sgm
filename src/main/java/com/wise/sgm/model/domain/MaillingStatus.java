package com.wise.sgm.model.domain;

import com.wise.sgm.model.enums.ReasonMaillingEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MAILLING_STATUS")
@SequenceGenerator(name = "SEQ_MAILLING_STATUS", sequenceName = "SEQ_MAILLING_STATUS")
public class MaillingStatus implements Serializable {

    private static final long serialVersionUID = -3534531151490523860L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILLING_STATUS")
    private Long id;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "REASON_MAILLING", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReasonMaillingEnum reasonMailling;

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
