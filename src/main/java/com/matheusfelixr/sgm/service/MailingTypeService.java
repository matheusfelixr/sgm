package com.matheusfelixr.sgm.service;

import com.matheusfelixr.sgm.model.domain.MailingType;
import com.matheusfelixr.sgm.model.domain.UserAuthentication;
import com.matheusfelixr.sgm.repository.MailingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MailingTypeService {

    @Autowired
    private MailingTypeRepository mailingTypeRepository;

    public MailingType saveFormatLayout(MultipartFile multipartFile, UserAuthentication currentUser) throws Exception {

        MailingType mailingType = new MailingType();
        mailingType.getDataControl().markCreate(currentUser);

        if (multipartFile.isEmpty()) {
            throw new ValidationException("Arquivo não encontrado");
        }
        String layout = getLayout(multipartFile);
        Optional<MailingType> maillingTypeByLayout = this.findByLayout(layout);

        if (maillingTypeByLayout.isPresent()) {
            throw new ValidationException("Layout já existe");
        }
        mailingType.setLayout(layout);
        mailingType.setNumberOfFields(this.getQuantityColumns(multipartFile));

        return this.save(mailingType);
    }

    public Optional<MailingType> existLayoutByMultipartFile(MultipartFile multipartFile) throws Exception {
        return this.findByLayout(this.getLayout(multipartFile));
    }

    private String getLayout(MultipartFile multipartFile) throws Exception {
        java.io.File file = new java.io.File(multipartFile.getOriginalFilename());
        FileOutputStream in = new FileOutputStream(file);
        in.write(multipartFile.getBytes());
        in.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "ISO-8859-1"));
        String layout = br.readLine();
        br.close();
        file.delete();
        return layout;
    }

    private int getQuantityColumns(MultipartFile multipartFile) throws Exception {
        java.io.File file = new java.io.File(multipartFile.getOriginalFilename());
        FileOutputStream in = new FileOutputStream(file);
        in.write(multipartFile.getBytes());
        in.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "ISO-8859-1"));
        Object[] lines = br.lines().toArray();


        int numberOfColumns = 0;
        Object lineObj = Arrays.stream(lines).findFirst();
        List<String> linesStr = new ArrayList<>();
        linesStr.add(lineObj.toString());

        for (String line : linesStr) {
            int skip = 0;
            int nextPipe = 0;
            String lineFor = line.toString();
            for (skip = 0; skip < 1; ) {
                numberOfColumns++;
                nextPipe = lineFor.indexOf("|");
                if (nextPipe == -1) {
                    skip = 1;
                    System.out.println(lineFor);
                }else{
                    System.out.println(lineFor);
                }
                lineFor = lineFor.substring(nextPipe + 1, lineFor.length());
            }
        }
        br.close();
        file.delete();
        return numberOfColumns;
    }

    public MailingType save(MailingType mailingType) throws Exception {
        return this.mailingTypeRepository.save(mailingType);
    }

    public Optional<MailingType> findByLayout(String layout) throws Exception {
        return Optional.ofNullable(this.mailingTypeRepository.findByLayout(layout));
    }

}
