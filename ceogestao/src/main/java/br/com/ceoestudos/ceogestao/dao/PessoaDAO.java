package br.com.ceoestudos.ceogestao.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.TipoPessoa;
import br.com.ceoestudos.ceogestao.model.ValorComparator;

/**
 *
 * @author amhfilho
 */
@Component
public class PessoaDAO {

    @PersistenceContext
    private EntityManager em;
    private final Logger LOGGER= Logger.getLogger(getClass());
    private final String SQL_BASE_PESSOA = "select distinct p from Pessoa p "
    		+ "left join fetch p.turmas "
    		+ "left join fetch p.contas ";

    public Pessoa getById(Long id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> listarPorCpf(String cpf) {
        String query = SQL_BASE_PESSOA + " where p.cpf = :cpf";
        return em.createQuery(query, Pessoa.class)
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
            
            cb.like(
            cb.lower(
                emp.get(
                    type.getDeclaredSingularAttribute("nome", String.class)
                )
            ), "%" + nome.toLowerCase() + "%"
            );
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
        q.setParameter("tipo", tipo);

        return q.getResultList();
    }

    public List<Pessoa> listarPorNome(String pesquisa, TipoPessoa tipo) {
        
        String query = SQL_BASE_PESSOA + " where p.tipo = :tipo";
        
        if(pesquisa != null && !("").equals(pesquisa)){
            query+=" AND p.nome like :pesquisa";
        }
        LOGGER.debug("listarPorNome: "+query);
        TypedQuery<Pessoa> q =em.createQuery(query,Pessoa.class).setParameter("tipo", tipo);
        
        if(pesquisa != null && !("").equals(pesquisa)){
            q.setParameter("pesquisa", "%" + pesquisa + "%");
        }
        
        return q.getResultList();
    }

    public List<Pessoa> listarProfessores() {
        return em.createQuery(SQL_BASE_PESSOA + " where p.tipo = 'PROFESSOR'",Pessoa.class).getResultList();
    }

    public List<Pessoa> listarAlunos() {
        return em.createQuery(SQL_BASE_PESSOA + " where p.tipo = 'ALUNO'",Pessoa.class).getResultList();
    }
    
    public List<Pessoa> listarAlunosComContas(String filtro, String orderBy){
    	List<Pessoa> result = new ArrayList<Pessoa>();
    	String query = " select distinct p from Pessoa p "
    			+ "join fetch p.contas contas "
    			+ "left join fetch contas.parcelas "
    			+ "left join fetch contas.pagamentos "
    			+ "left join p.turmas AS turma "
    			+ "where p.tipo = 'ALUNO' ";
    	
    	if(orderBy==null || "ORDER_ALUNO".equals(orderBy)) {
    		query += " ORDER BY p.nome asc ";
    	}
    	if("ORDER_CURSO".equals(orderBy)){
    		query += " ORDER BY turma.curso.nome asc ";
    	}
    	List<Pessoa> pessoas= em.createQuery(query,Pessoa.class).getResultList();
    	for(int i=0; i < pessoas.size(); i++){
    		Pessoa p = pessoas.get(i);
	    	if("DEVEDORES".equals(filtro)){
	    		if(p.getSaldoDevedor().compareTo(BigDecimal.ZERO) > 0){
	    			result.add(p);
	    		}
	    	}
	    	else if ("EM_DIA".equals(filtro)){
	    		if(p.getSaldoDevedor().compareTo(BigDecimal.ZERO) == 0){
	    			result.add(p);
	    		}
	    	}
	    	else {
	    		result.add(p);
	    	}
    	}
    	ValorComparator comparator = new ValorComparator();
    	if("ORDER_MAIOR_VALOR".equals(orderBy)){
    		Collections.sort(result,Collections.reverseOrder(comparator));
    	}
    	if("ORDER_MENOR_VALOR".equals(orderBy)){
    		Collections.sort(result,comparator);
    	}
    	return result;
    			
    }
    
    public static void main(String[] args) {
    	BigDecimal numero = new BigDecimal("4.00");  
    	BigDecimal numero2 = new BigDecimal("5.00");          
    	int i = numero.compareTo(numero2);        
    	System.out.println(i);  
	}

    public List<Pessoa> listarAlunosPorNome(String pesquisa) {
        String query = SQL_BASE_PESSOA + " where p.tipo = 'ALUNO' and p.nome like :pesquisa";
        return em.createQuery(query,Pessoa.class)
                .setParameter("pesquisa", "%" + pesquisa + "%")
                .getResultList();

    }
    
    public List<Pessoa> listarAlunosEInteressadosPorNome(String pesquisa){
        String query = SQL_BASE_PESSOA + " where (p.tipo = 'ALUNO' or p.tipo='LISTA_ESPERA') and p.nome like :pesquisa";
        return em.createQuery(query, Pessoa.class)
                .setParameter("pesquisa", "%" + pesquisa + "%")
                .getResultList();
    }

    public void adicionar(Pessoa pessoa) {
        if (pessoa.getCpf()!=null){
            if (cpfEncontrado(pessoa.getCpf())) {
                throw new RuntimeException("Erro ao adicionar uma pessoa. CPF: " + pessoa.getCpf() + " já existente.");
            }
        }
        em.persist(pessoa);
    }

    public void atualizar(Pessoa pessoa) {
        if(pessoa.getCpf()!=null){
            List<Pessoa> pessoas = listarPorCpf(pessoa.getCpf());
            if (pessoas.contains(pessoa) && !pessoas.get(pessoas.indexOf(pessoa)).equals(pessoa)) {
                throw new RuntimeException("Erro ao atualizar uma pessoa. CPF: " + pessoa.getCpf() + " já existente.");
            }
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
