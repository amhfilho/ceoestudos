package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.ServicoLaboratorio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author amhfilho
 */
@Component
public class ServicoLaboratorioDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public ServicoLaboratorio getById(Long id){
        return em.find(ServicoLaboratorio.class, id);
    }
    
    public List<ServicoLaboratorio> listarTodos(){
        return em.createQuery("select s from ServicoLaboratorio s").getResultList();
    }
    
    public void adicionar(ServicoLaboratorio s){
        em.persist(s);
    }
    
    public void atualizar(ServicoLaboratorio s){
        em.merge(s);
    }
    
    public void excluir(ServicoLaboratorio s){
        s = em.merge(s);
        em.remove(s);
    }
}
