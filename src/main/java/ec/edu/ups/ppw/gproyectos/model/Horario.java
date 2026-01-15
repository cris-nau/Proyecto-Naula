package ec.edu.ups.ppw.gproyectos.model;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbTransient;

@Entity
@Table(name="TBL_HORARIO")
public class Horario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hor_id")
    private int id;
    
    @Column(name="hor_dia")
    private String dia; 
    
    @Column(name="hor_hora_inicio")
    private String horaInicio;
    
    @Column(name="hor_hora_fin")
    private String horaFin;
    
    
    @ManyToOne
    @JoinColumn(name = "prog_id_fk")
    private Programador programador;

    @JsonbTransient
    public Programador getProgramador() { return programador; }
    public void setProgramador(Programador programador) { this.programador = programador; }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
    
    
}
