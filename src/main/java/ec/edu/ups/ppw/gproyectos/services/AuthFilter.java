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
	    String method = requestContext.getMethod();
	    String path = requestContext.getUriInfo().getPath();

	    // 1. REGLA DE ORO: Todo lo que sea GET es público (para ver el portafolio)
	    if (method.equals("GET")) {
	        return; 
	    }

	    // 2. EXCEPCIÓN: Si creaste una ruta específica que empiece con "publico/"
	    if (path.startsWith("publico")) {
	        return;
	    }

	    // 3. SEGURIDAD: Para POST, PUT, DELETE, exigimos el Token
	    String authHeader = requestContext.getHeaderString("Authorization");

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	            .entity("{\"error\": \"Acceso denegado. Se requiere Token.\"}").build());
	        return;
	    }

	    try {
	        String token = authHeader.substring(7);
	        // Firebase verifica la firma y la caducidad
	        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
	        
	        // Opcional: Puedes meter el email en el contexto por si lo necesitas luego
	        requestContext.setProperty("userEmail", decodedToken.getEmail());

	    } catch (Exception e) {
	        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	            .entity("{\"error\": \"Token inválido o expirado\"}").build());
	    }
	}
}
