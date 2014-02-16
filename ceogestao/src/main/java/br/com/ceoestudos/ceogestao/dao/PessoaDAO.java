package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Pessoa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author amhfilho
 */
@Component
public class PessoaDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Pessoa> listarTodos(){
        return em.createQuery("select p from Pessoa p").getResultList();
    }
    
    public Pessoa getById(Long id){
        return em.find(Pessoa.class, id);
    }
    
    public List<Pessoa> listarPorCpf(String cpf){
        String query = "select p from Pessoa p where p.cpf = :cpf";
        return em.createQuery(query)
                .setParameter("cpf", cpf)
                .getResultList();
    }
    
    public List<Pessoa> listarPorNome(String pesquisa){
        if(pesquisa==null){
            return listarTodos();
        }
        String query = "select p from Pessoa p where p.nome like :pesquisa";
        return em.createQuery(query)
                 .setParameter("pesquisa", "%"+pesquisa+"%")
                 .getResultList();
    }
    
    public void adicionar(Pessoa pessoa){
        if(cpfEncontrado(pessoa.getCpf())){
            throw new RuntimeException("Erro ao adicionar uma pessoa. CPF: "+pessoa.getCpf()+" já existente.");
        }
        em.persist(pessoa);
    }
    
    public void atualizar(Pessoa pessoa){
        List<Pessoa> pessoas = listarPorCpf(pessoa.getCpf());
        if(pessoas.contains(pessoa) && !pessoas.get(pessoas.indexOf(pessoa)).equals(pessoa)){
            throw new RuntimeException("Erro ao atualizar uma pessoa. CPF: "+pessoa.getCpf()+" já existente.");
        }
        em.merge(pessoa);
    }
    
    public void excluir(Long id){
        Pessoa pessoa = getById(id);
        pessoa= em.merge(pessoa);
        em.remove(pessoa);
    }
    
    private boolean cpfEncontrado(String cpf){
        return listarPorCpf(cpf).size() > 0;
    }
}
