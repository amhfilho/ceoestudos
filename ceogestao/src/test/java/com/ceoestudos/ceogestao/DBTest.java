/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ceoestudos.ceogestao;

import br.com.ceoestudos.ceogestao.model.Curso;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.SituacaoTurma;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author amhfilho
 */
public class DBTest {
    
    static EntityManagerFactory emf;
    EntityManager em;
    
    @Ignore
    public void testeCriacaoTurma(){
        em.getTransaction().begin();
        
        Pessoa professor = new Pessoa();
        professor.setCpf("1234");
        professor.setNome("Antonio");
        professor.setEmail("amhfilho@gmail.com");
        //em.persist(professor);
        
        Curso curso = new Curso();
        curso.setCodigo("12345");
        curso.setNome("Curso");
        curso.setCargaHoraria(1);
        //em.persist(curso);
        
        Pessoa aluno = new Pessoa();
        aluno.setCpf("12345");
        aluno.setNome("Ariadine");
        aluno.setEmail("ariadine@gmail.com");
        //em.persist(aluno);
        
        Turma turma = new Turma();
        //turma.setCurso(curso);
        turma.setDiasDaSemana(new String[]{"seg","ter","qua"});
        turma.setSituacao(SituacaoTurma.CONFIRMADA);
        
        Set<Pessoa> alunos = new HashSet<Pessoa>();
        alunos.add(aluno);
        //turma.setAlunos(alunos);
        em.persist(turma);
        
        em.getTransaction().commit();
        
        
    }
    
    
    @BeforeClass
    public static void createEntityFactoryManager(){
        emf = Persistence.createEntityManagerFactory("odontosysPU");
    }
    
    @Before
    public void createEntityManager(){
        em = emf.createEntityManager();
    }
    
    @After
    public void closeEntityManager(){
        em.close();
    }
    
    @AfterClass
    public static void closeEntityManagerFactory(){
        emf.close();
    }
    
}
