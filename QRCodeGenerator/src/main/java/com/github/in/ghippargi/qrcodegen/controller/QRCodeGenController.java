package com.github.in.ghippargi.qrcodegen.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Hashtable;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.github.in.ghippargi.qrcodegen.data.QrCodeInput;

@RestController
public class QRCodeGenController {
    
	@RequestMapping(method=RequestMethod.POST, path="/api/genqrcode", consumes = "application/json", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> genQrCode(@RequestBody QrCodeInput input) throws Exception 
    {       
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
	    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // H = 30% damage
	    
	    StringBuilder strBuild = new StringBuilder();
	    if (input.getEmergencyNum() != null) {
	    	strBuild.append("tel:");
	    	strBuild.append(input.getEmergencyNum());
	    	strBuild.append(System.lineSeparator());
	    }
	    if (input.getSmsNum() != null) {
	    	strBuild.append("SMSTO:");
	    	strBuild.append(input.getSmsNum());
	    	strBuild.append(":");
	    	strBuild.append(input.getSmsMsg());
	    	strBuild.append(System.lineSeparator());
	    }
	    if (input.getUrl() != null && !input.getUrl().trim().isEmpty()) {
	    	strBuild.append("URL:");
	    	strBuild.append(input.getUrl());
	    	strBuild.append(System.lineSeparator());
	    }
	    if (input.getInfo() != null && !input.getInfo().trim().isEmpty()) {
	    	strBuild.append(input.getInfo());
	    }
	    
        BitMatrix bitMatrix = qrCodeWriter.encode(strBuild.toString(), BarcodeFormat.QR_CODE, 200, 200, hintMap);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/png");
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
}