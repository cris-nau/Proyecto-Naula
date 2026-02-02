package ec.edu.ups.ppw.gproyectos.model;

import jakarta.persistence.*;

import java.io.Serializable;

import jakarta.json.bind.annotation.JsonbTransient;

@Entity
@Table(name="TBL_HORARIO")
public class Horario implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String diaSemana; // Ejemplo: "Lunes", "Martes"
    private String horaInicio; // Ejemplo: "08:00"
    private String horaFin;    // Ejemplo: "10:00"
    private String modalidad;  // "Virtual" o "Presencial"
    
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

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
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

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	@JsonbTransient
	public Programador getProgramador() {
		return programador;
	}

	public void setProgramador(Programador programador) {
		this.programador = programador;
	}

    
    
}
