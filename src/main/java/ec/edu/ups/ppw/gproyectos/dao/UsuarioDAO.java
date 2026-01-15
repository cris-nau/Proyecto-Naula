package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UsuarioDAO {

	@PersistenceContext
    private EntityManager em;

    public void insert(Usuario usuario) {
        em.persist(usuario);
    }

    public void update(Usuario usuario) {
        em.merge(usuario);
    }

    public Usuario read(int id) {
        return em.find(Usuario.class, id);
    }

    public void delete(int id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
    }

    public List<Usuario> getAll() {
        String jpql = "SELECT u FROM Usuario u";
        Query q = em.createQuery(jpql, Usuario.class);
        return q.getResultList();
    }

    
    public Usuario getUsuarioPorEmail(String email) {
        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
            Query q = em.createQuery(jpql, Usuario.class);
            q.setParameter("email", email);
            return (Usuario) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
