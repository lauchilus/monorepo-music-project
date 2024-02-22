package com.lauchilus.microservice.cloudinary;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.cloudinary.Cloudinary;
import com.lauchilus.microservice.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@RequiredArgsConstructor
public class CloudinaryComs {


    private final CloudinaryService cloudinaryService;




    public Images upload(MultipartFile multipartFile) throws IOException {
    	 try {
             BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
             if (bi == null) {
                 return null;
             }

             Map result = cloudinaryService.upload(multipartFile);
             Images image = new Images((String) result.get("original_filename"),
                     (String) result.get("url"),
                     (String) result.get("public_id"));
             return image;
         } catch (IOException e) {
             
             e.printStackTrace();
             return null; 
         }
    }

    public Images getImage(){
        return null;
    }

    public void delete(long id) {


    }
}
