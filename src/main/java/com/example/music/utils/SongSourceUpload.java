package com.example.music.utils;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.StoredFile;

import lombok.Getter;
import lombok.Setter;

public class SongSourceUpload extends StoredFile {
    private @Getter @Setter String title;
    private @Getter @Setter MultipartFile file;

    public String getUrl(Cloudinary instance) {
        if (version != null && format != null && publicId != null) {
            return instance.url()
                    .resourceType(resourceType)
                    .type(type)
                    .format(format)
                    .version(version)
                    .generate(publicId);
        } else
            return null;
    }
}
