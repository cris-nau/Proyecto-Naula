package ec.edu.ups.ppw.gproyectos.services;

import java.io.IOException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.Priorities;
import jakarta.annotation.Priority;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
		
		if (requestContext.getMethod().equals("GET")) {
	        return; // ¡Pase usted!
	    }
		
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();
        if (path.contains("publico")) { 
            return;
        }
        if (method.equals("GET")) {
            return; 
        }
        
        if (path.contains("usuario") && method.equals("POST")) {
            return; 
        }
        
        String authHeader = requestContext.getHeaderString("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("No se encontró el Token de autorización").build());
            return;
        }
        
        String token = authHeader.substring(7);

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            
            String email = decodedToken.getEmail();
            System.out.println("Usuario autorizado: " + email);
            
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("Token inválido o expirado").build());
        }
        
        
    }
}
