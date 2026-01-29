package ec.edu.ups.ppw.gproyectos.services;

import ec.edu.ups.ppw.gproyectos.bussines.GestionRedesSociales;
import ec.edu.ups.ppw.gproyectos.model.RedSocial;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/redes")
public class RedSocialService {

    @Inject
    private GestionRedesSociales grs;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response agregar(@QueryParam("programador_id") int programadorId, RedSocial red) {
        try {
            grs.agregarRedSocial(red, programadorId);
            return Response.ok(red).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new Error(500, "Error al agregar red social", e.getMessage()))
                           .build();
        }
    }

    @DELETE
    @Produces("application/json")
    public Response eliminar(@QueryParam("id") int id) {
        try {
            grs.eliminarRedSocial(id);
            return Response.ok("{\"mensaje\":\"Enlace eliminado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}