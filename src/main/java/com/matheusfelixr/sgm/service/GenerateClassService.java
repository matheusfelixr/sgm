package com.matheusfelixr.sgm.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class GenerateClassService {

    public void generateClass() throws IOException {

        String nameClass = "Person";
        String nameSystem = "sgm";

        FileWriter arq = new FileWriter("/"+nameClass+"Repository.java");
        PrintWriter gravarArq = new PrintWriter(arq);

        gravarArq.println("package com.matheusfelixr."+nameSystem+".repository;");
        gravarArq.println("\nimport com.matheusfelixr."+nameSystem+".model.domain."+nameClass+";");
        gravarArq.println("\nimport org.springframework.data.jpa.repository.JpaRepository;");

        gravarArq.println("\npublic interface "+nameClass+"Repository extends JpaRepository<"+nameClass+", Long> {");
        gravarArq.println("\n}\n");


        arq.close();

    }
}
