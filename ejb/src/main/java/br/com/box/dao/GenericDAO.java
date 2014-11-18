package br.com.box.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.box.model.EntidadeComum;

@SuppressWarnings("unchecked")	
public abstract class GenericDAO<T extends EntidadeComum>{

    @PersistenceContext(unitName="BOX_DS")
    private EntityManager em;
    
    public void salvar(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
 
    public void atualizar(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
      
	public T buscar(Long id) {
        return (T) getEntityManager().find(getTypeClass(), id);
    }
 
    public T buscaDetach(Long id) {
        T object = (T) buscar(id);
        getEntityManager().detach(object);
        return object;
    }
    
    public void delete(T entity) {
    	entity = getEntityManager().merge(entity);
        getEntityManager().remove(entity);
        getEntityManager().flush();
    }
        
    public List<T> listarPor(String atributo, Object valor) {
    	Query query = getEntityManager().createQuery("FROM " + getTypeClass().getSimpleName() + " o WHERE o." + atributo + " = :valor");
    	query.setParameter("valor", valor);
    	return query.getResultList();
    }
    
    public List<T> listarPorLike(String atributo, Object valor) {
    	Query query = getEntityManager().createQuery("FROM " + getTypeClass().getSimpleName() + " o WHERE o." + atributo + " like :valor");
    	query.setParameter("valor", "%" + valor + "%");
    	return query.getResultList();
    }
    
    public T buscarPor(String atributo, Object valor) {
    	Query query = getEntityManager().createQuery("FROM " + getTypeClass().getSimpleName() + " o WHERE o." + atributo + " = :valor");
    	query.setParameter("valor", valor);
    	try {
    		return (T) query.getSingleResult();
    	} catch(NoResultException e) {
    		return null;
    	}
    }
 
    public List<T> listar() {
        return getEntityManager().createQuery("FROM " + getTypeClass().getSimpleName() + " o").getResultList();
    }
    
    public T attach(T object) {
    	return (T) getEntityManager().getReference(getTypeClass(), object.getId());
    }
    
    protected EntityManager getEntityManager() {
        if (em == null)
            throw new IllegalStateException("getEntityManager() não foi setado antes do uso neste DAO");
        return em;
    }
    
    private Class<?> getTypeClass() {
    	Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }
    
    public T salvarERetornar(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }
    

}
