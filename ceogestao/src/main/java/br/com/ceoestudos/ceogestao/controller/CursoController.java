/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.CursoDAO;
import br.com.ceoestudos.ceogestao.model.Curso;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author amhfilho
 */
@Controller
public class CursoController {

    private Logger LOG = Logger.getLogger(getClass());

    @Autowired
    private CursoDAO cursoDAO;

    @RequestMapping("cursos")
    public String listarCursos(Model model) {
        List<Curso> cursos = cursoDAO.listarTodos();
        model.addAttribute("cursos", cursos);
        return "cursos";
    }

    @RequestMapping(value = "novoCurso", method = RequestMethod.POST)
    public String novoCurso(Model model) {
        model.addAttribute("curso", new Curso());
        return "formCurso";
    }

    @Transactional
    @RequestMapping(value = "salvarCurso", method = RequestMethod.POST)
    public String salvarCurso(@Valid Curso curso, BindingResult result, Model model) {
        LOG.debug(curso);
        try {
            if (result.hasErrors()) {
                LOG.info("Erro de validação de formulário");
            } else {
                if (curso.getId() == null) {
                    cursoDAO.adicionar(curso);
                } else {
                    cursoDAO.atualizar(curso);
                }
                model.addAttribute("SUCCESS_MESSAGE", "Curso gravado com sucesso!");
            }
        } catch (RuntimeException e) {
            LOG.error(e);
            model.addAttribute("ERROR_MESSAGE", "Ocorreu um erro ao salvar o Curso: " + e.getMessage());
        }
        model.addAttribute("curso", curso);
        return "formCurso";
    }
    
    @Transactional
    @RequestMapping("excluirCurso")
    public String excluirCurso(@RequestParam String id, Model model){
        try {
            cursoDAO.excluir(new Long(id));
            model.addAttribute("SUCCESS_MESSAGE", "Curso excluido com sucesso!");
            
        } catch (RuntimeException e){
            LOG.error(e);
            model.addAttribute("ERROR_MESSAGE", "Ocorreu um erro ao excluir o Curso: " + e.getMessage());
            return "formCurso";
        }
        return "redirect:cursos.html";
    }

    @RequestMapping("editarCurso")
    public String editarCurso(@RequestParam String id, Model model) {
        try {
            Long longId = new Long(id);
            Curso curso = cursoDAO.getById(longId);
            model.addAttribute("curso", curso);
        } catch (RuntimeException e) {
            LOG.error(e);
            model.addAttribute("ERROR_MESSAGE", e.getMessage());
            return "cursos";
        }
        return "formCurso";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        df.setGroupingUsed(false);
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(32);
        df.setMaximumIntegerDigits(32);
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, df, true));
    }
}
