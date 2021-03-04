package com.matheusfelixr.sgm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "EXPORT_MAILING")
@SequenceGenerator(name = "SEQ_EXPORT_MAILING", sequenceName = "SEQ_EXPORT_MAILING", allocationSize = 1)
public class ExportMailing implements Serializable {

    private static final long serialVersionUID = -367078151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXPORT_MAILING")
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

    @Column(name = "FILE", nullable = true)
    private byte[] file;

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exportMailings")
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
