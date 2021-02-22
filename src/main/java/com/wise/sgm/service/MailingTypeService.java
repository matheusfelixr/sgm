package com.wise.sgm.service;

import com.wise.sgm.model.domain.MailingType;
import com.wise.sgm.repository.MailingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class MailingTypeService {

    @Autowired
    private MailingTypeRepository mailingTypeRepository;

    public MailingType saveFormatLayout(MultipartFile multipartFile) throws Exception{

        MailingType mailingType = new MailingType();
        mailingType.getDataControl().markCreate();

        if(multipartFile.isEmpty()){
            throw new ValidationException("Arquivo não encontrado");
        }
        String layout = getLayout(multipartFile);
        Optional<MailingType> mailingTypeByLayout = this.findByLayout(layout);

        if(mailingTypeByLayout.isPresent()){
            throw new ValidationException("Layout já existe");
        }
        mailingType.setLayout(layout);

        return this.save(mailingType);
    }

    public Optional<MailingType> existLayoutByMultipartFile(MultipartFile multipartFile) throws Exception{
       return this.findByLayout(this.getLayout(multipartFile));
    }

    private String getLayout(MultipartFile multipartFile) throws Exception {
        java.io.File file = new java.io.File(multipartFile.getOriginalFilename());
        FileOutputStream in = new FileOutputStream(file) ;
        in.write(multipartFile.getBytes());
        in.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()), "ISO-8859-1"));
        String layout = br.readLine();
        br.close();
        file.delete();
        return layout;
    }

    public MailingType save(MailingType mailingType) throws Exception{
        return this.mailingTypeRepository.save(mailingType);
    }

    public Optional<MailingType> findByLayout(String layout) throws Exception{
        return Optional.ofNullable(this.mailingTypeRepository.findByLayout(layout));
    }

}
