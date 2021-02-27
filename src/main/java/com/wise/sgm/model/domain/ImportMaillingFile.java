package com.wise.sgm.model.domain;

import com.wise.sgm.model.enums.ImportStatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "IMPORT_MAILLING_FILE")
@SequenceGenerator(name = "SEQ_IMPORT_MAILLING_FILE", sequenceName = "SEQ_IMPORT_MAILLING_FILE")
public class ImportMaillingFile implements Serializable {

    private static final long serialVersionUID = -1231231151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_IMPORT_MAILLING_FILE")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "FILE", nullable = true)
    private byte[] file;

    @Column(name = "NAME_FILE", nullable = true)
    private String nameFile;

    @Column(name = "IMPORT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportStatusEnum importStatusEnum;

    @Column(name = "ERROR", nullable = true)
    private String error;

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