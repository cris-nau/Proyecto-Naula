package ec.edu.ups.ppw.gproyectos.model;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient;

@Entity
@Table(name="TBL_RED_SOCIAL")
public class RedSocial {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="red_id")
    private int id;
    
    @Column(name="red_nombre")
    private String nombre; 
    
    @Column(name="red_url")
    private String url;
    
    @Column(name="red_icono")
    private String icono;
    
    @ManyToOne
    @JoinColumn(name = "prog_id_fk")
    @JsonbTransient
    private Programador programador;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	@JsonbTransient
	public Programador getProgramador() {
		return programador;
	}

	public void setProgramador(Programador programador) {
		this.programador = programador;
	}

    
}
