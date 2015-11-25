/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
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
import br.com.ceoestudos.ceogestao.dao.PagamentoDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.FormaPagamento;
import br.com.ceoestudos.ceogestao.model.Pagamento;
import br.com.ceoestudos.ceogestao.model.Parcela;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.Turma;
import br.com.ceoestudos.ceogestao.util.Util;

@Controller
public class ContaController {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
    private ContaDAO contaDAO;

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private PessoaDAO pessoaDAO;
    
    @Autowired
    private PagamentoDAO pagamentoDAO;

    private Logger LOG = Logger.getLogger(getClass());

    @ModelAttribute("todasAsTurmas")
    public List<Turma> todasAsTurmas() {
        return turmaDAO.listarTodos();
    }

    @RequestMapping("novaConta")
    public String novaConta(Model model) {
        model.addAttribute("conta", new Conta());
        return "formConta";
    }

    @RequestMapping("pesquisarContas")
    public String pesquisarContas(Model model,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "situacao", required = false) Integer situacao,
            @RequestParam(value = "idTurma", required = false) String idTurma) {
        
        model.addAttribute("contas", contaDAO.listarPorNomeCpfTurmaSituacao(nome, cpf, situacao, idTurma));
        model.addAttribute("idTurmaPesquisada", idTurma);
        model.addAttribute("situacaoPesquisa", situacao);
        model.addAttribute("nomePesquisa", nome);
        model.addAttribute("cpfPesquisa", cpf);
        return "contas";
    }
    
    @RequestMapping("contasPorAluno")
    public String contasPorAluno(Model model, String filtro, String orderBy){
    	List<Pessoa> alunos = pessoaDAO.listarAlunosComContas(filtro,orderBy);
    	model.addAttribute("alunos",alunos);
    	model.addAttribute("filtro",filtro);
    	return "contasPorAluno";
    }

    @Transactional
    @RequestMapping(value = "salvarConta", method = RequestMethod.POST)
    public String salvarConta(Model model, @Valid Conta conta, BindingResult result) {
        
    	if (!result.hasErrors()) {
            if (conta.getId() == null) {
            	Parcela parcela = new Parcela();
            	parcela.setValor(conta.getValor());
            	parcela.setVencimento(new Date());
            	conta.addParcela(parcela);
            	contaDAO.adicionar(conta);
            } else {
            	Conta contaDB = refreshContaFromDb(conta);
            	contaDB.atualizarValor(conta.getValor());
                contaDAO.atualizar(contaDB);
                conta = contaDB;
            }
            model.addAttribute("SUCCESS_MESSAGE", "Conta salva com sucesso");
        }
        model.addAttribute("conta", conta);
        return "formConta";
    }

	private Conta refreshContaFromDb(Conta conta) {
		Conta contaDB = contaDAO.getById(conta.getId());
		contaDB.setCliente(conta.getCliente());
		contaDB.setDescricao(conta.getDescricao());
		contaDB.setTipoConta(conta.getTipoConta());
		
		return contaDB;
	}

    @RequestMapping("editarConta")
    public String editarConta(Model model, @RequestParam Long id) {
    	
        Conta conta = contaDAO.getById(id);
		model.addAttribute("conta", conta);
        return "formConta";
    }
    
    @Transactional
    @RequestMapping(value="salvarPagamento", method = RequestMethod.POST)
    public String adicionarPagamento(Model model, Conta conta,
    								 Long idPagamento,
    								 @DateTimeFormat(pattern="dd/MM/yyyy") Date dataPagamento, 
    								 @NumberFormat(style=NumberFormat.Style.NUMBER) BigDecimal valorPagamento, 
    								 @RequestParam(value = "obsPagamento", defaultValue="", required=false)String obsPagamento,
    								 @RequestParam(value = "numCheque", defaultValue="", required=false)String numCheque,
    								 @RequestParam(value = "banco", defaultValue="", required=false)String banco,
    								 @RequestParam(value = "formaPagamento", required=true)String formaPagamento
    								 ){
    	
    	
    		Pagamento pagamento;
    		String acao = "incluido";
    		if(idPagamento==null){
    			pagamento = new Pagamento();
    		} else {
    			acao = "atualizado";
    			pagamento = pagamentoDAO.findById(idPagamento);
    		}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataPagamento);
			
			FormaPagamento forma = FormaPagamento.valueOf(formaPagamento);
			
			pagamento.setBanco(banco);
			pagamento.setConta(conta);
			pagamento.setDataPagamento(cal);
			pagamento.setFormaPagamento(forma);
			pagamento.setNumeroCheque(numCheque);
			pagamento.setObs(obsPagamento);
			pagamento.setValor(valorPagamento);
			
			pagamentoDAO.save(pagamento);
			
			conta = contaDAO.getById(conta.getId());
			
			model.addAttribute("SUCCESS_MESSAGE",String.format("Pagamento %s com sucesso",acao));
			
		
			model.addAttribute("conta",conta);
		
    	   	
    	return "formConta";
    }
    
    @Transactional
    @RequestMapping(value="excluirPagamento", method=RequestMethod.POST)
    public String excluirPagamento(Model model, Conta conta, Long idPagamento){
    	pagamentoDAO.delete(idPagamento);
    	conta = contaDAO.getById(conta.getId());
    	model.addAttribute("conta",conta);
    	model.addAttribute("SUCCESS_MESSAGE","Parcela excluída com sucesso");
    	return "formConta";
    }
    
    

    @Transactional
    @RequestMapping(value="excluirConta", method = RequestMethod.POST)
    public String excluirConta(Long id) {
        contaDAO.excluir(id);

        return "redirect:pesquisarContas.html";
    }

    @Transactional
    @RequestMapping(value = "salvarParcela", method = RequestMethod.POST)
    public String salvarParcela(@ModelAttribute Conta conta,
            Long idParcela,
            @DateTimeFormat(pattern="dd/MM/yyyy") Date vencimentoParcela,
            @NumberFormat(style=NumberFormat.Style.NUMBER) BigDecimal valorParcela,
            String obsParcela,
            Model model) {

        
        try {
            if (vencimentoParcela == null) {
                throw new IllegalArgumentException("A data de vencimento deve estar preenchida");
            }
            
            if (valorParcela == null) {
                throw new IllegalArgumentException("O valor deve ser preenchido");
            }
            
            Parcela parcela;
            if (idParcela == null) {
                parcela = new Parcela();
            } else {
                parcela = contaDAO.getParcelaById(idParcela);
            }
            parcela.setConta(conta);
            parcela.setObs(obsParcela);
            parcela.setValor(valorParcela);
            parcela.setVencimento(vencimentoParcela);
            contaDAO.adicionarParcela(parcela);

            Conta contaDB = contaDAO.getById(conta.getId());
            conta.setCliente(contaDB.getCliente());
            conta.setDescricao(contaDB.getDescricao());
            conta.setParcelas(contaDB.getParcelas());
            conta.setTipoConta(contaDB.getTipoConta());
            
            conta.addParcela(parcela);
            conta.atualizarValor();
            contaDAO.salvar(conta);

            model.addAttribute("conta", conta);
            model.addAttribute("SUCCESS_MESSAGE", "Parcela atualizada com sucesso");

        } catch (RuntimeException rt) {
            LOG.error(new Util().toString(rt));
            model.addAttribute("ERROR_MESSAGE", "Erro ao salvar a parcela: " + rt.getMessage());
        }

        return "formConta";
    }

    @Transactional
    @RequestMapping(value = "excluirParcela", method = RequestMethod.POST)
    public String excluirParcela(Conta conta, Model model, Long idParcelaExcluir) {
    	Conta contaDB = null;
    	try {
            contaDB = refreshContaFromDb(conta);
            contaDB.removeParcela(contaDAO.getParcelaById(idParcelaExcluir));
            contaDB.atualizarValor();
            contaDAO.salvar(contaDB);
            model.addAttribute("conta", contaDB);

        } catch (RuntimeException e) {
            LOG.error(new Util().toString(e));
            model.addAttribute("conta",contaDB);
            model.addAttribute("ERROR_MESSAGE", "Não foi possível excluir a parcela: " + e.getMessage());
        }

        return "formConta";
    }
    
    @Transactional
    @RequestMapping(value = "aplicarTaxaJuros", method= RequestMethod.POST)
    public String aplicarTaxaJuros(Integer numParcelas, 
    		                       @DateTimeFormat(pattern="dd/MM/yyyy") Date dtPrimeiraParcela, 
    		                       @NumberFormat Double taxaJuros,
    		                       Conta conta,
    		                       Model model){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(dtPrimeiraParcela);
    	conta = contaDAO.getById(conta.getId());
    	conta.aplicarTaxaJuros(numParcelas, taxaJuros, cal);
    	if(conta.getId()==null){
    		contaDAO.adicionar(conta);
    	} else {
    		contaDAO.atualizar(conta);
    	}
    	
    	model.addAttribute("conta",conta);
    	model.addAttribute("SUCCESS_MESSAGE","Parcelas criadas com sucesso");   	
    	return "formConta";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Pessoa.class, "cliente", new PessoaPropertyEditor(pessoaDAO));
        SimpleDateFormat dateFormat = SIMPLE_DATE_FORMAT;
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
