package ec.edu.ups.ppw.gproyectos.services;

import java.awt.PageAttributes.MediaType;
import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.gproyectos.bussines.GestionUsuarios;
import ec.edu.ups.ppw.gproyectos.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("usuario")
public class UsuarioService {
	
	@Inject
	private GestionUsuarios gp;

	@GET
	@Produces("application/json")
	public Response getListaUsuario(){
		List<Usuario> listado=gp.getUsuarios();
		return Response.ok(listado).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getUsuario(@PathParam("id") int id){
		Usuario p;
		try {
			p = gp.getUsuarios(id);
		} catch (Exception e) {
			e.printStackTrace();
			Error err=new Error(500, "Error interno", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
		}
		if(p==null) {
			Error err=new Error(404, "No encontrado", "Persona con ID "+id+" no encontrada");
			return Response.status(Response.Status.NOT_FOUND).entity(err).build();
		}
		return Response.ok(p).build();
	}
	
	
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response crearUsuario(Usuario u, @Context UriInfo uriInfo) {
		try {
			gp.crearUsuario(u);
			return Response.ok(u).build();
		} catch (Exception e) {
			e.printStackTrace();
            Error err = new Error(500, "Error al crear", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
		}
	}
	
	@PUT
    @Path("/{id}") 
    @Consumes("application/json")
    @Produces("application/json")
    public Response actualizarUsuario(@PathParam("id") int id, Usuario u) {
        try {
            u.setId(id);
            gp.actualizarUsuario(u);
			return Response.ok(u).build();

        } catch (Exception e) {
            e.printStackTrace();
            Error err = new Error(500, "Error al actualizar", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }
    }
	
	@DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response eliminarUsuario(@PathParam("id") int id) {
        try {
            Usuario temp = gp.getUsuarios(id);
            if (temp == null) {
                Error err = new Error(404, "No encontrado", "No se puede eliminar: El usuario " + id + " no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(err).build();
            }
            gp.eliminarUsuario(id);
            return Response.status(Response.Status.NO_CONTENT).build();
            
        } catch (Exception e) {
            e.printStackTrace();
            Error err = new Error(500, "Error al eliminar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }
    }
	
	@POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(Usuario credenciales) {
        try {
            Usuario u = gp.validarLogin(credenciales.getEmail(), credenciales.getPassword());
            
            if (u != null) {
                return Response.ok(u).build();
            } else {
                Error err = new Error(401, "No autorizado", "Credenciales incorrectas");
                return Response.status(Response.Status.UNAUTHORIZED).entity(err).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Error err = new Error(500, "Error interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }
    }
	
	@GET
    @Path("/buscar")
    @Produces("application/json")
    public Response buscarPorEmail(@QueryParam("email") String email) {
        try {
            Usuario usuario = gp.buscarUsuarioPorEmail(email);
            
            if (usuario != null) {
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"mensaje\": " + "\"Usuario no encontrado\"" + "}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": " + "\"" + e.getMessage() + "\"" + "}")
                    .build();
        }
    }
	
	@POST
    @Path("/login-firebase")
    @Consumes("application/json")
    @Produces("application/json")
    public Response loginFirebase(Usuario usuarioReq) {
        try {
            Usuario u = gp.loginOcrearUsuarioFirebase(usuarioReq);
            
            return Response.ok(u).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

}
