package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.CirurgiaDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Cirurgia;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.Turma;
import br.com.ceoestudos.ceogestao.util.Util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class CirurgiaController {
    
    @Autowired
    private CirurgiaDAO cirurgiaDAO;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private TurmaDAO turmaDAO;
    
    private Logger LOG = Logger.getLogger(getClass());
    
    @RequestMapping("cirurgias")
    public String cirurgias(Model model) {
        model.addAttribute("cirurgias", cirurgiaDAO.listarTodos());
        return "cirurgias";
    }
    
    @ModelAttribute("todasAsTurmas")
    public List<Turma> getTurmas(){   
        return turmaDAO.listarTodos();
    }
    
    @RequestMapping("novaCirurgia")
    public String novaCirurgia(Model model) {
        model.addAttribute("cirurgia", new Cirurgia());
        return "formCirurgia";
    }
    
    @ModelAttribute("professores")
    public List<Pessoa> getProfessores() {
        return pessoaDAO.listarProfessores();
    }
    
    @RequestMapping("editarCirurgia")
    public String editarCirurgia(Model model, Long id){
        Cirurgia c = cirurgiaDAO.getById(id);
        model.addAttribute("cirurgia",c);
        return "formCirurgia";
    }
    
    @Transactional
    @RequestMapping(value="salvarCirurgia",method=RequestMethod.POST)
    public String salvarCirurgia(Model model, @Valid Cirurgia cirurgia, BindingResult result){
        try {
            if(!result.hasErrors()){
                if(cirurgia.getId()==null){
                    cirurgiaDAO.adicionar(cirurgia);
                } else {
                    cirurgia.setHistorico(cirurgiaDAO.getById(cirurgia.getId()).getHistorico());
                    cirurgiaDAO.atualizar(cirurgia);
                }
                model.addAttribute("SUCCESS_MESSAGE","Cirurgia salva com sucesso");
            }
            
        } catch (RuntimeException e){
            model.addAttribute("ERROR_MESSAGE", "Houve um erro ao salvar a cirurgia");
            LOG.error(new Util().toString(e));
        }
        
        return "formCirurgia";
    }
    
    @Transactional
    @RequestMapping(value = "atualizarHistoricoCirurgia", method = RequestMethod.POST)
    public String atualizarHistorico(Model model, Cirurgia cirurgia,
            String dataHistorico, String descricaoHistorico, Long idProfessor, String removerHistorico) {
        try {
            Date data = null;
            getCirurgiaBD(cirurgia);
            try {
                data = new SimpleDateFormat("dd/MM/yyyy").parse(dataHistorico);
                
            } catch (ParseException ex) {
                model.addAttribute("ERROR_MESSAGE", "Data de histório inválida");
                model.addAttribute("cirurgia", cirurgia);
                return "formCirurgia";
            }
            String successMessage = "";
            if(removerHistorico!=null && removerHistorico.equals("true")){
                cirurgia.removerHistorico(data, descricaoHistorico, pessoaDAO.getById(idProfessor));
                successMessage = "Cirurgia atualizada e histórico removido";
            } else{
                cirurgia.adicionarHistorico(data, descricaoHistorico);
                successMessage = "Cirurgia atualizada e histórico adicionado";
            }
            
            cirurgiaDAO.atualizar(cirurgia);
            model.addAttribute("SUCCESS_MESSAGE", successMessage);
            
        } catch (RuntimeException e) {
            model.addAttribute("ERROR_MESSAGE", "Houve um erro ao atualizar o histórico");
            LOG.error(new Util().toString(e));
        }
        model.addAttribute("cirurgia", cirurgia);
        return "formCirurgia";
    }
    @Transactional
    @RequestMapping("excluirCirurgia")
    public String excluirCirurgia(Model model, Long id){
        try{
            Cirurgia c = cirurgiaDAO.getById(id);
            model.addAttribute("cirurgia",c);
            cirurgiaDAO.excluir(c);
            model.addAttribute("SUCCESS_MESSAGE","Cirurgia excluída com sucesso");
        
        } catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE","Erro ao tentar excluir uma cirurgia: "+e.getMessage());
            
        }
        return "redirect:cirurgias.html";
    }
        
    private void getCirurgiaBD(Cirurgia cirurgia) {
        if (cirurgia.getId() != null) {
            Cirurgia c = cirurgiaDAO.getById(cirurgia.getId());
            cirurgia.setAnsiolitico(c.getAnsiolitico());
            cirurgia.setAntibiotico(c.getAntibiotico());
            cirurgia.setAntiinflamatorio(c.getAntiinflamatorio());
            cirurgia.setExameRadiografico(c.getExameRadiografico());
            cirurgia.setHistorico(c.getHistorico());
            cirurgia.setPaciente(c.getPaciente());
            cirurgia.setPlanejamento(c.getPlanejamento());
        } else {
            cirurgiaDAO.adicionar(cirurgia);
        }
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Pessoa.class, "paciente", new PessoaPropertyEditor(pessoaDAO));
        
    }
}
