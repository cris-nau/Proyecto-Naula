package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
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
            // Usamos LOWER para ignorar mayúsculas y TRIM para borrar espacios
            String jpql = "SELECT p FROM Programador p WHERE LOWER(TRIM(p.email)) = LOWER(TRIM(:email))";
            
            return em.createQuery(jpql, Programador.class)
                     .setParameter("email", email)
                     .getSingleResult();
                     
        } catch (NoResultException e) {
            return null; // Si no existe, devuelve null (y el WS devolverá 404)
        } catch (NonUniqueResultException e) {
            // CASO RARO: Si por error hay 2 con el mismo correo, devuelve el primero
            String jpql = "SELECT p FROM Programador p WHERE p.email = :email";
            return em.createQuery(jpql, Programador.class)
                     .setParameter("email", email)
                     .getResultList().get(0);
        }
    }
}
