/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Conta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author afilho
 */
@Component
public class ContaDAO {
    @PersistenceContext
    private EntityManager em;
    
    public Conta getById(Long id){
        return em.find(Conta.class, id);
    }
    
    public List<Conta> listarTodos(){
        return em.createQuery("select c from Conta c").getResultList();
    }
    
    public List<Conta> listarPorNomeCpf(String nome, String cpf, String pagasCanceladas){
        String query = "select c from Conta c WHERE 1 = 1 ";
        if(nome!=null && !nome.trim().equals("")){
            query+=" AND UPPER(c.cliente.nome) like '%"+nome.toUpperCase()+"%' ";
        }
        if(cpf!=null && !cpf.trim().equals("")){
            query += " AND c.cliente.cpf = '"+cpf+"'";
        }
        if(pagasCanceladas==null || pagasCanceladas.equals("")){
            query+= " AND c.situacao = 0";
        }
        return em.createQuery(query).getResultList();
    }
    
    public void adicionar(Conta c){
        em.persist(c);
    }
    
    public void atualizar(Conta c){
        em.merge(c);
    }
    
    public void excluir(Long id){
        em.remove(getById(id));
    }
    
}
