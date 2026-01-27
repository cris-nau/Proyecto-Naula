package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.bussines.GestionProyectos;
import ec.edu.ups.ppw.gproyectos.model.Proyecto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/proyectos")
public class ProyectoServices {

	@Inject
    private GestionProyectos gp;
	
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response crear(@QueryParam("programador_id") int programadorId, Proyecto proyecto) {
        try {
            gp.crearProyecto(proyecto, programadorId);
            return Response.ok(proyecto).build();
        } catch (Exception e) {
        	e.printStackTrace(); // Esto te ayudará a ver el error real en la consola de WildFly
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }
    
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response listar(@QueryParam("programador_id") int programadorId) {
        try {
            List<Proyecto> lista = gp.listarPorProgramador(programadorId);
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response actualizar(Proyecto proyecto) {
        try {
            gp.actualizarProyecto(proyecto);
            return Response.ok(proyecto).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
    
    @DELETE
    @Produces("application/json")
    public Response eliminar(@QueryParam("id") int id) {
        try {
            gp.eliminarProyecto(id);
            return Response.ok("{\"mensaje\":\"Eliminado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
