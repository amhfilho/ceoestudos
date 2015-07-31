package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.dao.TratamentoDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.HistoricoTratamento;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.Procedimento;
import br.com.ceoestudos.ceogestao.model.Tratamento;
import br.com.ceoestudos.ceogestao.model.TratamentoDente;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
    
    private final Logger LOG = Logger.getLogger(getClass());
    
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
    public List<Turma> getTurmas(){
        
        return turmaDAO.listarTodos();
    }
    
    @ModelAttribute("professores")
    public List<Pessoa> getProfessores(){
        return pessoaDAO.listarProfessores();
    }
    
    @RequestMapping("editarTratamento")
    public String editarTratamento(Model model,
                                   @RequestParam Long idTratamento){
        Tratamento t = tDAO.getById(idTratamento);
        LOG.debug(idTratamento+","+t);
        model.addAttribute("tratamento",t);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value="adicionarResponsavel",method = RequestMethod.POST)
    public String adicionarResponsavel(Model model, 
                                       Long idResponsavel,
                                       Tratamento tratamento) {
        LOG.debug(idResponsavel);
        if(tratamento.getId()!=null){
            Tratamento tratamentoBD = tDAO.getById(tratamento.getId());
            tratamento.setDentes(tratamentoBD.getDentes());
            tratamento.setResponsaveis(tratamentoBD.getResponsaveis());
        } else{
            tDAO.adicionar(tratamento);
        }
        Pessoa responsavel = pessoaDAO.getById(idResponsavel);
        tratamento.addResponsavel(responsavel);
        tDAO.atualizar(tratamento);
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "salvarTratamento", method = RequestMethod.POST)
    public String salvarTratamento(Model model, @Valid Tratamento tratamento, BindingResult result){
        if(!result.hasErrors()){
            if(tratamento.getId()==null){
                tDAO.adicionar(tratamento);
            } else {
                tratamento = getTratamentoBD(tratamento);
                tDAO.atualizar(tratamento);
            }
            model.addAttribute("SUCCESS_MESSAGE","Tratamento salvo com sucesso");
        }
        
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping("excluirTratamento")
    public String excluirTratamento(Model model, Long id){
        tDAO.excluir(tDAO.getById(id));
        return "redirect:tratamentos.html";
    }
    
    @Transactional
    @RequestMapping(value = "adicionarProcedimento", method = RequestMethod.POST)
    public String adicionarProcedimento(Model model, 
                                        Tratamento tratamento, 
                                        Long idProcedimento,
                                        Integer qtdProcedimento,
                                        Integer idDente) {

        Procedimento procedimento = procedimentoDAO.getById(idProcedimento);
        
        if(tratamento.getId()==null){
            tratamento.addTratamentoDente(idDente, qtdProcedimento, procedimento);
            tDAO.adicionar(tratamento);
        } else {
            tratamento.setDentes(tDAO.getById(tratamento.getId()).getDentes());
            tratamento.addTratamentoDente(idDente, qtdProcedimento, procedimento);
            tDAO.atualizar(tratamento);
        }
             
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "removerProcedimento", method = RequestMethod.POST)
    public String removerProcedimento(Model model,
                                      Tratamento tratamento,
                                      Long idTratamentoDente){
        TratamentoDente td = new TratamentoDente(idTratamentoDente);
        tratamento = tDAO.getById(tratamento.getId());
        tratamento.removeTratamento(td);
        tDAO.excluirTratamentoDente(td);
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "adicionarHistorico", method=RequestMethod.POST)
    public String adicionarHistorico(Model model, 
            Tratamento tratamento, String dataHistorico, String descricaoHistorico,
            Long idProfessor){
        
        tratamento = getTratamentoBD(tratamento);
        Pessoa professor = pessoaDAO.getById(idProfessor);
        Date dt;
        try {
            dt = new SimpleDateFormat("dd/MM/yyyy").parse(dataHistorico);
        } catch (ParseException ex) {
            
            model.addAttribute("ERROR_MESSAGE","Formato de data inválida");
            model.addAttribute("tratamento", tratamento);
            return "formTratamento";
        }
        
        tratamento.adicionarHistorico(new HistoricoTratamento(dt, dataHistorico, professor));
        tDAO.atualizar(tratamento);
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    private Tratamento getTratamentoBD(Tratamento tratamento){
       if(tratamento.getId()!=null){
            Tratamento tratamentoBD = tDAO.getById(tratamento.getId());
            tratamento.setDentes(tratamentoBD.getDentes());
            tratamento.setHistorico(tratamentoBD.getHistorico());
            tratamento.setResponsaveis(tratamentoBD.getResponsaveis());
        } else {
            tDAO.adicionar(tratamento);
        }
       return tratamento;
    }
    
    @RequestMapping(value = "aplicarTaxa", method=RequestMethod.POST)
    public String aplicarTaxa(Model model, Tratamento tratamento){
        
        tratamento = getTratamentoBD(tratamento);
        
        model.addAttribute("SUCCESS_MESSAGE","Taxa aplicada com sucesso");
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Pessoa.class, "paciente", new PessoaPropertyEditor(pessoaDAO));
        
    }
    
}
