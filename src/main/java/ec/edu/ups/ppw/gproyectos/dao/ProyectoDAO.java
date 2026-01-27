package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.model.Proyecto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ProyectoDAO {

	@PersistenceContext 
	private EntityManager em;
    
    public void insert(ec.edu.ups.ppw.gproyectos.model.Proyecto p) 
    { 
    	em.persist(p); 
    }
    public void update(ec.edu.ups.ppw.gproyectos.model.Proyecto p) 
    { 
    	em.merge(p); 
    }
    public void delete(int id) { 
        ec.edu.ups.ppw.gproyectos.model.Proyecto p = em.find(ec.edu.ups.ppw.gproyectos.model.Proyecto.class, id);
        if(p!=null) em.remove(p);
    }
    
    public Proyecto read(int id) {
        return em.find(Proyecto.class, id);
    }

    public List<Proyecto> getProyectosPorProgramador(int programadorId) {
        String jpql = "SELECT p FROM Proyecto p WHERE p.programador.id = :id";
        Query q = em.createQuery(jpql, Proyecto.class);
        q.setParameter("id", programadorId);
        return q.getResultList();
    }
}
