package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.CursoDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Curso;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.SituacaoTurma;
import br.com.ceoestudos.ceogestao.model.Turma;
import br.com.ceoestudos.ceogestao.util.Util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
    
    @ModelAttribute("todosOsCursos")
    public List<Curso> getCursos(){
        return cursoDAO.listarTodos();
    }
    
    @ModelAttribute("professores")
    public Map<Long,String> getProfessores(){
        Map<Long,String> map = new HashMap<Long, String>();
        List<Pessoa> professores = pessoaDAO.listarProfessores();
        for(Pessoa professor:professores){
            map.put(professor.getIdentificador(), professor.getNome());
        }
        return map;
        
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
        try {
            Long longId = new Long(id);
            turmaDAO.excluir(longId);
            model.addAttribute("SUCCESS_MESSAGE", "Turma excluída com sucesso");

        } catch (RuntimeException e) {
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE", "Erro ao excluir a turma");
        }
        return "redirect:turmas.html";
    }

    @ModelAttribute("mapCursos")
    public Map getMapCursos() {
        Map<Long, String> mapCursos = new LinkedHashMap<Long, String>();
        List<Curso> cursos = cursoDAO.listarTodos();
        for (Curso curso : cursos) {
            mapCursos.put(curso.getId(), 
                          curso.getNome() + " - " + curso.getCargaHoraria() + " horas" );
        }
        return mapCursos;
    }
    
    @Transactional
    @RequestMapping(value = "adicionarTurma", method = RequestMethod.POST)
    public String adicionarTurma(@Valid Turma turma, BindingResult result, Model model) {
        LOG.info("adicionarTurma: " + new SimpleDateFormat("HH:mm").format(turma.getHoraInicio()) + " - " + new SimpleDateFormat("HH:mm").format(turma.getHoraFim()));
        if (!result.hasErrors()) {
            try {
                if (turma.getId() == null) {
                    turmaDAO.adicionar(turma);
                }
                model.addAttribute("SUCCESS_MESSAGE", "Turma gravada com sucesso");

            } catch (RuntimeException e) {
                LOG.error(new Util().toString(e));
                model.addAttribute("ERROR_MESSAGE", "Erro ao adicionar a turma: " + e.getMessage());
            }
        } else {
            LOG.warn("Erro de validação de formulário: formTurma");
        }
        model.addAttribute("turma", turma);

        return "formTurma";
    }

    @Transactional
    @RequestMapping(value = "atualizarTurma", method = RequestMethod.POST)
    public String atualizarTurma(@Valid Turma turma, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                //Lendo a turma gravada no banco de dados
                Turma turmaBD = getTurmaBD(turma);
                LOG.debug("atualizarTurma: " + turmaBD);
                turmaDAO.atualizar(turmaBD);
                model.addAttribute("turma",turmaBD);
                model.addAttribute("SUCCESS_MESSAGE", "Turma atualizada com sucesso");
                return "formTurma";

            } catch (RuntimeException e) {
                LOG.error(new Util().toString(e));
                model.addAttribute("ERROR_MESSAGE", "Ocorreu um erro ao atualizar a turma: " + e.getMessage());
                
            }    
        }
        model.addAttribute("turma",turma);
        
        return "formTurma";
    }

    private Turma getTurmaBD(Turma turmaWeb) {
        //Lendo a turma gravada no banco de dados
        Turma turmaBD = turmaDAO.getById(turmaWeb.getId());
        //Editando as propriedades simples
        turmaBD.setCurso(cursoDAO.getById(turmaWeb.getCurso().getId()));
        turmaBD.setDataInicio(turmaWeb.getDataInicio());
        turmaBD.setDataFim(turmaWeb.getDataFim());
        turmaBD.setHoraInicio(turmaWeb.getHoraInicio());
        turmaBD.setHoraFim(turmaWeb.getHoraFim());
        turmaBD.setDiasDaSemana(turmaWeb.getDiasDaSemana());
        turmaBD.setObservacoes(turmaWeb.getObservacoes());
        turmaBD.setQuorumMinimo(turmaWeb.getQuorumMinimo());
        turmaBD.setSala(turmaWeb.getSala());
        turmaBD.setSituacao(turmaWeb.getSituacao());
        return turmaBD;

    }

    @RequestMapping("editarTurma")
    public String editarTurma(@RequestParam String id, Model model) {
        try {
            Long longId = new Long(id);
            Turma turma = turmaDAO.getById(longId);
            LOG.debug("editarTurma: " + turma);
            model.addAttribute("turma", turma);
            return "formTurma";
        } catch (RuntimeException e) {
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE", e.getMessage());
            return "turmas";
        }
    }

    @ModelAttribute("situacoesTurma")
    public Map situacoesTurma() {
        Map map = new HashMap<String, String>();
        SituacaoTurma[] vector = SituacaoTurma.values();
        for (SituacaoTurma st : vector) {
            map.put(st.name(), st.toString());
        }
        return map;
    }

    @RequestMapping("procurarAluno")
    public String procurarAluno(String nome, HttpServletResponse response, Model model) {
        LOG.debug("procurarAluno: " + nome);
        List<Pessoa> alunos = pessoaDAO.listarAlunosPorNome(nome);
        model.addAttribute("listaAlunosBusca", alunos);
        response.setStatus(200);
        return "formTurmaListaAlunos";
    }

    @Transactional
    @RequestMapping(value = "adicionarAluno", method = RequestMethod.POST)
    public String adicionarAluno(@ModelAttribute Turma turma, @RequestParam String alunoId, Model model) {
        try{
            Pessoa aluno = getAlunoBD(alunoId);
            Turma turmaBD = getTurmaBD(turma);
            turmaBD.adicionarAluno(aluno);
            model.addAttribute("SUCCESS_MESSAGE","Aluno adicionado com sucesso");
            model.addAttribute("turma", turmaBD);
        }catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE", "Erro ao adicionar aluno: " + e.getMessage());
            return editarTurma(turma.getId().toString(), model);
        }

        return "formTurma";
    }
    @Transactional
    @RequestMapping(value = "excluirAluno", method = RequestMethod.POST)
    public String excluirAluno(@ModelAttribute Turma turma, @RequestParam String excluirAlunoId, Model model){
        try{
            Pessoa aluno = getAlunoBD(excluirAlunoId);
            Turma turmaBD = getTurmaBD(turma);
            turmaBD.removerAluno(aluno);
            model.addAttribute("SUCCESS_MESSAGE","Aluno removido com sucesso");
            model.addAttribute("turma", turmaBD);
        }catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE", "Erro ao remover aluno: " + e.getMessage());
            return editarTurma(turma.getId().toString(), model);
        }
        return "formTurma";
    }
    
    
    private Pessoa getAlunoBD(String alunoId){
        Long idAluno = new Long(alunoId);
        Pessoa aluno = pessoaDAO.getById(idAluno);
        LOG.debug("Encontrado aluno: " + aluno);
        return aluno;
    }
    
    @Autowired
    private TurmaValidator turmaValidator;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(turmaValidator);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        hourFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "horaInicio", new CustomDateEditor(hourFormat, false));
        binder.registerCustomEditor(Date.class, "horaFim", new CustomDateEditor(hourFormat, false));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    

}
