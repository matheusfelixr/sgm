package com.matheusfelixr.sgm.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateClassService {

    public void generateClass(List<String> attributes) throws IOException {

        String nameClass = "PeRsoN";
        nameClass = nameClass.toLowerCase();
        nameClass = StringUtils.capitalize(nameClass);

        String nameEntity = "person";
        nameEntity = nameEntity.toUpperCase();

        String nameSystem = "sgm";
        String domain = "com.matheusfelixr";

        String directoryBase = "src/main/java/";


        Boolean isDataControl = true;
        Boolean isCancelled = true;
        String directoryTypeClassEntity = "/model/domain/";
        CreateEntity(nameClass, nameEntity, nameSystem, domain, directoryBase, directoryTypeClassEntity, isDataControl, isCancelled, attributes);

        String directoryTypeClassRepository = "/repository/";
        createRepository(nameClass, nameSystem, domain, directoryBase, directoryTypeClassRepository);
    }

    private void CreateEntity(String nameClass, String nameEntity, String nameSystem, String domain, String directoryBase, String directoryTypeClassEntity, Boolean isDataControl, Boolean isCancelled, List<String> attributes) throws IOException {
        String directoryFull = directoryBase + domain.replace(".", "/") + "/" + nameSystem + directoryTypeClassEntity;

        FileWriter arq = new FileWriter(directoryFull + nameClass + ".java");
        PrintWriter printWriter = new PrintWriter(arq);
        printWriter.println("package " + domain + "." + nameSystem + ".model.domain;");

        printWriter.println("\nimport lombok.Data;");

        printWriter.println("\nimport javax.persistence.*;");
        printWriter.println("import java.io.Serializable;");

        printWriter.println("\n@Data");
        printWriter.println("@Entity");
        printWriter.println("@Table(name = \"" + nameEntity + "\")");
        printWriter.println("@SequenceGenerator(name = \"SEQ_" + nameEntity + "\", sequenceName = \"SEQ_" + nameEntity + "\", allocationSize = 1)");
        printWriter.println("public class " + nameClass + " implements Serializable {");

        printWriter.println("\n\tprivate static final long serialVersionUID = -1L;");

        printWriter.println("\n\t@Id");
        printWriter.println("\t@Column(name = \"id\")");
        printWriter.println("\t@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"SEQ_" + nameEntity + "\")");
        printWriter.println("\tprivate Long id;");

        for(String attribute :attributes){
            printWriter.println("\n\t@Column(name = \""+attribute.toUpperCase()+"\")");
            printWriter.println("\tprivate String "+attribute+";");
        }

        if(isDataControl){
            printWriter.println("\n\t@Embedded");
            printWriter.println("\tprivate DataControlImpl dataControl;");
        }

        if(isCancelled){
            printWriter.println("\n\t@Embedded");
            printWriter.println("\tprivate CancellationImpl cancellation;");
        }

        if(isDataControl){
            printWriter.println("\n\tpublic DataControlImpl getDataControl() {");
            printWriter.println("\t\tif(this.dataControl== null){");
            printWriter.println("\t\t\tdataControl = new DataControlImpl();");
            printWriter.println("\t\t}");
            printWriter.println("\t\treturn dataControl;");
            printWriter.println("\t}");
        }

        if(isCancelled){
            printWriter.println("\n\tpublic CancellationImpl getCancellation() {");
            printWriter.println("\t\tif(this.cancellation== null){");
            printWriter.println("\t\t\tcancellation = new CancellationImpl();");
            printWriter.println("\t\t}");
            printWriter.println("\t\treturn cancellation;");
            printWriter.println("\t}");
        }

        printWriter.println("\n}");

        arq.close();
    }

    private void createRepository(String nameClass, String nameSystem, String domain, String directoryBase, String directoryTypeClass) throws IOException {

        String directoryFull = directoryBase + domain.replace(".", "/") + "/" + nameSystem + directoryTypeClass;


        FileWriter arq = new FileWriter(directoryFull + nameClass + "Repository.java");
        PrintWriter printWriter = new PrintWriter(arq);

        printWriter.println("package " + domain + "." + nameSystem + ".repository;");
        printWriter.println("\nimport " + domain + "." + nameSystem + ".model.domain." + nameClass + ";");
        printWriter.println("import org.springframework.data.jpa.repository.JpaRepository;");

        printWriter.println("\npublic interface " + nameClass + "Repository extends JpaRepository<" + nameClass + ", Long> {");
        printWriter.println("\n}\n");

        arq.close();
    }


}
