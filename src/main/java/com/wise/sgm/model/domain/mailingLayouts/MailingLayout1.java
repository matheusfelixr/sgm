package com.wise.sgm.model.domain.mailingLayouts;

import com.wise.sgm.model.domain.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

//CODCAMPANHA|CUSTOMER_KEY|CPF CNPJ|NOME|TELEFONE_CONTATO_1|TELEFONE_CONTATO_2|TELEFONE_CONTATO_3|INFORMAÇÕES ADICIONAIS|OFERTA_1|OFERTA_2|OFERTA_3|OFERTA_1_CONDICIONAL|OFERTA_2_CONDICIONAL|NUMERO OPP|SUBSCRIÇÃO|CIDADE|REGIONAL|CAMPANHA
@Data
@Entity
@Table(name = "MAILING_LAYOUT_1")
@SequenceGenerator(name = "SEQ_MAILING_LAYOUT_1", sequenceName = "SEQ_MAILING_LAYOUT_1", allocationSize = 1)
public class MailingLayout1 implements Serializable {

    private static final long serialVersionUID = -456234151490523860L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAILING_LAYOUT_1")
    private Long id;

    @Column(name = "CODCAMPANHA", length = 9999)
    private String CODCAMPANHA;

    @Column(name = "CUSTOMER_KEY", length = 9999)
    private String CUSTOMER_KEY;

    @Column(name = "CPF_CNPJ", length = 9999)
    private String CPF_CNPJ;

    @Column(name = "NOME", length = 9999)
    private String NOME;

    @Column(name = "TELEFONE_CONTATO_1", length = 9999)
    private String TELEFONE_CONTATO_1;

    @Column(name = "TELEFONE_CONTATO_2", length = 9999)
    private String TELEFONE_CONTATO_2;

    @Column(name = "TELEFONE_CONTATO_3", length = 9999)
    private String TELEFONE_CONTATO_3;

    @Column(name = "INFORMAÇÕES_ADICIONAIS", length = 9999)
    private String INFORMAÇÕES_ADICIONAIS;

    @Column(name = "OFERTA_1", length = 9999)
    private String OFERTA_1;

    @Column(name = "OFERTA_2", length = 9999)
    private String OFERTA_2;

    @Column(name = "OFERTA_3", length = 9999)
    private String OFERTA_3;

    @Column(name = "OFERTA_1_CONDICIONAL", length = 9999)
    private String OFERTA_1_CONDICIONAL;

    @Column(name = "OFERTA_2_CONDICIONAL", length = 9999)
    private String OFERTA_2_CONDICIONAL;

    @Column(name = "NUMERO_OPP", length = 9999)
    private String NUMERO_OPP;

    @Column(name = "SUBSCRIÇÃO", length = 9999)
    private String SUBSCRIÇÃO;

    @Column(name = "CIDADE", length = 9999)
    private String CIDADE;

    @Column(name = "REGIONAL", length = 9999)
    private String REGIONAL;

    @Column(name = "CAMPANHA", length = 9999)
    private String CAMPANHA;

    //valores obrigatorios para todos layouts

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAILING_TYPE", referencedColumnName = "ID")
    private MailingType mailingType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_MAILING_FILE", referencedColumnName = "ID")
    private ImportMailingFile importMailingFile;

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

    public static final String getLayout(){
        return "CODCAMPANHA|CUSTOMER_KEY|CPF CNPJ|NOME|TELEFONE_CONTATO_1|TELEFONE_CONTATO_2|TELEFONE_CONTATO_3|INFORMAÇÕES ADICIONAIS|OFERTA_1|OFERTA_2|OFERTA_3|OFERTA_1_CONDICIONAL|OFERTA_2_CONDICIONAL|NUMERO OPP|SUBSCRIÇÃO|CIDADE|REGIONAL|CAMPANHA";
    }
}
