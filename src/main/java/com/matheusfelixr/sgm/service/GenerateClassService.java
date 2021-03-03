package com.matheusfelixr.sgm.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class GenerateClassService {

    public void generateClass() throws IOException {

        String nameClass = "MailingLayout1";
        String nameSystem = "sgm";
        String domain = "com.matheusfelixr";

        String directoryBase = "src/main/java/";
        String directoryTypeClass = "/repository/";
        String directoryFull = directoryBase + domain.replace(".", "/")+"/"+nameSystem+directoryTypeClass;

        createRepository(nameClass, nameSystem, domain, directoryFull);
    }

    private void createRepository(String nameClass, String nameSystem, String domain, String directoryFull) throws IOException {


        FileWriter arq = new FileWriter(directoryFull + nameClass +"Repository.java");
        PrintWriter printWriter = new PrintWriter(arq);

        printWriter.println("package "+ domain +"."+ nameSystem +".repository;");
        printWriter.println("\nimport "+ domain +"."+ nameSystem +".model.domain."+ nameClass +";");
        printWriter.println("\nimport org.springframework.data.jpa.repository.JpaRepository;");

        printWriter.println("\npublic interface "+ nameClass +"Repository extends JpaRepository<"+ nameClass +", Long> {");
        printWriter.println("\n}\n");

        arq.close();
    }


}
