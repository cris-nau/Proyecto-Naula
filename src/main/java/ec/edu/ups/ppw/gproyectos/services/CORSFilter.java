package ec.edu.ups.ppw.gproyectos.services;

import java.io.IOException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.container.PreMatching;

@Provider
@PreMatching
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
	    
	    // CAMBIA ESTO: De "http://localhost:4200" a "*"
	    responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
	    
	    // El resto déjalo igual, pero asegúrate de que estas líneas estén presentes:
	    responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, x-requested-with");
	    responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    
	    // IMPORTANTE: Si usas "*", cambia el "true" por "false" en Credentials o simplemente borra la línea
	    responseContext.getHeaders().add("Access-Control-Allow-Credentials", "false");

	    if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
	        responseContext.setStatus(Response.Status.OK.getStatusCode());
	    }
	}


}