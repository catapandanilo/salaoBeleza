package br.com.catapan.salaobeleza.application.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.catapan.salaobeleza.util.IOUtils;

@Service
public class ImageService {
	@Value("${salaobeleza.files.logotipo}")
	private String logotiposDir;
	
	@Value("${salaobeleza.files.categoria}")
	private String categoriasDir;
	
	@Value("${salaobeleza.files.servico}")
	private String servicosDir;
	
	public void uploadLogotipo(MultipartFile multipartFile, String fileName) {
		try {
			IOUtils.copy(multipartFile.getInputStream(), fileName, logotiposDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	public void uploadServico(MultipartFile multipartFile, String fileName) {
		try {
			IOUtils.copy(multipartFile.getInputStream(), fileName, servicosDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	public byte[] getBytes(String type, String imgName) {
		try {
			String dir;
			if ("servico".equals(type)) {
				dir = servicosDir;
			} 
			else if ("logotipo".equals(type)) {
				dir = logotiposDir;
			} 
			else if ("categoria".equals(type)) {
				dir = categoriasDir;
			} 
			else {
				throw new Exception(type + " não é um tipo de imagem válido");
			}
			
			return IOUtils.getBytes(Paths.get(dir, imgName));
		} catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
	}
}
