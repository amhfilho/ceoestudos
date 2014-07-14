package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.TipoPessoa;
import br.com.ceoestudos.ceogestao.util.Util;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private TipoPessoa tipoPessoa = TipoPessoa.PROFESSOR;

    @RequestMapping(value = "alunos")
    public String pesquisarAlunos(Model model, String pesquisa, String resultado) {
        tipoPessoa = TipoPessoa.ALUNO;
        model.addAttribute("tipoPessoa", "aluno");
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, tipoPessoa);
        model.addAttribute("pessoas", lista);
        if (resultado != null && !resultado.equals("")) {
            return resultado;
        }
        return "alunos";
    }

    @RequestMapping(value = "pacientes")
    public String pesquisarPacientes(Model model, String pesquisa, String resultado) {
        tipoPessoa = TipoPessoa.PACIENTE;
        model.addAttribute("tipoPessoa", "paciente");
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, tipoPessoa);
        model.addAttribute("pessoas", lista);
        if (resultado != null && !resultado.equals("")) {
            return resultado;
        }
        return "pacientes";
    }

    @RequestMapping(value = "professores")
    public String pesquisarProfessores(Model model, String pesquisa, String resultado) {
        tipoPessoa = TipoPessoa.PROFESSOR;
        model.addAttribute("tipoPessoa", "professor");
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, tipoPessoa);
        model.addAttribute("pessoas", lista);
        if (resultado != null && !resultado.equals("")) {
            return resultado;
        }
        return "professores";
    }

    @RequestMapping(value = "novoaluno")
    public String novoAluno(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        model.addAttribute("tipoPessoa", "ALUNO");
        return "formAluno";
    }

    @RequestMapping(value = "novopaciente")
    public String novoPaciente(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        model.addAttribute("tipoPessoa", "PACIENTE");
        return "formPaciente";
    }

    @RequestMapping(value = "novoprofessor")
    public String novoProfessor(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        model.addAttribute("tipoPessoa", "PROFESSOR");
        return "formProfessor";
    }

    @Transactional
    @RequestMapping(value = "salvarPessoa", method = RequestMethod.POST)
    public String salvarPessoa(@Valid Pessoa pessoa, BindingResult result, Model model, String tipoPessoa) {

        if (!result.hasErrors()) {
            try {
                if (pessoa.getCro() == null) {
                    pessoa.setUfCro(null);
                }
                pessoa.setTipo(TipoPessoa.valueOf(tipoPessoa));
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
        }
        String retorno = "";
        model.addAttribute("pessoa", pessoa);
        model.addAttribute("tipoPessoa", tipoPessoa);
        if (tipoPessoa.equals("ALUNO")) {
            retorno = "formAluno";
        } else if (tipoPessoa.equals("PACIENTE")) {
            retorno = "formPaciente";
        } else if (tipoPessoa.equals("PROFESSOR")) {
            retorno = "formProfessor";
        }
        return retorno;
    }

    @RequestMapping("editarPessoa")
    public String editarPessoa(@RequestParam String id, Model model) {
        try {
            Long identificador = new Long(id);
            Pessoa pessoa = pessoaDAO.getById(identificador);
            model.addAttribute("pessoa", pessoa);
            model.addAttribute("tipoPessoa",pessoa.getTipo().name());
            String retorno = "";
            if (pessoa.getTipo().name().equals("ALUNO")) {
                retorno = "formAluno";
            } else if (pessoa.getTipo().name().equals("PACIENTE")) {
                retorno = "formPaciente";
            } else if (pessoa.getTipo().name().equals("PROFESSOR")) {
                retorno = "formProfessor";
            }
            return retorno;
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
            model.addAttribute("pessoa", pessoaDAO.getById(identificador));
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
