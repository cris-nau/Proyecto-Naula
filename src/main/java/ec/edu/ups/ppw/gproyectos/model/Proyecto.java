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
    
    @ManyToOne
    @JoinColumn(name = "prog_id_fk")
    private Programador programador;

    // Getters y Setters
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
    @JsonbTransient
    public Programador getProgramador() { return programador; }
    public void setProgramador(Programador programador) { this.programador = programador; }
}
