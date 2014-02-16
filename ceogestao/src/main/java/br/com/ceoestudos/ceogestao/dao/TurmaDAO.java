package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Turma;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author amhfilho
 */
@Component
public class TurmaDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Turma> listarTodos(){
        return em.createQuery("select t from Turma t").getResultList();
    }
    
    public Turma getById(Long id){
        return em.find(Turma.class, id);
    }
    
    public void atualizar(Turma turma){
        em.merge(turma);
    }
    
    public void adicionar(Turma turma){
        em.persist(turma);
    }
    
    public void excluir(Long id){
        Turma turma = getById(id);
        em.merge(turma);
        em.remove(turma);
    }
}
