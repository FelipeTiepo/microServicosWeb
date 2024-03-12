package br.com.fiap.produtomvc.controllers;

import br.com.fiap.produtomvc.models.Produto;
import br.com.fiap.produtomvc.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// -- código omitido
//URL - localhost:8080/produtos
@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    //URL - localhost:8080/produtos/novo
    @GetMapping("/novo")
    public String adicionarProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/novo-produto";
    }

    @PostMapping("/salvar")
    @Transactional
    public String insertProduto(@Valid Produto produto,
                                BindingResult result,
                                RedirectAttributes attributes) {
        if(result.hasErrors()){
            return "produto/novo-produto";
        }
        repository.save(produto);
        attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso");
        return "redirect:/produtos/novo";
    }

    //URL - localhost:8080/produtos/listar
    @GetMapping("/listar")
    @Transactional(readOnly = true)
    public String listarProdutos(Model model){
        model.addAttribute("produtos", repository.findAll());
        return "/produto/listar-produtos"; //View
    }

    //URL - localhost:8080/produtos/editar/1
    @GetMapping("/editar/{id}")
    @Transactional(readOnly = true)
    public String editarProduto(@PathVariable ("id") Long id, Model model ){
        Produto produto = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Produto inválido - id: " + id)
        );
        model.addAttribute("produto", produto);
        return "/produto/editar-produto";
    }

    //URL - localhost:8080/produtos/editar/1
    @PostMapping("/editar/{id}")
    @Transactional
    public String editarProduto(@PathVariable("id") Long id, @Valid Produto produto,
                                BindingResult result){
        if(result.hasErrors()){
            produto.setId(id);
            return "/produto/editar-produto";
        }
        repository.save(produto);
        return "redirect:/produtos/listar";
    }

    //URL - localhost:8080/produtos/deletar/1
    @GetMapping("/deletar/{id}")
    @Transactional
    public String deletarProduto(@PathVariable("id") Long id, Model model){
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("Produto inválido - id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (Exception e){
            throw new IllegalArgumentException("Produto inválido - id: " + id);
        }
        return "redirect:/produtos/listar";
    }
}










