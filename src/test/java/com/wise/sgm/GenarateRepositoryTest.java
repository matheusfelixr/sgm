package com.wise.sgm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@SpringBootTest
class GenarateRepositoryTest {

	@Test
	void contextLoads() throws IOException {

		String nameClass = "Person";
		String nameSystem = "sgm";

		FileWriter arq = new FileWriter("d:\\"+nameClass+"Repository.java");
		PrintWriter gravarArq = new PrintWriter(arq);

		gravarArq.println("package com.matheus.felix."+nameSystem+".repository;");
		gravarArq.println("\nimport com.matheus.felix."+nameSystem+".model.domain."+nameClass+";");
		gravarArq.println("\nimport org.springframework.data.jpa.repository.JpaRepository;");

		gravarArq.println("\npublic interface "+nameClass+"Repository extends JpaRepository<"+nameClass+", Long> {");
		gravarArq.println("\n}\n");


		arq.close();


	}

}
