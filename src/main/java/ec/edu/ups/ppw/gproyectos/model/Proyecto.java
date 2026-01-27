package ec.edu.ups.ppw.gproyectos.model;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient;

@Entity
@Table(name="TBL_PROYECTO")
public class Proyecto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="proy_id") 
	private int id;
    
    @Column(name="proy_titulo")
    private String titulo;
    
    @Column(name="proy_descripcion", length = 500) 
    private String descripcion;
    
    @Column(name="proy_url_imagen") 
    private String urlImagen;
    
    @Column(name="proy_url_repo") 
    private String urlRepositorio;
    
    @Column(name="proy_tecnologias")
    private String tecnologias;

    @Column(name="proy_tipo")
    private String tipo;
    
    @ManyToOne
    @JoinColumn(name = "prog_id_fk")
    private Programador programador;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public String getUrlRepositorio() { return urlRepositorio; }
    public void setUrlRepositorio(String urlRepositorio) { this.urlRepositorio = urlRepositorio; }

    // --- NUEVOS Getters y Setters ---
    public String getTecnologias() { return tecnologias; }
    public void setTecnologias(String tecnologias) { this.tecnologias = tecnologias; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    // --------------------------------

    @JsonbTransient
    public Programador getProgramador() { return programador; }
    public void setProgramador(Programador programador) { this.programador = programador; }
}
