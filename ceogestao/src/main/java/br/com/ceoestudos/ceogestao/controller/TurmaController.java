package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.CursoDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Curso;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.SituacaoTurma;
import br.com.ceoestudos.ceogestao.model.Turma;
import br.com.ceoestudos.ceogestao.util.Util;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author amhfilho
 */
@Controller
public class TurmaController {

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private CursoDAO cursoDAO;
    
    @Autowired
    private PessoaDAO pessoaDAO;
    
    private Logger LOG = Logger.getLogger(getClass());

    @RequestMapping("turmas")
    public String listarTurmas(Model model) {
        List<Turma> turmas = turmaDAO.listarTodos();
        model.addAttribute("turmas", turmas);
        return "turmas";
    }

    @RequestMapping(value = "novaTurma", method = RequestMethod.POST)
     public String novaTurma(Model model) {
        model.addAttribute("turma", new Turma());
        //model.addAttribute("mapCursos", getMapCursos());
        return "formTurma";
    }

    @Transactional
    @RequestMapping("excluirTurma")
    public String excluirTurma(@RequestParam String id, Model model) {
        return "redirect:turmas.html";
    }

    @ModelAttribute("mapCursos")
    public Map getMapCursos() {
        Map<Long, String> mapCursos = new LinkedHashMap<Long, String>();
        List<Curso> cursos = cursoDAO.listarTodos();
        for (Curso curso : cursos) {
            mapCursos.put(curso.getId(), curso.getNome());
        }

        return mapCursos;
    }

    @Transactional
    @RequestMapping(value = "salvarTurma", method = RequestMethod.POST)
    public String salvarTurma(@Valid Turma turma, BindingResult result, Model model) {
        LOG.info(turma);
        if (!result.hasErrors()) {
            try {
                turmaDAO.adicionar(turma);
                
                model.addAttribute("SUCCESS_MESSAGE", "Turma gravada com sucesso");
                
            } catch (RuntimeException e){
                LOG.error(new Util().toString(e));
                model.addAttribute("ERROR_MESSAGE","Erro ao salvar a turma: "+e.getMessage());
            }
        } else {
            LOG.warn("Erro de validação de formulário: formTurma");
        }
        model.addAttribute("turma", turma);

        return "formTurma";
    }
    
    @RequestMapping("editarTurma")
    public String editarTurma(@RequestParam String id,Model model){
        try {
            Long longId = new Long(id);
            Turma turma = turmaDAO.getById(longId);
            LOG.debug("editarTurma: "+turma);
            model.addAttribute("turma",turma);
            return "formTurma";
        } catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE",e.getMessage());
            return "turmas";
        } 
    }

    @ModelAttribute("situacoesTurma")
    public  Map situacoesTurma() {
        Map map = new HashMap<String,String>();
        SituacaoTurma[] vector = SituacaoTurma.values();
        for(SituacaoTurma st:vector){
            map.put(st.name(), st.toString());
        }
        return map;
    }
    
    @RequestMapping("procurarAluno")
    public void procurarAluno(String nome, HttpServletResponse response){
        LOG.debug("procurarAluno: "+nome);
        List<Pessoa> alunos = pessoaDAO.listarPorNome(nome);
        response.setStatus(200);
    }

}
