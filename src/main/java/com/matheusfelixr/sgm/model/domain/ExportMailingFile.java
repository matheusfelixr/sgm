package com.matheusfelixr.sgm.model.domain;

import com.matheusfelixr.sgm.model.enums.TransactionsStatusEnum;
import com.matheusfelixr.sgm.model.enums.TypeExportedEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "EXPORT_MAILING_FILE")
@SequenceGenerator(name = "SEQ_EXPORT_MAILING_FILE", sequenceName = "SEQ_EXPORT_MAILING_FILE", allocationSize = 1)
public class ExportMailingFile implements Serializable {

    private static final long serialVersionUID = -342018151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXPORT_MAILING_FILE")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE_EXPORT", nullable = true)
    private Date startDateExport;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE_EXPORT", nullable = true)
    private Date endDateExport;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_EXPORTED", nullable = false)
    private TypeExportedEnum typeExported;

    @Column(name = "FILE", nullable = true)
    private byte[] file;

    @Column(name = "TRANSACTIONS_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionsStatusEnum transactionsStatus;

    @Column(name = "ERROR", nullable = true, length = 9999)
    private String error;

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exportMailingFiles")
    private List<Mailing> mailings;

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
