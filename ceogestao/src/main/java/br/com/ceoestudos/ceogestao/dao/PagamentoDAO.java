package br.com.ceoestudos.ceogestao.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import br.com.ceoestudos.ceogestao.model.Pagamento;

@Component
public class PagamentoDAO {

	@PersistenceContext
	private EntityManager em;
	
	public Pagamento findById(Long id){
		return em.find(Pagamento.class, id);
	}
	
	public void create(Pagamento p){
		em.persist(p);
	}
	
	public Pagamento merge(Pagamento p){
		return em.merge(p);
	}
	
	public void delete(Pagamento p){
		em.merge(p);
		em.remove(p);
	}
	
	public void delete(Long idPagamento){
		delete(findById(idPagamento));
	}
	
	public void save(Pagamento pagamento){
		if(pagamento!=null){
			if(pagamento.getId()==null){
				create(pagamento);
			} else {
				merge(pagamento);
			}
		}
	}
	
	
}
