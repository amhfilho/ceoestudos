package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Tratamento;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TratamentoController {
    
    @Autowired
    private TurmaDAO turmaDAO;
    
    
    
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
    
}
