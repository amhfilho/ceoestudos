package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Curso;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.SituacaoTurma;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author amhfilho
 */
@Component
public class TurmaDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private CursoDAO cursoDAO;
    
    @Autowired
    private PessoaDAO pessoaDAO;
    
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
        Curso cursoBD = cursoDAO.getById(turma.getCurso().getId());
        if(turma.getProfessor()!=null){
            Pessoa professor = pessoaDAO.getById(turma.getProfessor().getIdentificador());
            turma.setProfessor(professor);
        }
        turma.setCurso(cursoBD);
        em.persist(turma);
    }
    
    public void excluir(Long id){
        Turma turma = getById(id);
        if(turma.getSituacao().equals(SituacaoTurma.EM_ANDAMENTO)){
            throw new RuntimeException("Não é possível excluir uma turma em andamento");
        }
        //turma = em.merge(turma);
        em.remove(turma);
    }
}
