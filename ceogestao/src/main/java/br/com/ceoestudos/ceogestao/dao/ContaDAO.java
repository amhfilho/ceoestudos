/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Conta;
import br.com.ceoestudos.ceogestao.model.Parcela;
import br.com.ceoestudos.ceogestao.model.SituacaoConta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author afilho
 */
@Component
public class ContaDAO {

    @PersistenceContext
    private EntityManager em;
    private Logger LOG = Logger.getLogger(getClass());

    public Conta getById(Long id) {
        String query = "select c from Conta c left join fetch c.parcelas where c.id =:id";
        Query q = em.createQuery(query);
        q.setParameter("id", id);
        return (Conta) q.getSingleResult();
    }

    public List<Conta> listarPorNomeCpfTurmaSituacao(String nome, String cpf, SituacaoConta situacao, String idTurma) {

        String query = "select distinct c from Conta c left join fetch c.parcelas WHERE 1 = 1 ";
        if (nome != null && !nome.trim().equals("")) {
            query += " AND UPPER(c.cliente.nome) like '%" + nome.toUpperCase() + "%' ";
        }
        if (cpf != null && !cpf.trim().equals("")) {
            query += " AND c.cliente.cpf = '" + cpf + "'";
        }

        if (idTurma != null && !idTurma.equals("")) {
            query += " AND c.turma.id = " + idTurma;
        }

        List<Conta> contasDB = em.createQuery(query).getResultList();
        
        return filtrarPorSituacao(contasDB, situacao);
    }
    
    public List<Conta> filtrarPorSituacao(List<Conta> contas,SituacaoConta situacao){
        List<Conta> contasResult = new ArrayList<Conta>();
        if(situacao!=null){
            for(int i=0; i < contas.size(); i++){
                Conta c = contas.get(i);
                if(situacao.equals(c.getSituacao())){
                    contasResult.add(c);
                }
            }
        } else {
            return contas;
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

    public void atualizarParcela(Parcela p) {
        em.merge(p);
    }

    public Parcela getParcelaById(Long id) {
        return em.find(Parcela.class, id);
    }

}
