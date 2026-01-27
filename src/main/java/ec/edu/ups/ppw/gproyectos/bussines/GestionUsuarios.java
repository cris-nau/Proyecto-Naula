package ec.edu.ups.ppw.gproyectos.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.dao.*;
import ec.edu.ups.ppw.gproyectos.model.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionUsuarios {
	
	@Inject
	private UsuarioDAO daoUsuario;
	
	@Inject
    private ProgramadorDAO programadorDAO;

    @Inject
    private HorarioDAO horarioDAO;  
    
    @Inject
    private RolDAO rolDAO;   
    
    public void registrarHorario(int idProgramador, Horario horario) throws Exception {
        Programador programador = programadorDAO.read(idProgramador);
        
        if (programador == null) {
            throw new Exception("El programador no existe");
        }
        
        horario.setProgramador(programador);
        
        horarioDAO.insert(horario);
    }

	public List<Usuario> getUsuarios(){
		return daoUsuario.getAll();
	}
	
	public Usuario getUsuarios(int id) throws Exception {
		if(id==0)
			throw new Exception("Parametro vacio");
		Usuario p=daoUsuario.read(id);
		return p;
	}
	
	public void crearUsuario(Usuario u) throws Exception {
		if(u.getNombre() == null || u.getNombre().isEmpty()) {
	        throw new Exception("El nombre es obligatorio");
	    }
	    
	    if(u.getEmail() == null || u.getEmail().isEmpty()) {
	        throw new Exception("El email es obligatorio");
	    }
		daoUsuario.insert(u);
	}
	
	public void actualizarUsuario(Usuario u) throws Exception {
		if(u.getId() == 0)
			throw new Exception("Para actualizar, el usuario debe tener un ID");
		Usuario prev = daoUsuario.read(u.getId());
		if(prev == null)
			throw new Exception("No se puede actualizar: El usuario no existe");
		
		daoUsuario.update(u);
	}
	
	public void eliminarUsuario(int id) throws Exception {
		if(id == 0)
			throw new Exception("ID invalido para eliminar");
			
		daoUsuario.delete(id);
	}
	
	public Usuario validarLogin(String email, String password) throws Exception {
        // 1. Buscamos al usuario en bruto (sin sincronizar aún)
        Usuario u = daoUsuario.getUsuarioPorEmail(email);
        
        // 2. Validaciones de credenciales
        if (u == null) {
            return null; // No existe el correo
        }
        
        if (!u.getPassword().equals(password)) {
            return null; // Contraseña incorrecta
        }

        sincronizarDatosProgramador(u);
        
        return u;
    }
	
	// Método auxiliar para no repetir código entre Firebase y Login Normal
	private void sincronizarDatosProgramador(Usuario u) throws Exception {
	    // Si el usuario tiene ROL DE PROGRAMADOR (ID 2)
	    if (u.getRol() != null && u.getRol().getId() == 2) {
	        
	        // Verificamos si existe en la tabla hija
	        Programador prog = programadorDAO.read(u.getId());
	        
	        // Si no existe (porque cambiaste el rol manualmente en SQL)
	        if (prog == null) {
	            System.out.println(">>> Sincronizando: Creando perfil de Programador para ID " + u.getId());
	            
	            Programador nuevoP = new Programador();
	            nuevoP.setNombre(u.getNombre() + " " + u.getApellido());
	            nuevoP.setEmail(u.getEmail());
	            nuevoP.setDescripcion("Perfil generado automáticamente al iniciar sesión");
	            nuevoP.setActivo(true);
	            
	            programadorDAO.insert(nuevoP);
	        }
	    }
	}
	
	public Usuario buscarUsuarioPorEmail(String email) throws Exception {
        if(email == null || email.trim().isEmpty()) {
            throw new Exception("El email es necesario para buscar");
        }
        
        // 1. Buscamos el Usuario (Login básico)
        Usuario u = daoUsuario.getUsuarioPorEmail(email);
        
        if (u == null) {
            return null; // No existe el usuario
        }

        // 2. SINCRONIZACIÓN: Verificamos si es Programador y si tiene ficha
        try {
            if (u.getRol() != null && "PROGRAMADOR".equalsIgnoreCase(u.getRol().getNombre())) {
                
                // Buscamos en la tabla TBL_PROGRAMADOR
                Programador p = programadorDAO.getProgramadorPorEmail(email);
                
                // 3. Si es NULL, significa que falta la ficha. ¡La creamos!
                if (p == null) {
                    System.out.println(">>> ALERTA: Usuario es Programador pero no tiene ficha. Creando...");
                    
                    p = new Programador();
                    
                    // Copiamos datos básicos
                    p.setNombre(u.getNombre() + " " + u.getApellido());
                    p.setEmail(u.getEmail());
                    p.setActivo(true);
                    
                    // Llenamos campos obligatorios con valores por defecto
                    p.setContacto("Sin contacto registrado");
                    p.setEspecialidad("General");
                    p.setDescripcion("Perfil generado automáticamente.");
                    p.setFoto("https://cdn-icons-png.flaticon.com/512/149/149071.png");
                    
                    // Guardamos en TBL_PROGRAMADOR
                    programadorDAO.insert(p);
                    
                    System.out.println(">>> ÉXITO: Ficha de Programador creada para: " + email);
                }
            }
        } catch (Exception e) {
            System.err.println("Error en la sincronización de programador: " + e.getMessage());
            // No lanzamos la excepción para no bloquear el login, solo lo logueamos
        }

        return u;
    }
	
	public Usuario loginOcrearUsuarioFirebase(Usuario usuarioReq) throws Exception {
        Usuario u = daoUsuario.getUsuarioPorEmail(usuarioReq.getEmail());

        if (u == null) {
            u = new Usuario();
            u.setEmail(usuarioReq.getEmail());
            u.setNombre(usuarioReq.getNombre());
            u.setApellido(usuarioReq.getApellido());
            u.setPassword("FIREBASE");
            
            Rol rolDefecto = rolDAO.read(1); 
            u.setRol(rolDefecto);

            daoUsuario.insert(u);
        }else {
            if (u.getRol().getId() == 2) {
                
                // Verificamos: ¿Ya tiene datos en la tabla TBL_PROGRAMADOR?
            	Programador prog = programadorDAO.getProgramadorPorEmail(u.getEmail());
                
                // Si NO tiene datos (es nulo), significa que acabas de cambiarle el rol manual
                if (prog == null) {
                    System.out.println(">>> DETECTADO CAMBIO DE ROL MANUAL: Creando perfil de Programador...");
                    
                    Programador nuevoP = new Programador();
                    nuevoP.setNombre(u.getNombre() + " " + u.getApellido());
                    nuevoP.setEmail(u.getEmail());
                    nuevoP.setDescripcion("Perfil generado automáticamente tras cambio de rol");
                    nuevoP.setActivo(true);
                    
                    // Guardamos en la tabla hija
                    programadorDAO.insert(nuevoP);
                }
            }
        }

        return u;
    }
}
