package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.Rol;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class RolDAO {

	@PersistenceContext
    private EntityManager em;

    public void insert(Rol rol) {
        em.persist(rol);
    }

    public void update(Rol rol) {
        em.merge(rol);
    }

    public Rol read(int id) {
        return em.find(Rol.class, id);
    }

    public void delete(int id) {
        Rol rol = em.find(Rol.class, id);
        if (rol != null) {
            em.remove(rol);
        }
    }

    public List<Rol> getAll() {
        String jpql = "SELECT r FROM Rol r";
        Query q = em.createQuery(jpql, Rol.class);
        return q.getResultList();
    }
    
    public Rol getRolPorNombre(String nombre) {
        try {
            String jpql = "SELECT r FROM Rol r WHERE r.nombre = :nombre";
            Query q = em.createQuery(jpql, Rol.class);
            q.setParameter("nombre", nombre);
            return (Rol) q.getSingleResult();
        } catch (Exception e) {
            return null; 
        }
    }

    public Rol buscarPorId(int id) {
        return em.find(Rol.class, id);
    }
}
