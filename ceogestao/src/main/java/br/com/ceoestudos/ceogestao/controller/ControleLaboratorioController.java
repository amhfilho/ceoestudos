package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.dao.ServicoLaboratorioDAO;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.Procedimento;
import br.com.ceoestudos.ceogestao.model.ServicoLaboratorio;
import br.com.ceoestudos.ceogestao.util.Util;
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
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @RequestMapping("controleLaboratorio")
    public String listarServicosLaboratorio(Model model){
        model.addAttribute("servicosLaboratorio",sDAO.listarTodos());
        return "controleLaboratorio";
    }
    
    @RequestMapping(value="novoServicoLaboratorio")
    public String novoServicoLaboratorio(Model model){
        model.addAttribute("servicoLaboratorio",new ServicoLaboratorio());
        return "formServicoLaboratorio";
    }
    
    @RequestMapping(value = "salvarServicoLaboratorio", method = RequestMethod.POST)
    public String salvarServicoLaboratorio(Model model, @Valid ServicoLaboratorio servico, BindingResult result){
        if(!result.hasErrors()){
            try {
                String success = "";
                if(servico.getId()==null){
                    sDAO.adicionar(servico);
                    success = "Serviço de Laboratório criado com sucesso";
                } else {
                    sDAO.atualizar(servico);
                    success = "Serviço de Laboratório atualizado com sucesso";
                }
                model.addAttribute("servicoLaboratorio",servico);
                model.addAttribute("SUCCESS_MESSAGE",success);
                
            } catch (RuntimeException e){
                LOG.error(new Util().toString(e));
                model.addAttribute("ERROR_MESSAGE","Erro ao salvar o serviço de laboratório: " + e.getMessage());
            }
        }
        return "formServicoLaboratorio";
    }
    
    @RequestMapping("editarServicoLaboratorio")
    public String editarServicoLaboratorio(Model model, @RequestParam Long id){
        try {
            ServicoLaboratorio servico = sDAO.getById(id);
            model.addAttribute("servicoLaboratorio",servico);
            return "formServicoLaboratorio";
            
        } catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE", "Erro ao exibir os detalhes do serviço: "+e.getMessage());
            return "controleLaboratorio";
        }
        
    }
    
    @RequestMapping("excluirServicoLaboratorio")
    public String excluirServicoLaboratorio(Model model, @RequestParam Long id){
        ServicoLaboratorio servico = null ;
        try {
            servico = sDAO.getById(id);
            sDAO.excluir(servico);
            model.addAttribute("SUCCESS_MESSAGE", "Serviço excluído com sucesso");
            return "redirect:controleLaboratorio.html";
            
        } catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE", "Erro ao excluir o serviço: "+e.getMessage());
            model.addAttribute("servicoLaboratorio",servico);
            return "formServicoLaboratorio";
        }
        
    }
    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Pessoa.class, "paciente", new PessoaPropertyEditor(pessoaDAO));
        binder.registerCustomEditor(Pessoa.class, "profissional", new PessoaPropertyEditor(pessoaDAO));
        binder.registerCustomEditor(Procedimento.class, "procedimento", new ProcedimentoPropertyEditor(procedimentoDAO));
    }
}
