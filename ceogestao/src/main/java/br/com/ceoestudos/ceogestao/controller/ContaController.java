/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.ContaDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.SituacaoConta;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
public class ContaController {
    
    @Autowired
    private ContaDAO contaDAO;
    
    @Autowired
    private TurmaDAO turmaDAO;
    
    @Autowired
    private PessoaDAO pessoaDAO;
    
    @ModelAttribute("todasAsTurmas")
    public List<Turma> todasAsTurmas(){
        return turmaDAO.listarTodos();
    } 
    
    @RequestMapping("contas")
    public String listarContasPendentes(Model model){
        model.addAttribute("contas",contaDAO.listarContasPorSituacao(SituacaoConta.PENDENTE));
        return "contas";
    }
    
    @RequestMapping("novaConta")
    public String novaConta(Model model){
        model.addAttribute("conta", new Conta());
        return "formConta";
    }
    
    @RequestMapping("pesquisarContas")
    public String pesquisarContas(Model model,
            @RequestParam String nome, 
            @RequestParam String cpf, 
            @RequestParam(required=false, value="pagasCanceladas") String pagasCanceladas,
            @RequestParam String idTurma){
        model.addAttribute("contas",contaDAO.listarPorNomeCpfTurma(nome, cpf,pagasCanceladas,idTurma));
        model.addAttribute("idTurmaPesquisada",idTurma);
        model.addAttribute("pagasCanceladas",pagasCanceladas);
        return "contas";
    }
    
    @Transactional
    @RequestMapping(value="salvarConta",method = RequestMethod.POST)
    public String salvarConta(Model model, @Valid Conta conta, BindingResult result){
        if(!result.hasErrors()){
            if(conta.getId()==null){
                contaDAO.adicionar(conta);
            } else {
                contaDAO.atualizar(conta);
            }
            model.addAttribute("SUCCESS_MESSAGE","Conta salva com sucesso");
        }
        model.addAttribute("conta",conta);
        return "formConta";
    }
    
    @RequestMapping("editarConta")
    public String editarConta(Model model, @RequestParam Long id){
        model.addAttribute("conta", contaDAO.getById(id));
        return "formConta";
    }
    
    @Transactional
    @RequestMapping("excluirConta")
    public String excluirConta(@RequestParam Long id){
        contaDAO.excluir(id);
        
        return "redirect:contas.html";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Pessoa.class, "cliente", new PessoaPropertyEditor(pessoaDAO));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
}
