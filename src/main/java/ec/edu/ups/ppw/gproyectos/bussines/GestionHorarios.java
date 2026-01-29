package ec.edu.ups.ppw.gproyectos.bussines;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.dao.HorarioDAO;
import ec.edu.ups.ppw.gproyectos.model.Horario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionHorarios {

	@Inject
    private HorarioDAO daoHorario;

    public void agregarHorario(Horario horario, int programadorId) throws Exception {
        if(horario.getDiaSemana() == null) throw new Exception("Día es obligatorio");
        daoHorario.agregarHorario(programadorId, horario);
    }

    public List<Horario> listarPorProgramador(int programadorId) {
        return daoHorario.getHorariosPorProgramador(programadorId);
    }

    public void eliminarHorario(int id) {
        daoHorario.delete(id);
    }
}
