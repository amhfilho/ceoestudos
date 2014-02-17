package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void atualizar(Turma turma){
//        Turma turmaBD = getById(turma.getId());
//        turmaBD.setCurso(turma.getCurso());
//        turmaBD.setDataInicio(turma.getDataInicio());
//        turmaBD.setDiasDaSemana(turma.getDiasDaSemana());
//        turmaBD.setMinutosAula(turma.getMinutosAula());
//        turmaBD.setObservacoes(turma.getObservacoes());
//        turmaBD.setQuorumMinimo(turma.getQuorumMinimo());
//        turmaBD.setSala(turma.getSala());
//        turmaBD.setSituacao(turma.getSituacao());
        em.merge(turma);
    }
    @Transactional
    public void adicionar(Turma turma){
        em.persist(turma);
    }
    
    public void excluir(Long id){
        Turma turma = getById(id);
        em.merge(turma);
        em.remove(turma);
    }
}
