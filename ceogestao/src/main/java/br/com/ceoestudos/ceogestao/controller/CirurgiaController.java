package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.CirurgiaDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.model.Cirurgia;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CirurgiaController {
    
    @Autowired
    private CirurgiaDAO cirurgiaDAO;
    @Autowired
    private PessoaDAO pessoaDAO;
    
    @RequestMapping("cirurgias")
    public String cirurgias(Model model) {
        model.addAttribute("cirurgias", cirurgiaDAO.listarTodos());
        return "cirurgias";
    }
    
    @RequestMapping("novaCirurgia")
    public String novaCirurgia(Model model) {
        model.addAttribute("cirurgia", new Cirurgia());
        return "formCirurgia";
    }
    
    @ModelAttribute("professores")
    public List<Pessoa> getProfessores() {
        return pessoaDAO.listarProfessores();
    }
    
    @RequestMapping(value = "adicionarHistoricoCirurgia", method = RequestMethod.POST)
    public String adicionarHistorico(Model model, Cirurgia cirurgia,
            String dataHistorico, String descricaoHistorico, Long idProfessor) {
        try {
            Date data = null;
            getCirurgiaBD(cirurgia);
            try {
                data = new SimpleDateFormat("dd/MM/yyyy").parse(dataHistorico);
                
            } catch (ParseException ex) {
                model.addAttribute("ERROR_MESSAGE", "Data de histório inválida");
                model.addAttribute("cirurgia", cirurgia);
                return "formCirurgia";
            }
            
            cirurgia.adicionarHistorico(data, descricaoHistorico, pessoaDAO.getById(idProfessor));
            cirurgiaDAO.atualizar(cirurgia);
            model.addAttribute("SUCCESS_MESSAGE", "Cirurgia atualizada e histórico adicionado");
            
        } catch (RuntimeException e) {
            model.addAttribute("ERROR_MESSAGE", "Houve um erro ao adicionar o histórico");
        }
        model.addAttribute("cirurgia", cirurgia);
        return "formCirurgia";
    }
    
    private void getCirurgiaBD(Cirurgia cirurgia) {
        if (cirurgia.getId() != null) {
            Cirurgia c = cirurgiaDAO.getById(cirurgia.getId());
            cirurgia.setAnsiolitico(c.getAnsiolitico());
            cirurgia.setAntibiotico(c.getAntibiotico());
            cirurgia.setAntiinflamatorio(c.getAntiinflamatorio());
            cirurgia.setExameRadiografico(c.getExameRadiografico());
            cirurgia.setHistorico(c.getHistorico());
            cirurgia.setPaciente(c.getPaciente());
            cirurgia.setPlanejamento(c.getPlanejamento());
        } else {
            cirurgiaDAO.adicionar(cirurgia);
        }
    }
}
