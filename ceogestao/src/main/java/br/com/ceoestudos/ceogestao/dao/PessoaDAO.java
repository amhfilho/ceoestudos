package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.TipoPessoa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import org.apache.log4j.Logger;


import org.springframework.stereotype.Component;

/**
 *
 * @author amhfilho
 */
@Component
public class PessoaDAO {

    @PersistenceContext
    private EntityManager em;
    private Logger LOGGER= Logger.getLogger(getClass());


    public Pessoa getById(Long id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> listarPorCpf(String cpf) {
        String query = "select p from Pessoa p where p.cpf = :cpf";
        return em.createQuery(query)
                .setParameter("cpf", cpf)
                .getResultList();
    }

    public List<Pessoa> listarPorCriteria(String nome, TipoPessoa tipo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pessoa> c = cb.createQuery(Pessoa.class);
        Root<Pessoa> emp = c.from(Pessoa.class);
        EntityType<Pessoa> type = em.getMetamodel().entity(Pessoa.class);
        c.select(emp);
        c.distinct(true);

        List<Predicate> criteria = new ArrayList<Predicate>();
        if (nome != null) {
            ParameterExpression<String> p
                    = cb.parameter(String.class, "nome");
            //criteria.add(cb.equal(emp.get("nome"), p));
            cb.like(
            cb.lower(
                emp.get(
                    type.getDeclaredSingularAttribute("nome", String.class)
                )
            ), "%" + nome.toLowerCase() + "%"
            );
            //criteria.add(cb.like(emp.get("nome"), p));
        }

        ParameterExpression<TipoPessoa> p
                = cb.parameter(TipoPessoa.class, "tipo");
        criteria.add(cb.equal(emp.get("tipo"), p));

        if (criteria.isEmpty()) {
            throw new RuntimeException("no criteria");
        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(cb.and(criteria.toArray(new Predicate[0])));
        }
        TypedQuery<Pessoa> q = em.createQuery(c);
//        if (nome != null) {
//            q.setParameter("nome", nome);
//        }

        q.setParameter("tipo", tipo);

        return q.getResultList();
    }

    public List<Pessoa> listarPorNome(String pesquisa, TipoPessoa tipo) {
        
        String query = "select p from Pessoa p where p.tipo = :tipo";
        
        if(pesquisa != null && !("").equals(pesquisa)){
            query+=" AND p.nome like :pesquisa";
        }
        LOGGER.debug("listarPorNome: "+query);
        Query q =em.createQuery(query).setParameter("tipo", tipo);
        
        if(pesquisa != null && !("").equals(pesquisa)){
            q.setParameter("pesquisa", "%" + pesquisa + "%");
        }
        
        return q.getResultList();
    }

    public List<Pessoa> listarProfessores() {
        return em.createQuery("select p from Pessoa p where p.professor = TRUE").getResultList();
    }

    public List<Pessoa> listarAlunos() {
        return em.createQuery("select p from Pessoa p where p.professor = FALSE").getResultList();
    }

    public List<Pessoa> listarAlunosPorNome(String pesquisa) {
        String query = "select p from Pessoa p where p.professor = FALSE and p.nome like :pesquisa";
        return em.createQuery(query)
                .setParameter("pesquisa", "%" + pesquisa + "%")
                .getResultList();

    }

    public void adicionar(Pessoa pessoa) {
        if (cpfEncontrado(pessoa.getCpf())) {
            throw new RuntimeException("Erro ao adicionar uma pessoa. CPF: " + pessoa.getCpf() + " já existente.");
        }
        em.persist(pessoa);
    }

    public void atualizar(Pessoa pessoa) {
        List<Pessoa> pessoas = listarPorCpf(pessoa.getCpf());
        if (pessoas.contains(pessoa) && !pessoas.get(pessoas.indexOf(pessoa)).equals(pessoa)) {
            throw new RuntimeException("Erro ao atualizar uma pessoa. CPF: " + pessoa.getCpf() + " já existente.");
        }
        em.merge(pessoa);
    }

    public void excluir(Long id) {
        Pessoa pessoa = getById(id);
        if (pessoa.getTurmas() != null && pessoa.getTurmas().size() > 0) {
            throw new RuntimeException("Não é possível excluir a pessoa pois a mesma faz parte de uma turma. ");
        }
        pessoa = em.merge(pessoa);
        em.remove(pessoa);
    }

    private boolean cpfEncontrado(String cpf) {
        return listarPorCpf(cpf).size() > 0;
    }
}
