package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.ServicoLaboratorioDAO;
import br.com.ceoestudos.ceogestao.model.ServicoLaboratorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author amhfilho
 */
@Controller
public class ControleLaboratorioController {
    
    @Autowired
    private ServicoLaboratorioDAO sDAO;
    
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
    
    
    
}
