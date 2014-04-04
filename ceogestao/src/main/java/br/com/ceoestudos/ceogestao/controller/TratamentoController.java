package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.Procedimento;
import br.com.ceoestudos.ceogestao.model.Tratamento;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TratamentoController {
    
    @Autowired
    private TurmaDAO turmaDAO;
    
    @Autowired
    private ProcedimentoDAO procedimentoDAO;
    
    @Autowired
    private PessoaDAO pessoaDAO;
    
    private Logger LOG = Logger.getLogger(getClass());
    
    @RequestMapping("novoTratamento")
    public String novoTratamento(Model model){
        model.addAttribute("tratamento",new Tratamento());
        return "formTratamento";
    }
    
    @ModelAttribute("todasAsTurmas")
    public Map<Long,String> getTurmas(){
        Map<Long,String> map = new HashMap<Long,String>();
        List<Turma> turmas =  turmaDAO.listarTodos();
        for(Turma t:turmas){
            map.put(t.getId(), t.toString());
        }
        return map;
    }
    
    @RequestMapping(value = "salvarTratamento", method = RequestMethod.POST)
    public String salvarTratamento(Model model, @Valid Tratamento tratamento, BindingResult result){
        if(!result.hasErrors()){
            
            model.addAttribute("SUCCESS_MESSAGE","Tratamento criado com sucesso");
        }
        
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @RequestMapping(value = "adicionarProcedimento", method = RequestMethod.POST)
    public String adicionarProcedimento(Model model, 
                                        Tratamento tratamento, 
                                        Long idProcedimento, 
                                        Integer qtdProcedimento, 
                                        Integer idDente){
        Procedimento procedimento = procedimentoDAO.getById(idProcedimento);
        LOG.debug(procedimento);
        LOG.debug(tratamento);
        LOG.debug(qtdProcedimento);
        LOG.debug(idDente);
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Pessoa.class, "idPaciente", new PessoaPropertyEditor(pessoaDAO));
        //binder.registerCustomEditor(Pessoa.class, "profissional", new PessoaPropertyEditor(pessoaDAO));
        //binder.registerCustomEditor(Procedimento.class, "idProcedimento", new ProcedimentoPropertyEditor(procedimentoDAO));
    }
    
}
