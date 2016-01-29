package br.com.ceoestudos.ceogestao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import br.com.ceoestudos.ceogestao.model.Cirurgia;

@Component
public class CirurgiaDAO {
    
    private static final String BASE_QUERY = "select c from Cirurgia c LEFT JOIN FETCH c.historico";
	@PersistenceContext
    private EntityManager em;
    
    public List<Cirurgia> listarTodos(){
        return em.createQuery(BASE_QUERY,Cirurgia.class).getResultList();
    }
    
    public Cirurgia getById(Long id){
        TypedQuery<Cirurgia> query = em.createQuery(BASE_QUERY + " WHERE c.id = :id",Cirurgia.class);
        query.setParameter("id", id);
        List<Cirurgia> list = query.getResultList();
        if(!list.isEmpty()){
        	return list.get(0);
        }
		return null;
    }
    
    public void adicionar(Cirurgia c){
        em.persist(c);
    }
    
    public void atualizar(Cirurgia c){
        em.merge(c);
    }
    
    public void excluir(Cirurgia c){
        em.merge(c);
        em.remove(c);
    }
    
}
