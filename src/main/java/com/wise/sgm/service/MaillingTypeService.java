package com.wise.sgm.service;

import com.wise.sgm.model.domain.MaillingType;
import com.wise.sgm.model.domain.UserAuthentication;
import com.wise.sgm.repository.MaillingTypeRepository;
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
public class MaillingTypeService {

    @Autowired
    private MaillingTypeRepository maillingTypeRepository;

    public MaillingType saveFormatLayout(MultipartFile multipartFile, UserAuthentication currentUser) throws Exception{

        MaillingType maillingType = new MaillingType();
        maillingType.getDataControl().markCreate(currentUser);

        if(multipartFile.isEmpty()){
            throw new ValidationException("Arquivo não encontrado");
        }
        String layout = getLayout(multipartFile);
        Optional<MaillingType> maillingTypeByLayout = this.findByLayout(layout);

        if(maillingTypeByLayout.isPresent()){
            throw new ValidationException("Layout já existe");
        }
        maillingType.setLayout(layout);

        return this.save(maillingType);
    }

    public Optional<MaillingType> existLayoutByMultipartFile(MultipartFile multipartFile) throws Exception{
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

    public MaillingType save(MaillingType maillingType) throws Exception{
        return this.maillingTypeRepository.save(maillingType);
    }

    public Optional<MaillingType> findByLayout(String layout) throws Exception{
        return Optional.ofNullable(this.maillingTypeRepository.findByLayout(layout));
    }

}
