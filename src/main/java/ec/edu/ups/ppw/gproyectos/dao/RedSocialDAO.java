package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.RedSocial;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class RedSocialDAO {

	@PersistenceContext private EntityManager em;

    public void insert(RedSocial red) { em.persist(red); }
    public void update(RedSocial red) { em.merge(red); }
    public RedSocial read(int id) { return em.find(RedSocial.class, id); }
    public void delete(int id) {
        RedSocial r = em.find(RedSocial.class, id);
        if (r != null) em.remove(r);
    }
    public List<RedSocial> getAll() {
        return em.createQuery("SELECT r FROM RedSocial r", RedSocial.class).getResultList();
    }
}
