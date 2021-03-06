package br.com.ceoestudos.ceogestao.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ceoestudos.ceogestao.dao.ContaDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.dao.TratamentoDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.HistoricoTratamento;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.Procedimento;
import br.com.ceoestudos.ceogestao.model.StatusTratamento;
import br.com.ceoestudos.ceogestao.model.TipoPessoa;
import br.com.ceoestudos.ceogestao.model.Tratamento;
import br.com.ceoestudos.ceogestao.model.TratamentoDente;
import br.com.ceoestudos.ceogestao.model.Turma;

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
    
    @Autowired
    private ContaDAO contaDAO;
    
    private final Logger LOG = Logger.getLogger(getClass());
    
    @RequestMapping("tratamentos")
    public String tratamentos(Model model){
        model.addAttribute("tratamentos", tDAO.listarTodos());
        return "tratamentos";
    }
    
    @RequestMapping("novoTratamento")
    public String novoTratamento(Model model){
        if(!existeProfessor()){
            model.addAttribute("ERROR_MESSAGE","Não há professores cadastrados no sistema. Não é possível criar um tratamento");
            model.addAttribute("tratamentos", tDAO.listarTodos());
            return "tratamentos";
        }
        model.addAttribute("tratamento",new Tratamento());
        return "formTratamento";
    }
    
    private boolean existeProfessor(){
        return pessoaDAO.listarPorCriteria(null, TipoPessoa.PROFESSOR).size()>0;
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
    public String editarTratamento(Model model,@RequestParam Long idTratamento){
        Tratamento t = tDAO.getFullById(idTratamento);
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
        Pessoa responsavel = pessoaDAO.getById(idResponsavel);
        if(tratamento.getId()!=null){
            loadTratamentoFromBD(tratamento);
            if(tratamento.getResponsaveis().contains(responsavel)){
                model.addAttribute("ERROR_MESSAGE",String.format("Aluno %s jÃ¡ adicionado",responsavel.getNome()));
                tratamento.setDentes(tratamento.getDentes());
                tratamento.setResponsaveis(tratamento.getResponsaveis());
                tratamento.setProcedimentosAvulsos(tratamento.getProcedimentosAvulsos());
                tratamento.setHistorico(tratamento.getHistorico());
                return "formTratamento";
            }
        } else{
            tDAO.adicionar(tratamento);
        }
        
        tratamento.addResponsavel(responsavel);
        tDAO.atualizar(tratamento);
        model.addAttribute("SUCCESS_MESSAGE","Aluno adicionado com sucesso");
        loadTratamentoFromBD(tratamento);
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
                loadTratamentoFromBD(tratamento);
                tDAO.atualizar(tratamento);
            }
            model.addAttribute("SUCCESS_MESSAGE","Tratamento salvo com sucesso");
        }
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(name="excluirTratamento", method=RequestMethod.POST)
    public String excluirTratamento(Model model, Long idTratamento){
        tDAO.excluir(tDAO.getById(idTratamento));
        model.addAttribute("SUCCESS_MESSAGE", "Tratamento excluido com sucesso.");
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
            loadTratamentoFromBD(tratamento);
            tratamento.addTratamentoDente(idDente, qtdProcedimento, procedimento);
            tDAO.atualizar(tratamento);
        }
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    @Transactional
    @RequestMapping(value = "adicionarProcedimentoAvulso", method = RequestMethod.POST)
    public String adicionarProcedimentoAvulso(Model model, 
                                        Tratamento tratamento, 
                                        Long idProcedimento,
                                        Integer qtdProcedimento){
        
        Procedimento procedimento = procedimentoDAO.getById(idProcedimento);
        
        if(tratamento.getId()==null){
            tDAO.adicionar(tratamento);
        } else {
            loadTratamentoFromBD(tratamento);
        }
        tratamento.addProcedimentoAvulso(procedimento, qtdProcedimento);
        tDAO.atualizar(tratamento);
        model.addAttribute("SUCCESS_MESSAGE","Procedimento adicionado com sucesso");
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "removerProcedimento", method = RequestMethod.POST)
    public String removerProcedimento(Model model,
                                      Tratamento tratamento,
                                      Long idTratamentoDente){
        TratamentoDente td = new TratamentoDente(idTratamentoDente);
        loadTratamentoFromBD(tratamento);
        tratamento.removeTratamento(td);
        tDAO.excluirTratamentoDente(td);
        model.addAttribute("tratamento",tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "removerResponsavel", method = RequestMethod.POST)
    public String removerResponsavel(Model model,Tratamento tratamento, Long idResponsavel){
        loadTratamentoFromBD(tratamento);
        tratamento.removeResponsavel(pessoaDAO.getById(idResponsavel));
        tDAO.atualizar(tratamento);
        model.addAttribute("SUCCESS_MESSAGE", "Responsável removido com sucesso");
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "adicionarHistorico", method=RequestMethod.POST)
    public String adicionarHistorico(Model model, 
            Tratamento tratamento, String dataHistorico, String descricaoHistorico,
            Long idProfessor){
        
        if(tratamento.getId()==null){
            tDAO.adicionar(tratamento);
        } else {
            loadTratamentoFromBD(tratamento);
        }
        Pessoa professor = pessoaDAO.getById(idProfessor);
        Date dt;
        try {
            dt = new SimpleDateFormat("dd/MM/yyyy").parse(dataHistorico);
        } catch (ParseException ex) {          
            model.addAttribute("ERROR_MESSAGE","Formato de data inválida");
            model.addAttribute("tratamento", tratamento);
            return "formTratamento";
        }
        
        tratamento.adicionarHistorico(new HistoricoTratamento(dt, descricaoHistorico, professor));
        tDAO.atualizar(tratamento);
        model.addAttribute("SUCCESS_MESSAGE", "Histórico adicionado com sucesso");
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    @RequestMapping(value="removerTratamentoHistorico", method=RequestMethod.POST)
    @Transactional
    public String removerHistorico(Model model, Long idTratamentoHistorico, Tratamento tratamento){
        tDAO.excluirHistoricoTratamento(idTratamentoHistorico);
        model.addAttribute("SUCCESS_MESSAGE", "Linha de histórico removida com sucesso");
        model.addAttribute("tratamento", tDAO.getFullById(tratamento.getId()));
        return "formTratamento";
    }
    
    private void loadTratamentoFromBD(Tratamento tratamento){
       if(tratamento.getId()!=null){
            Tratamento tratamentoBD = tDAO.getFullById(tratamento.getId());
            tratamento.setDentes(tratamentoBD.getDentes());
            tratamento.setHistorico(tratamentoBD.getHistorico());
            tratamento.setResponsaveis(tratamentoBD.getResponsaveis());
            tratamento.setProcedimentosAvulsos(tratamentoBD.getProcedimentosAvulsos());
        } else {
            tDAO.adicionar(tratamento);
        }
    }
    
    @Transactional
    @RequestMapping(value = "aplicarTaxa", method=RequestMethod.POST)
    public String aplicarTaxa(Model model, Tratamento tratamento){
        loadTratamentoFromBD(tratamento);
        tDAO.atualizar(tratamento);
        model.addAttribute("SUCCESS_MESSAGE","Taxa aplicada com sucesso");
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    @Transactional
    @RequestMapping(value = "aprovarOrcamento", method=RequestMethod.POST)
    public String aprovarOrcamento(Model model, Tratamento tratamento){
        loadTratamentoFromBD(tratamento);
        tratamento.setStatus(StatusTratamento.APROVADO);
        adicionarHistoricoAprovacao(tratamento);
        tDAO.atualizar(tratamento);
        Conta conta = Conta.createContaFromTratamento(tratamento);
        contaDAO.adicionar(conta);
        model.addAttribute("SUCCESS_MESSAGE","Orçamento aprovado com sucesso");
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    
    @Transactional
    @RequestMapping(value = "cancelarOrcamento", method=RequestMethod.POST)
    public String cancelarOrcamento(Model model, Tratamento tratamento){
        loadTratamentoFromBD(tratamento);
        tratamento.setStatus(StatusTratamento.CANCELADO);
        adicionarHistoricoAprovacao(tratamento);
        tDAO.atualizar(tratamento);
        model.addAttribute("SUCCESS_MESSAGE","Orçamento cancelado com sucesso");
        model.addAttribute("tratamento", tratamento);
        return "formTratamento";
    }
    
    private void adicionarHistoricoAprovacao(Tratamento tratamento){
        String descricao = String.format("Orçamento %s", tratamento.getStatus().toString());
        HistoricoTratamento ht = new HistoricoTratamento(new Date(), descricao);
        tratamento.adicionarHistorico(ht);
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Pessoa.class, "paciente", new PessoaPropertyEditor(pessoaDAO));
        
    }
    
}
