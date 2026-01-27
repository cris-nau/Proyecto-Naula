package ec.edu.ups.ppw.gproyectos.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.dao.ProgramadorDAO;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import ec.edu.ups.ppw.gproyectos.model.Proyecto;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionProyectos {

	@Inject
    private ProyectoDAO daoProyecto;

    @Inject
    private ProgramadorDAO daoProgramador; 
    
    public void crearProyecto(Proyecto proyecto, int programadorId) throws Exception {
        Programador prog = daoProgramador.read(programadorId);
        if (prog == null) {
            throw new Exception("Error: El programador con ID " + programadorId + " no existe.");
        }
        
        proyecto.setProgramador(prog);
        daoProyecto.insert(proyecto);
    }
    
    public List<Proyecto> listarPorProgramador(int programadorId) {
        return daoProyecto.getProyectosPorProgramador(programadorId);
    }
    
    public void actualizarProyecto(Proyecto proyecto) throws Exception {
        daoProyecto.update(proyecto);
    }
    
    public void eliminarProyecto(int id) {
        daoProyecto.delete(id);
    }
}
