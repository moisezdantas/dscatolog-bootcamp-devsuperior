package com.devsuperior.dscatolog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.dscatolog.services.S3Service;

@SpringBootApplication
public class DscatologApplication implements CommandLineRunner{
	
	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(DscatologApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			
		s3Service.uploadFile("/home/moisez_dantas/Imagens/nova.png");
		
	}

}
