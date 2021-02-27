package com.wise.sgm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MAILLING_TYPE")
@SequenceGenerator(name = "SEQ_MAILLING_TYPE", sequenceName = "SEQ_MAILLING_TYPE")
public class MaillingType implements Serializable {

    private static final long serialVersionUID = -12312351490523860L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILLING_TYPE")
    private Long id;

    @Column(name = "LAYOUT", nullable = true)
    private String layout;

    @Embedded
    private DataControlImpl dataControl;

    public DataControlImpl getDataControl() {
        if (this.dataControl == null) {
            dataControl = new DataControlImpl();
        }
        return dataControl;
    }
}
