package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.bussines.GestionHorarios;
import ec.edu.ups.ppw.gproyectos.model.Horario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/horarios")
public class HorarioService {

	@Inject
    private GestionHorarios gh;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response agregar(@QueryParam("programador_id") int programadorId, Horario horario) {
        try {
            gh.agregarHorario(horario, programadorId);
            return Response.ok(horario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new Error(500, "Error al agregar horario", e.getMessage()))
                           .build();
        }
    }

    @GET
    @Path("/list")
    @Produces("application/json")
    public Response listar(@QueryParam("programador_id") int programadorId) {
        try {
            List<Horario> lista = gh.listarPorProgramador(programadorId);
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Produces("application/json")
    public Response eliminar(@QueryParam("id") int id) {
        try {
            gh.eliminarHorario(id);
            return Response.ok("{\"mensaje\":\"Horario eliminado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
