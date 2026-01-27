package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ProgramadorDAO {

	@PersistenceContext
    private EntityManager em;

    public void insert(Programador programador) {
        em.persist(programador);
    }

    public void update(Programador programador) {
        em.merge(programador);
    }

    public Programador read(int id) {
        return em.find(Programador.class, id);
    }

    public void delete(int id) {
        Programador programador = em.find(Programador.class, id);
        if (programador != null) {
            em.remove(programador);
        }
    }

    public List<Programador> getAll() {
        String jpql = "SELECT p FROM Programador p";
        return em.createQuery(jpql, Programador.class).getResultList();
    }
    
    public Programador getProgramadorPorEmail(String email) {
        try {
            String jpql = "SELECT p FROM Programador p WHERE p.email = :email";
            Query q = em.createQuery(jpql, Programador.class);
            q.setParameter("email", email);
            return (Programador) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
