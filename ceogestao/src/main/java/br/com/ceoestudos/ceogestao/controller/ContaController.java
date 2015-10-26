/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import br.com.ceoestudos.ceogestao.dao.ContaDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.Parcela;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.SituacaoConta;
import br.com.ceoestudos.ceogestao.model.Turma;
import br.com.ceoestudos.ceogestao.util.Util;

@Controller
public class ContaController {

    @Autowired
    private ContaDAO contaDAO;

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private PessoaDAO pessoaDAO;

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
            @RequestParam(value = "situacao", required = false) String situacao,
            @RequestParam(value = "idTurma", required = false) String idTurma) {
        SituacaoConta sc = null;
        if (situacao != null && !("").equals(situacao)) {
            sc = SituacaoConta.valueOf(situacao);
        }
        model.addAttribute("contas", contaDAO.listarPorNomeCpfTurmaSituacao(nome, cpf, sc, idTurma));
        model.addAttribute("idTurmaPesquisada", idTurma);
        model.addAttribute("situacaoPesquisa", situacao);
        model.addAttribute("nomePesquisa", nome);
        model.addAttribute("cpfPesquisa", cpf);
        return "contas";
    }

    @Transactional
    @RequestMapping(value = "salvarConta", method = RequestMethod.POST)
    public String salvarConta(Model model, @Valid Conta conta, BindingResult result) {
        if (!result.hasErrors()) {
            if (conta.getId() == null) {
                contaDAO.adicionar(conta);
            } else {
                contaDAO.atualizar(conta);
            }
            model.addAttribute("SUCCESS_MESSAGE", "Conta salva com sucesso");
        }
        model.addAttribute("conta", conta);
        return "formConta";
    }

    @RequestMapping("editarConta")
    public String editarConta(Model model, @RequestParam Long id) {
        model.addAttribute("conta", contaDAO.getById(id));
        return "formConta";
    }

    @Transactional
    @RequestMapping("excluirConta")
    public String excluirConta(@RequestParam Long id) {
        contaDAO.excluir(id);

        return "redirect:contas.html";
    }

    @Transactional
    @RequestMapping(value = "salvarParcela", method = RequestMethod.POST)
    public String salvarParcela(@ModelAttribute Conta conta,
            Long idParcela,
            String vencimentoParcela,
            String pagamentoParcela,
            String valorParcela,
            String obsParcela,
            Model model) {

        LOG.info(conta + "\n" + idParcela + "," + vencimentoParcela + "," + pagamentoParcela + ","
                + valorParcela + "," + obsParcela);
        try {
            if (vencimentoParcela == null) {
                throw new RuntimeException("A data de vencimento deve estar preenchida");
            }
            Date vencimento = new SimpleDateFormat("dd/MM/yyyy").parse(vencimentoParcela);
            Date pagamento = null;
            if (pagamentoParcela != null) {
                pagamento = new SimpleDateFormat("dd/MM/yyyy").parse(pagamentoParcela);
            }
            if (valorParcela == null) {
                throw new RuntimeException("O valor deve ser preenchido");
            }
            Locale ptBR = new Locale("pt", "BR");
            NumberFormat numberFormat =  NumberFormat.getNumberInstance(ptBR);
            DecimalFormat df = (DecimalFormat) numberFormat;
            df.setParseBigDecimal(true);           
            BigDecimal valor = (BigDecimal) df.parse(valorParcela);

            Parcela parcela;
            if (idParcela == null) {
                parcela = new Parcela();
            } else {
                parcela = contaDAO.getParcelaById(idParcela);
            }
            parcela.setConta(conta);
            parcela.setObs(obsParcela);
            parcela.setPagamento(pagamento);
            parcela.setValor(valor);
            parcela.setVencimento(vencimento);
            contaDAO.adicionarParcela(parcela);

            Conta contaDB = contaDAO.getById(conta.getId());
            conta.setCliente(contaDB.getCliente());
            conta.setDescricao(contaDB.getDescricao());
            conta.setParcelas(contaDB.getParcelas());
            conta.setTipoConta(contaDB.getTipoConta());
            LOG.info("parcelas: " + conta.getParcelas());
            conta.addParcela(parcela);
            conta = contaDAO.atualizar(conta);

            model.addAttribute("conta", conta);
            model.addAttribute("SUCCESS_MESSAGE", "Parcela atualizada com sucesso");

        } catch (ParseException ex) {
            LOG.error(ex);
            model.addAttribute("ERROR_MESSAGE", "Valores inválidos");
            //model.addAttribute("conta",conta);
        } catch (RuntimeException rt) {
            LOG.error(new Util().toString(rt));
            model.addAttribute("ERROR_MESSAGE", "Erro ao salvar a parcela: " + rt.getMessage());
            //model.addAttribute("conta",conta);
        }

        return "formConta";
    }

    @Transactional
    @RequestMapping(value = "excluirParcela", method = RequestMethod.POST)
    public String excluirParcela(Conta conta, Model model, Long idParcelaExcluir) {
        try {
            LOG.info(idParcelaExcluir);
            LOG.info(conta);

            contaDAO.excluirParcela(idParcelaExcluir);
            model.addAttribute("conta", contaDAO.getById(conta.getId()));

        } catch (RuntimeException e) {
            LOG.error(new Util().toString(e));
            model.addAttribute("ERROR_MESSAGE", "Não foi possível excluir a parcela: " + e.getMessage());
        }

        return "formConta";
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
