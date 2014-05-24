package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Cirurgia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class CirurgiaDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Cirurgia> listarTodos(){
        return em.createQuery("select c from Cirurgia c").getResultList();
    }
    
    public Cirurgia getById(Long id){
        return em.find(Cirurgia.class, id);
    }
    
    public void adicionar(Cirurgia c){
        em.persist(c);
    }
    
    public void atualizar(Cirurgia c){
        em.merge(c);
    }
    
}
