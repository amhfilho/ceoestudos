package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.dao.ServicoLaboratorioDAO;
import br.com.ceoestudos.ceogestao.model.ServicoLaboratorio;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author amhfilho
 */
@Controller
public class ControleLaboratorioController {
    
    private Logger LOG = Logger.getLogger(getClass());
    
    @Autowired
    private ServicoLaboratorioDAO sDAO;
    
    @Autowired
    private PessoaDAO pessoaDAO;
    
    @Autowired
    private ProcedimentoDAO procedimentoDAO;
    
    @RequestMapping("controleLaboratorio.html")
    public String listarServicosLaboratorio(Model model){
        model.addAttribute("servicosLaboratorio",sDAO.listarTodos());
        return "controleLaboratorio";
    }
    
    @RequestMapping(value="novoServicoLaboratorio", method = RequestMethod.POST)
    public String novoServicoLaboratorio(Model model){
        model.addAttribute("servicoLaboratorio",new ServicoLaboratorio());
        return "formServicoLaboratorio";
    }
    
    @RequestMapping(value = "salvarServicoLaboratorio", method = RequestMethod.POST)
    public String salvarServicoLaboratorio(Model model, 
            @Valid ServicoLaboratorio servico, BindingResult result, 
            Long idPaciente, Long idProfissional,Long idProcedimento){
        if(!result.hasErrors()){
            try {
                servico.setNome(pessoaDAO.getById(idPaciente));
                servico.setProfissional(pessoaDAO.getById(idProfissional));
                servico.setProcedimento(procedimentoDAO.getById(idProcedimento));
                
                if(servico.getId()==null){
                    sDAO.adicionar(servico);
                } else {
                    sDAO.atualizar(servico);
                }
            } catch (RuntimeException e){
                
            }
        }
        return "formServicoLaboratorio";
    }
    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
