package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.ProcedimentoAvulso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class ProcedimentoAvulsoDAO {
    @PersistenceContext
    private EntityManager em;
    
    public ProcedimentoAvulso getById(Long id){
        return em.find(ProcedimentoAvulso.class, id);
    }
    
    public void adicionar(ProcedimentoAvulso t){
        em.persist(t);
    }
    
    public void atualizar(ProcedimentoAvulso t){
        em.merge(t);
    }
    
    public void remover(ProcedimentoAvulso t){
        em.remove(t);
    }
    
    public List<ProcedimentoAvulso> findByTratamento(Long idTratamento){
        String query = "SELECT p FROM ProcedimentoAvulso p WHERE p.tratamento.id = :idTratamento";
        TypedQuery<ProcedimentoAvulso> q = em.createQuery(query,ProcedimentoAvulso.class);
        q.setParameter("idTratamento", idTratamento);
        return q.getResultList();
    }
}
