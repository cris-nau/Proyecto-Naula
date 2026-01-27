package ec.edu.ups.ppw.gproyectos.services;

import java.io.IOException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {

	@Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
        
        // 2. Permitir las cabeceras de autorización y contenido
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, x-requested-with");
        
        // 3. Permitir los métodos necesarios
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        
        // 4. Permitir credenciales si fuera necesario
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // MAGIA: Si la petición es de tipo OPTIONS (la pre-consulta), respondemos OK y terminamos
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            responseContext.setStatus(Response.Status.OK.getStatusCode());
        }
    }
}