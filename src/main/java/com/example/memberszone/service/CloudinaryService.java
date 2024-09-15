package com.example.memberszone.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	private final Cloudinary cloudinary;

	@Autowired
	public CloudinaryService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	public String uploadImage(MultipartFile file) throws IOException {
		try {
			Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			return (String) uploadResult.get("url");
		} catch (IOException e) {
			throw new IOException("Failed to upload image to Cloudinary", e);
		}
	}
}
