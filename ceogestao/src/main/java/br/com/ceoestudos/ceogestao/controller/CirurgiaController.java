package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.CirurgiaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CirurgiaController {
    
    @Autowired
    private CirurgiaDAO cirurgiaDAO;
    
    @RequestMapping("cirurgias")
    public String cirurgias(Model model){
        model.addAttribute("cirurgias",cirurgiaDAO.listarTodos());
        return "cirurgias";
    }
    
}
