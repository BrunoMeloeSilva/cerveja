package github.com.brunomeloesilva.cervejas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import github.com.brunomeloesilva.cervejas.dto.FotoDTO;
import github.com.brunomeloesilva.cervejas.storage.FotoStorage;
import github.com.brunomeloesilva.cervejas.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;

	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		
		DeferredResult<FotoDTO> deferredResult = new DeferredResult<FotoDTO>();
		Thread thread = new Thread(new FotoStorageRunnable(files, deferredResult, fotoStorage));
		thread.start();
		return deferredResult;
	}
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporaria(@PathVariable String nome) {
		return fotoStorage.recuperarFotoTemporaria(nome);
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return fotoStorage.recuperar(nome);
	}
	
}