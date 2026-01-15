package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.Solicitud;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class SolicitudDAO {

	@PersistenceContext
    private EntityManager em;

    public void insert(Solicitud solicitud) {
        em.persist(solicitud);
    }

    public Solicitud read(int id) {
        return em.find(Solicitud.class, id);
    }

    public void update(Solicitud solicitud) {
        em.merge(solicitud);
    }

    public void delete(int id) {
        Solicitud solicitud = em.find(Solicitud.class, id);
        em.remove(solicitud);
    }

    public List<Solicitud> getAll() {
        String jpql = "SELECT s FROM Solicitud s";
        Query q = em.createQuery(jpql, Solicitud.class);
        return q.getResultList();
    }
    
    public List<Solicitud> getSolicitudesPorUsuario(int usuarioId) {
        String jpql = "SELECT s FROM Solicitud s WHERE s.usuario.id = :id";
        Query q = em.createQuery(jpql, Solicitud.class);
        q.setParameter("id", usuarioId);
        return q.getResultList();
    }
}
