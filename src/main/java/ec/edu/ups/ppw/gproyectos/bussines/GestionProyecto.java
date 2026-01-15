package ec.edu.ups.ppw.gproyectos.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.dao.ProgramadorDAO;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionProyecto {

	@Inject
	private ProgramadorDAO daoProgramador;

	public List<Programador> getProgramadores(){
		return daoProgramador.getAll();
	}
}	
