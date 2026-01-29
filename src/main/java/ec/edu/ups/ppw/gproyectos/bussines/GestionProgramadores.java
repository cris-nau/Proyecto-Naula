package ec.edu.ups.ppw.gproyectos.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.dao.ProgramadorDAO;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionProgramadores {

	@Inject
	private ProgramadorDAO daoProgramador;

	public List<Programador> getProgramadores(){
		return daoProgramador.getAll();
	}
	
	public Programador getProgramador(int id) throws Exception {
        Programador p = daoProgramador.read(id);
        if(p == null) throw new Exception("Programador no encontrado");
        return p;
    }
	
    public Programador buscarPorEmail(String email) throws Exception {
        if(email == null || email.trim().isEmpty()) {
            throw new Exception("Email es requerido para la búsqueda");
        }
        return daoProgramador.getProgramadorPorEmail(email);
    }
    
    public void actualizarProgramador(Programador programador) throws Exception {
        if(programador.getId() == 0) {
            throw new Exception("El programador debe tener un ID para actualizarse");
        }
        
        Programador pExistente = daoProgramador.read(programador.getId());
        if(pExistente == null) {
            throw new Exception("No existe el registro del programador");
        }
        
        daoProgramador.update(programador);
    }
    
    public void guardarProgramador(Programador programador) throws Exception {
        if (daoProgramador.read(programador.getId()) == null) {
            daoProgramador.insert(programador);
        } else {
            daoProgramador.update(programador);
        }
    }
}	
