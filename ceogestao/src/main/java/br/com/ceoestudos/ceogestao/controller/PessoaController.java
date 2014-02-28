package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author amhfilho
 */
@Controller

public class PessoaController {

    private final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    private PessoaDAO pessoaDAO;

    @RequestMapping(value = "pessoas")
    public String pesquisarPessoas(Model model, String pesquisa, String resultado) {
        LOGGER.debug("String de Pesquisa: " + pesquisa);
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa);
        model.addAttribute("pessoas", lista);
        if(resultado!=null && !resultado.equals("")){
            return resultado;
        }
        return "pessoas";
    }

    @RequestMapping(value = "novaPessoa", method = RequestMethod.POST)
    public String novaPessoa(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        return "formPessoa";
    }

    @Transactional
    @RequestMapping(value = "salvarPessoa", method = RequestMethod.POST)
    public String salvarPessoa(@Valid Pessoa pessoa, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                LOGGER.info("Erro de validação do formulário");
                model.addAttribute("pessoa", pessoa);
                return "formPessoa";
            }

            System.out.println("salvarPessoa:\n" + pessoa);
            if (pessoa.getIdentificador() == null || pessoa.getIdentificador() == 0) {
                pessoaDAO.adicionar(pessoa);
            } else {
                pessoaDAO.atualizar(pessoa);
            }
            model.addAttribute("SUCCESS_MESSAGE", "Pessoa gravada com sucesso!");

        } catch (RuntimeException e) {
            model.addAttribute("ERROR_MESSAGE", e.getMessage());
            LOGGER.error(e);
        }
        model.addAttribute("pessoa", pessoa);
        return "formPessoa";
    }

    @RequestMapping("editarPessoa")
    public String editarPessoa(@RequestParam String id, Model model) {
        try {
            Long identificador = new Long(id);
            Pessoa pessoa = pessoaDAO.getById(identificador);
            model.addAttribute("pessoa", pessoa);
            return "formPessoa";
        } catch (RuntimeException e) {
            LOGGER.error(e);
            model.addAttribute("ERROR_MESSAGE", e.getMessage());
            return "pessoas";
        }
    }

    @Transactional
    @RequestMapping("excluirPessoa")
    public String excluirPessoa(@RequestParam String id, Model model) {
        Long identificador = new Long(id);
        try {    
            pessoaDAO.excluir(identificador);
            
        } catch (RuntimeException e) {
            LOGGER.error(e);
            model.addAttribute("ERROR_MESSAGE", e.getMessage());
            model.addAttribute("pessoa",pessoaDAO.getById(identificador));
            return "formPessoa";
        }
        return "redirect:pessoas.html";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
