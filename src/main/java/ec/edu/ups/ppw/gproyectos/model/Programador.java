package ec.edu.ups.ppw.gproyectos.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="TBL_PROGRAMADOR")
public class Programador {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prog_id")
    private int id;

    @Column(name="prog_nombre")
    private String nombre; 

    @Column(name="prog_contacto")
    private String contacto;
    
    @Column(name="prog_email", unique=true)
    private String email;

    @Column(name="prog_especialidad")
    private String especialidad;
    
    @Column(name="prog_descripcion")
    private String descripcion;
    
    @Column(name="prog_activo")
    private boolean activo;
    
    @Column(name="prog_foto")
    private String foto;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "programador")
    private List<Horario> horarios;

    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "programador")
    private List<RedSocial> redesSociales;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "programador")
    private List<Proyecto> proyectos;
    
    

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}

	public List<RedSocial> getRedesSociales() {
		return redesSociales;
	}

	public void setRedesSociales(List<RedSocial> redesSociales) {
		this.redesSociales = redesSociales;
	}
    
    
}
