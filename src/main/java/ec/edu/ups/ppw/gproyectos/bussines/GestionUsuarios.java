package ec.edu.ups.ppw.gproyectos.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionUsuarios {
	
	@Inject
	private UsuarioDAO daoUsuario;

	public List<Usuario> getUsuarios(){
		return daoUsuario.getAll();
	}
	
	public Usuario getUsuarios(int id) throws Exception {
		if(id==0)
			throw new Exception("Parametro vacio");
		Usuario p=daoUsuario.read(id);
		return p;
	}
	
	public void crearUsuario(Usuario u) throws Exception {
		if(u.getId()==0)
			throw new Exception("Parametro vacio");
		daoUsuario.insert(u);
	}
	
	public void actualizarUsuario(Usuario u) throws Exception {
		if(u.getId() == 0)
			throw new Exception("Para actualizar, el usuario debe tener un ID");
		Usuario prev = daoUsuario.read(u.getId());
		if(prev == null)
			throw new Exception("No se puede actualizar: El usuario no existe");
		
		daoUsuario.update(u);
	}
	
	public void eliminarUsuario(int id) throws Exception {
		if(id == 0)
			throw new Exception("ID invalido para eliminar");
			
		daoUsuario.delete(id);
	}
}
