package com.example.taskworklife.fileservice;

import com.example.taskworklife.config.ReserveringConfiguration;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@EnableScheduling
public class FileService  {

    ReserveringConfiguration appConfiguration;

    Tika tika;

    public FileService(ReserveringConfiguration reserveringConfiguration) {
        this.appConfiguration = reserveringConfiguration;
    }

    public String saveImage(String base64Image, String type, String naam) throws IOException {
        String imageName = getRandomName();
        if(base64Image==null){
            return "";
        }
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        File target;
        switch (type){
            case "profile":
                target = new File(appConfiguration.getFullProfileImagesPath() + "/" + naam+ "/"+ imageName);
                break;
            case "kamer":
                target = new File(appConfiguration  .getProfileImagesFolder() + "/" + naam+ "/"+ imageName);
                break;
            case "attachment":
                target = new File(appConfiguration.getAttachmentFolder()+"/"+ imageName);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        FileUtils.writeByteArrayToFile(target, decodedBytes);
        return imageName;
    }
    public String detectType(byte[] fileArr) {
        return tika.detect(fileArr);
    }





    private String getRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
