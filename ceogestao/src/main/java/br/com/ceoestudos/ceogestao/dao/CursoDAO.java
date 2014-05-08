/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Curso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author amhfilho
 */
@Component
public class CursoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public Curso getById(Long id){
        return em.find(Curso.class, id);
    }
    
    public void atualizar(Curso curso){
        em.merge(curso);
    }
    
    public void adicionar(Curso curso){
        em.persist(curso);
    }
    
    public void excluir(Long id){
        Curso curso = getById(id);
        if(curso.getTurmas()!=null && curso.getTurmas().size()>0){
            throw new RuntimeException("Curso não pode ser removido pois existem turmas relacionadas.");
        }
        em.merge(curso);
        em.remove(curso);
    }
    
    public List<Curso> listarTodos(){
        return em.createQuery("select c from Curso c").getResultList();
    }
}
