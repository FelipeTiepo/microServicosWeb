package br.com.fiap.hello.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HomeController {
	
	@GetMapping()
	public String mostrarMensagem(Model model) {
		
		model.addAttribute("mensagem", "Minha primeira mensagem");
		
		return "view/hello";
	}
	
}
