package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.bussines.GestionProgramadores;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("programador")
public class ProgramadorService {

	@Inject
	private GestionProgramadores gp;
	
	@GET
    @Produces("application/json")
    public Response listar() {
		List<Programador> lista = gp.getProgramadores();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/buscar")
    @Produces("application/json")
    public Response buscar(@QueryParam("email") String email) {
        try {
            Programador p = gp.buscarPorEmail(email);
            if (p != null) return Response.ok(p).build();
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response crear(Programador p) {
        try {
            // Usamos un método que creamos en la gestión para guardar (insertar)
            gp.guardarProgramador(p); 
            return Response.ok(p).build();
        } catch (Exception e) {
            return Response.status(500).entity(new Error(500, "Error al crear", e.getMessage())).build();
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response actualizar(Programador p) {
        try {
            gp.actualizarProgramador(p);
            return Response.ok(p).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
