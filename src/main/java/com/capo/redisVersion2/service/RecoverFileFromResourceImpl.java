package com.capo.redisVersion2.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.capo.redisVersion2.interfaces.RecoverFileFromResource;

@Service
public class RecoverFileFromResourceImpl implements RecoverFileFromResource {
	
	@Override
	public String getFileInString(String address) throws Exception{
		InputStream classLoader= getClass().getResourceAsStream(address);
		String json= new String(classLoader.readAllBytes(),StandardCharsets.UTF_8);
		return json;
	}
}
