package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.HistoricoTratamento;
import br.com.ceoestudos.ceogestao.model.Tratamento;
import br.com.ceoestudos.ceogestao.model.TratamentoDente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class TratamentoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public Tratamento getById(Long id){
        return em.find(Tratamento.class, id);
    }
    
    public Tratamento getFullById(Long id){
        TypedQuery<Tratamento> query = em.createQuery(
                "select t from Tratamento t "
              + "left join fetch t.dentes "
              + "left join fetch t.procedimentosAvulsos "
              + "left join fetch t.historico "
              + "left join fetch t.responsaveis "
              + "where t.id = :id",Tratamento.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    
    public List<Tratamento> listarTodos(){
        return em.createQuery("select t from Tratamento t "
                + "left join fetch t.dentes "
                + "left join fetch t.procedimentosAvulsos").getResultList();
    }
    
    public void adicionar(Tratamento t){
        em.persist(t);
    }
    
    public void atualizar(Tratamento t){
        em.merge(t);
    }
    
    public void excluir(Tratamento t){
        t = em.merge(t);
        em.remove(t);
    }
    
    public void excluirTratamentoDente(TratamentoDente td){
        td = em.find(TratamentoDente.class, td.getId());
        em.remove(td);
    }
    
    public void excluirHistoricoTratamento(Long id){
        HistoricoTratamento ht = em.find(HistoricoTratamento.class, id);
        em.remove(ht);
    }
    
}
