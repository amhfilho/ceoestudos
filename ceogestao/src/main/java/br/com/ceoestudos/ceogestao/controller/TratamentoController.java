package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.dao.TratamentoDAO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TratamentoController {
    
    @Autowired
    private TurmaDAO turmaDAO;
    
    @Autowired
    private ProcedimentoDAO procedimentoDAO;
    
    @Autowired
    private PessoaDAO pessoaDAO;
    
    @Autowired
    private TratamentoDAO tDAO;
    
    private Logger LOG = Logger.getLogger(getClass());
    
    @RequestMapping("tratamentos")
    public String tratamentos(Model model){
        model.addAttribute("tratamentos", tDAO.listarTodos());
        return "tratamentos";
    }
    
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
    
    @RequestMapping("editarTratamento")
    public String editarTratamento(Model model,
                                   @RequestParam Long idTratamento){
        Tratamento t = tDAO.getById(idTratamento);
        LOG.debug(t);
        model.addAttribute("tratamento",t);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "salvarTratamento", method = RequestMethod.POST)
    public String salvarTratamento(Model model, @Valid Tratamento tratamento, BindingResult result){
        if(!result.hasErrors()){
            if(tratamento.getId()==null){
                tDAO.adicionar(tratamento);
            } else {
                tratamento.setDentes(tDAO.getById(tratamento.getId()).getDentes());
                tDAO.atualizar(tratamento);
            }
            model.addAttribute("SUCCESS_MESSAGE","Tratamento criado com sucesso");
        }
        
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "adicionarProcedimento", method = RequestMethod.POST)
    public String adicionarProcedimento(Model model, 
                                        Tratamento tratamento, 
                                        Long idProcedimento,
                                        Integer qtdProcedimento,
                                        Integer idDente
                                        //BindingResult result
    ) {
//        if(result.hasErrors()){
//            return "formTratamento";
//        }
        Procedimento procedimento = procedimentoDAO.getById(new Long(idProcedimento));
        
        
        LOG.debug(procedimento);
        LOG.debug(tratamento);
        LOG.debug(qtdProcedimento);
        LOG.debug(idDente);
        if(tratamento.getId()==null){
            tratamento.addTratamentoDente(idDente, new Integer(qtdProcedimento), procedimento);
            tDAO.adicionar(tratamento);
        } else {
            tratamento.setDentes(tDAO.getById(tratamento.getId()).getDentes());
            tratamento.addTratamentoDente(idDente, new Integer(qtdProcedimento), procedimento);
            tDAO.atualizar(tratamento);
        }
             
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "removerProcedimento", method = RequestMethod.POST)
    public String removerProcedimento(Model model,
                                      Tratamento tratamento,
                                      Integer idDente,
                                      Long idProcedimento){
        
        tratamento.setDentes(tDAO.getById(tratamento.getId()).getDentes());
        tratamento.removeTratamentoDente(idDente,idProcedimento);
        tDAO.atualizar(tratamento);
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Pessoa.class, "paciente", new PessoaPropertyEditor(pessoaDAO));
        
    }
    
}
