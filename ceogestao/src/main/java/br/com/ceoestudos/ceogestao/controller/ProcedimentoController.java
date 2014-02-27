package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.model.Procedimento;
import br.com.ceoestudos.ceogestao.util.Util;
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

@Controller
public class ProcedimentoController {

    private Logger LOG = Logger.getLogger(getClass());

    @Autowired
    private ProcedimentoDAO procedimentoDAO;

    @RequestMapping("procedimentos")
    public String listarProcedimentosPorNomeETipo(Model model, String pesquisa, String filtroTipo) {
        List<Procedimento> lista = procedimentoDAO.listar(pesquisa, filtroTipo);
        model.addAttribute("procedimentos", lista);
        model.addAttribute("filtroTipo", filtroTipo);
        model.addAttribute("pesquisa", pesquisa);
        return "procedimentos";
    }

    @ModelAttribute("tiposProcedimentos")
    public List<String> getTiposProcedimentos() {
        return procedimentoDAO.getTiposProcedimentos();

    }

    @RequestMapping(value = "novoProcedimento", method = RequestMethod.POST)
    public String novoProcedimento(Model model) {
        model.addAttribute("procedimento", new Procedimento());
        return "formProcedimento";
    }
    
    @RequestMapping(value = "editarProcedimento")  
    public String editarProcedimento(@RequestParam Long id, Model model){
        try {
            Procedimento p = procedimentoDAO.getById(id);
            model.addAttribute("procedimento",p);
            return "formProcedimento";
        } catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE","Erro ao exibir os detalhes do procedimento: "+e.getMessage());
            return "procedimentos";
        }
    }

    @Transactional
    @RequestMapping("excluirProcedimento")
    public String excluirProcedimento(@RequestParam Long id, Model model) {

        try {
            procedimentoDAO.remover(id);
            model.addAttribute("SUCCESS_MESSAGE", "Procedimento excluido com sucesso");
            model.addAttribute("procedimento", procedimentoDAO.getById(id));
            return "redirect:procedimentos.html";

        } catch (RuntimeException ex) {
            LOG.error(new Util().toString(ex));
            model.addAttribute("procedimento", procedimentoDAO.getById(id));
            model.addAttribute("ERROR_MESSAGE", "Erro ao excluir o procedimento: "+ex.getMessage());
            return "redirect:procedimentos.html";

        }
    }
    
    @Transactional
    @RequestMapping(value = "salvarProcedimento", method = RequestMethod.POST)
    public String salvarProcedimento(Model model, @Valid Procedimento procedimento, BindingResult result, String textTipo){
        try {
            if(!result.hasErrors()){
                if(textTipo!=null && !textTipo.equals("")){
                    procedimento.setTipo(textTipo);
                }
                String success;
                if(procedimento.getId()==null){
                    procedimentoDAO.adicionar(procedimento);
                    success = "Procedimento criado com sucesso";
                } else {
                    procedimentoDAO.atualizar(procedimento);
                    success = "Procedimento atualizado com sucesso";
                }
                model.addAttribute("tiposProcedimentos",getTiposProcedimentos());
                model.addAttribute("procedimento",procedimento);
                model.addAttribute("SUCCESS_MESSAGE",success);
            }
        } catch (RuntimeException e){
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE",e.getMessage());
        }
        
        return "formProcedimento";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
