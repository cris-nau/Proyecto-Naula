package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.bussines.GestionProyecto;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

@Path("programador")
public class ProyectoService {

	@Inject
	private GestionProyecto gp;

	@GET
	@Produces("application/json")
	public List<Programador> getListaProgramador(){
		return gp.getProgramadores();
	}
}
