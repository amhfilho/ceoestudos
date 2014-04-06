package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Tratamento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class TratamentoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public Tratamento getById(Long id){
        return em.find(Tratamento.class, id);
    }
    
    public List<Tratamento> listarTodos(){
        return em.createQuery("select t from Tratamento t").getResultList();
    }
    
    public void adicionar(Tratamento t){
        em.persist(t);
    }
    
    public void atualizar(Tratamento t){
        em.merge(t);
    }
    
    public void excluir(Tratamento t){
        em.merge(t);
        em.remove(t);
    }
    
}
