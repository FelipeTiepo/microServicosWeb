package br.com.fiap.produtomvc.controllers;

import br.com.fiap.produtomvc.models.Produto;
import br.com.fiap.produtomvc.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//URL - localhost:8080/produtos
//código omitido
@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    //injeção de pedendência
    @Autowired
    private ProdutoRepository repository;

    //URL - localhost:8080/produtos/novo
    @GetMapping("/novo")
    public String adicionarProduto(Model model){

        model.addAttribute("produto", new Produto());
        //model -> enviar o obj. Produto para a view
        return "produto/novo-produto";
    } //código omitido

    // código omitido
    // receber dados do form da View novo-produto.html
    //URL - localhost:8080/produtos/salvar
    @PostMapping("/salvar")
    public String insertProduto(@Valid Produto produto,
                                BindingResult result,
                                RedirectAttributes attributes){
    if (result.hasErrors()){
        return "produto/novo-produto";
    }
        repository.save(produto);
        attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso");
        //redireciona para localhost:8080/produtos/novo
        return "redirect:/produtos/novo";
    }
    //URL - localhost:8080/produtos/listar
    @GetMapping("/listar")
    public String listarProduto(Model model) {
        model.addAttribute("produtos", repository.findAll());
        return "/produto/listar-produtos"; //View
    }

}










