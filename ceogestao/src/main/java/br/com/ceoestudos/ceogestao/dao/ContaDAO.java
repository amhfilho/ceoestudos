/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.Parcela;

/**
 *
 * @author afilho
 */
@Component
public class ContaDAO {

    @PersistenceContext
    private EntityManager em;

    public Conta getById(Long id) {
        String query = "select distinct c from Conta c "
        		+ "left join fetch c.parcelas "
        		+ "left join fetch c.pagamentos "
        		+ "where c.id =:id";
        Query q = em.createQuery(query);
        q.setParameter("id", id);
        return (Conta) q.getSingleResult();
    }

    public List<Conta> listarPorNomeCpfTurmaSituacao(String nome, String cpf, Integer situacao, String idTurma) {

        String query = "select distinct c from Conta c "
        		+ "left join fetch c.parcelas "
        		+ "left join fetch c.pagamentos "
        		+ "WHERE 1 = 1 ";
        if (nome != null && !nome.trim().equals("")) {
            query += " AND UPPER(c.cliente.nome) like '%" + nome.toUpperCase() + "%' ";
        }
        if (cpf != null && !cpf.trim().equals("")) {
            query += " AND c.cliente.cpf = '" + cpf + "'";
        }

        if (idTurma != null && !idTurma.equals("")) {
            query += " AND c.turma.id = " + idTurma;
        }

        List<Conta> contasDB = em.createQuery(query,Conta.class).getResultList();
        if(situacao!=null){
        	return filtrarPorSituacao(contasDB, situacao);
        } else {
        	return contasDB;
        }
    }
    
    public List<Conta> filtrarPorSituacao(List<Conta> contas,Integer situacao){
        List<Conta> contasResult = new ArrayList<Conta>();
        
            for(int i=0; i < contas.size(); i++){
                Conta c = contas.get(i);
                switch(situacao){
                case Conta.QUITADAS:
                	if(c.getSaldoDevedor().intValue() <= 0){
                		contasResult.add(c);
                	}
                	break;
                case Conta.SALDO_DEVEDOR:
                	if(c.getSaldoDevedor().intValue() > 0){
                		contasResult.add(c);
                	}
                	break;
                default: 
                	contasResult.add(c);
                	break;
                }
            }
        
        return contasResult;
    }

    public void adicionar(Conta c) {
        em.persist(c);
    }

    public Conta atualizar(Conta c) {
        return em.merge(c);

    }

    public void excluir(Long id) {
        em.remove(getById(id));
    }

    public void excluirParcela(Long id) {
        em.remove(getParcelaById(id));
    }

    public void adicionarParcela(Parcela p) {
        em.persist(p);
    }
    
    public void salvar(Conta p){
    	if(p.getId()==null){
    		adicionar(p);
    	} else {
    		atualizar(p);
    	}
    }

    public void atualizarParcela(Parcela p) {
        em.merge(p);
    }

    public Parcela getParcelaById(Long id) {
        return em.find(Parcela.class, id);
    }

}
