package ec.edu.ups.ppw.gproyectos.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="TBL_SOLICITUD")
public class Solicitud {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sol_id")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sol_fecha")
    private Date fechaSolicitud;
    
    @Column(name="sol_estado")
    private String estado;
    
    @Column(name="sol_mensaje")
    private String mensaje;
    
    
    @ManyToOne
    @JoinColumn(name = "usu_id_fk")
    private Usuario usuario;
    
    
    @ManyToOne
    @JoinColumn(name = "prog_id_fk")
    private Programador programador;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}


	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Programador getProgramador() {
		return programador;
	}


	public void setProgramador(Programador programador) {
		this.programador = programador;
	}
    
    
}
