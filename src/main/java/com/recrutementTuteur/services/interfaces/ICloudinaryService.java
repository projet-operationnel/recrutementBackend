package com.recrutementTuteur.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    String uploadPhoto(MultipartFile file);
}
