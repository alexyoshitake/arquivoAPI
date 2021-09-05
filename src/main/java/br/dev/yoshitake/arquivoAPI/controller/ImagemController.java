package br.dev.yoshitake.arquivoAPI.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.dev.yoshitake.arquivoAPI.dto.CoordenadaDTO;
import br.dev.yoshitake.arquivoAPI.service.ImagemService;

@RestController
@RequestMapping("/imagem")
public class ImagemController {

	@Autowired
	private ImagemService imagemService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> recortar(MultipartFile arquivo, CoordenadaDTO coordenada) throws IOException {

		String extension = FilenameUtils.getExtension(arquivo.getOriginalFilename());

		BufferedImage imgCortada = imagemService.recortar(arquivo, coordenada);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(imgCortada, extension, baos);
		byte[] bytes = baos.toByteArray();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
		responseHeaders.setContentType(MediaType.parseMediaType(arquivo.getContentType()));
		responseHeaders.setContentLength(bytes.length);
		responseHeaders.set("Content-disposition", "attachment; filename=".concat("prato.".concat(extension)));

		return new ResponseEntity<byte[]>(bytes, responseHeaders, HttpStatus.OK);
	}
}
