package com.matheusfelixr.sgm.model.domain;

import com.matheusfelixr.sgm.model.enums.ImportStatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "IMPORT_MAILING_FILE")
@SequenceGenerator(name = "SEQ_IMPORT_MAILING_FILE", sequenceName = "SEQ_IMPORT_MAILING_FILE", allocationSize = 1)
public class ImportMailingFile implements Serializable {

    private static final long serialVersionUID = -1231231151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMPORT_MAILING_FILE")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;

    @Column(name = "FILE", nullable = true)
    private byte[] file;

    @Column(name = "NAME_FILE", nullable = true, length = 9999)
    private String nameFile;

    @Column(name = "IMPORT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportStatusEnum importStatusEnum;

    @Column(name = "ERROR", nullable = true, length = 9999)
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