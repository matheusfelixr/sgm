package com.wise.sgm.model.domain;

import com.wise.sgm.model.enums.ImportStatus;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILING")
    private Long id;

   

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
