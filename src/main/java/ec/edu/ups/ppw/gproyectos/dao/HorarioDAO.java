package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.Horario;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class HorarioDAO {

	@PersistenceContext
    private EntityManager em;

    public void insert(Horario horario) {
        em.persist(horario);
    }

    public Horario read(int id) {
        return em.find(Horario.class, id);
    }

    public void update(Horario horario) {
        em.merge(horario);
    }

    public void delete(int id) {
        Horario h = em.find(Horario.class, id);
        em.remove(h);
    }
    
    public void agregarHorario(int idProgramador, Horario horario) {
        Programador p = em.find(Programador.class, idProgramador);
        
        // Vinculamos ambas partes
        horario.setProgramador(p); // El horario conoce a su dueño
        p.getHorarios().add(horario); // El dueño conoce su nuevo horario
        
        em.persist(horario);
        em.merge(p); // Actualizamos el programador
    }
    
    public List<Horario> getHorariosPorProgramador(int programadorId) {
        String jpql = "SELECT h FROM Horario h WHERE h.programador.id = :id";
        Query q = em.createQuery(jpql, Horario.class);
        q.setParameter("id", programadorId);
        return q.getResultList();
    }
}
