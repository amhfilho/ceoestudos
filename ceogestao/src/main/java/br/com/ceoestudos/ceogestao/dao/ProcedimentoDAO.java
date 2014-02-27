package br.com.ceoestudos.ceogestao.dao;

import br.com.ceoestudos.ceogestao.model.Procedimento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ProcedimentoDAO {
    private Logger LOG = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    public Procedimento getById(Long id) {
        return em.find(Procedimento.class, id);
    }

    public List<Procedimento> listarTodos() {
        return em.createQuery("select p from Procedimento p").getResultList();
    }

    public List<Procedimento> listar(String nome, String filtro) {
        String query = "select p from Procedimento p";
        boolean whereClause=false;
        if (nome!=null ){
            query+=" WHERE p.nome like :nome";
            whereClause=true;
        }
        if(filtro!=null){
            if(!whereClause){
                query+=" WHERE ";
            } else {
                query+=" AND ";
            }
            query+=" p.tipo = :filtro";
        }
        LOG.debug("listar procedimento: "+query);
        Query q = em.createQuery(query);
        if(nome!=null) q = q.setParameter("nome", "%"+nome+"%");
        if(filtro!=null) q = q.setParameter("filtro", filtro);
        return q.getResultList();
    }
    
    

    public List<Procedimento> listarPorTipo(String tipo) {
        String sql = "select p from Procedimento p where p.tipo like :tipo";
        return em.createQuery(sql)
                .setParameter("tipo", "%" + tipo + "%")
                .getResultList();
    }

    public List<Procedimento> listarPorNome(String nome) {
        String sql = "select p from Procedimento p where p.nome like :nome";
        return em.createQuery(sql)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public List<Procedimento> listarPorNomeETipo(String nome, String tipo) {
        String sql = "select p from Procedimento p where p.nome like :nome and p.tipo like :tipo";
        return em.createQuery(sql)
                .setParameter("nome", "%" + nome + "%")
                .setParameter("tipo", "%" + tipo + "%")
                .getResultList();
    }

    public void adicionar(Procedimento procedimento) {
        em.persist(procedimento);
    }

    public void atualizar(Procedimento procedimento) {
        em.merge(procedimento);
    }

    public void remover(Long id) {
        Procedimento procedimento = em.merge(getById(id));
        em.remove(procedimento);
    }

    public List<String> getTiposProcedimentos() {
        return em.createQuery("select distinct p.tipo from Procedimento p").getResultList();
    }
}
