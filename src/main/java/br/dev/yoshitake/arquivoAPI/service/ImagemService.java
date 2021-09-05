package br.dev.yoshitake.arquivoAPI.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.dev.yoshitake.arquivoAPI.dto.CoordenadaDTO;

@Service
public class ImagemService {

	public BufferedImage recortar(MultipartFile arquivo, CoordenadaDTO map) throws IOException {

		BufferedImage bufferedImage = ImageIO.read(arquivo.getInputStream());

		int width = (map.getWidth() != 0) ? map.getWidth() : bufferedImage.getWidth();
		int height = (map.getHeight() != 0) ? map.getHeight() : bufferedImage.getHeight();

		BufferedImage subimage = bufferedImage.getSubimage(map.getPosicaoX(), map.getPosicaoY(), width, height);

		return subimage;
	}
}
