package com.matheusfelixr.sgm.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class GenerateClassService {

    public void generateClass() throws IOException {

        String nameClass = "PeRsoN";
        nameClass = nameClass.toLowerCase();
        nameClass = StringUtils.capitalize(nameClass);
        
        String nameEntity = "person";
        nameEntity = nameEntity.toUpperCase();

        String nameSystem = "sgm";
        String domain = "com.matheusfelixr";

        String directoryBase = "src/main/java/";





        String directoryTypeClassEntity = "/model/domain/";
        CreateEntity(nameClass, nameEntity, nameSystem, domain, directoryBase, directoryTypeClassEntity);

        String directoryTypeClassRepository = "/repository/";
        createRepository(nameClass, nameSystem, domain, directoryBase, directoryTypeClassRepository);
    }

    private void CreateEntity(String nameClass, String nameEntity, String nameSystem, String domain, String directoryBase, String directoryTypeClassEntity) throws IOException {
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
