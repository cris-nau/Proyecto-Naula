package ec.edu.ups.ppw.gproyectos.bussines;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ec.edu.ups.ppw.gproyectos.dao.ProgramadorDAO;
import ec.edu.ups.ppw.gproyectos.dao.RolDAO;
import ec.edu.ups.ppw.gproyectos.dao.SolicitudDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.model.Horario;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import ec.edu.ups.ppw.gproyectos.model.RedSocial;
import ec.edu.ups.ppw.gproyectos.model.Rol;
import ec.edu.ups.ppw.gproyectos.model.Solicitud;
import ec.edu.ups.ppw.gproyectos.model.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class Demo {
	
	@Inject
	private RolDAO daoRol;
	
	@Inject
	private UsuarioDAO daoUsuario;
	
	@Inject
	private ProgramadorDAO daoProgramador;
	
	@Inject
	private SolicitudDAO daoSolicitud;

	@PostConstruct
	public void init() {
		
		
		Rol rolCliente = new Rol();
		rolCliente.setNombre("CLIENTE");
		rolCliente.setDescripcion("Usuario que busca servicios");
		daoRol.insert(rolCliente);
		
		Rol rolAdmin = new Rol();
		rolAdmin.setNombre("ADMIN");
		rolAdmin.setDescripcion("Administrador del sistema");
		daoRol.insert(rolAdmin);
		
		System.out.println("-> Roles creados");

		
		Usuario u = new Usuario();
		u.setNombre("Santiago");
		u.setApellido("Mosquera");
		u.setEmail("santiago@mail.com");
		u.setPassword("12345");
		u.setRol(rolCliente); 
		
		daoUsuario.insert(u);
		System.out.println("-> Usuario creado: " + u.getNombre());
		
		u = new Usuario();
		u.setNombre("Santiago");
		u.setApellido("Naula");
		u.setEmail("santiagoNa@mail.com");
		u.setPassword("12346");
		u.setRol(rolAdmin); 
		
		daoUsuario.insert(u);
		
		Programador p = new Programador();
		p.setNombre("Christian Naula");
		p.setEmail("chris@code.com");
		p.setEspecialidad("Desarrollo Web Java");
		p.setDescripcion("Experto en Jakarta EE y Angular");
		
		
		List<Horario> listaHorarios = new ArrayList<>();
		
		Horario h1 = new Horario();
		h1.setDia("Lunes");
		h1.setHoraInicio("08:00");
		h1.setHoraFin("12:00");
		h1.setProgramador(p);
		listaHorarios.add(h1);
		
		Horario h2 = new Horario();
		h2.setDia("Martes");
		h2.setHoraInicio("14:00");
		h2.setHoraFin("18:00");
		h2.setProgramador(p);
		listaHorarios.add(h2);
		
		p.setHorarios(listaHorarios);
		
		List<RedSocial> listaRedes = new ArrayList<>();
		
		RedSocial red = new RedSocial();
		red.setNombre("GitHub");
		red.setUrl("github.com/cnaula");
		red.setProgramador(p);
		listaRedes.add(red);
		
		p.setRedesSociales(listaRedes);
		
		daoProgramador.insert(p);
		System.out.println("-> Programador creado con sus horarios");

		// 4. CREAR UNA SOLICITUD
		Solicitud sol = new Solicitud();
		sol.setFechaSolicitud(new Date());
		sol.setMensaje("Hola, necesito una página web.");
		sol.setEstado("PENDIENTE");
		sol.setUsuario(u);     
		sol.setProgramador(p);  
		
		daoSolicitud.insert(sol);
		System.out.println("-> Solicitud creada exitosamente");
		
		// 5. LISTAR PARA VERIFICAR
		System.out.println("--- LISTADO DE SOLICITUDES ---");
		List<Solicitud> solicitudes = daoSolicitud.getAll();
		for(Solicitud s : solicitudes) {
			System.out.println("ID: " + s.getId() + 
					" | Cliente: " + s.getUsuario().getNombre() + 
					" | Para: " + s.getProgramador().getNombre() + 
					" | Mensaje: " + s.getMensaje());
		}
	}
}
