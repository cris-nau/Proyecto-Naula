package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.Horario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class HorarioDAO {

	@PersistenceContext private EntityManager em;

    public void insert(Horario horario) { em.persist(horario); }
    public void update(Horario horario) { em.merge(horario); }
    public Horario read(int id) { return em.find(Horario.class, id); }
    public void delete(int id) {
        Horario h = em.find(Horario.class, id);
        if (h != null) em.remove(h);
    }
    public List<Horario> getAll() {
        return em.createQuery("SELECT h FROM Horario h", Horario.class).getResultList();
    }
}
