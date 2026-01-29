package ec.edu.ups.ppw.gproyectos.bussines;

import ec.edu.ups.ppw.gproyectos.dao.ProgramadorDAO;
import ec.edu.ups.ppw.gproyectos.dao.RedSocialDAO;
import ec.edu.ups.ppw.gproyectos.model.Programador;
import ec.edu.ups.ppw.gproyectos.model.RedSocial;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionRedesSociales {

	@Inject
    private RedSocialDAO daoRedSocial;
    
    @Inject
    private ProgramadorDAO daoProgramador;

    public void agregarRedSocial(RedSocial red, int programadorId) throws Exception {
        Programador p = daoProgramador.read(programadorId);
        if(p == null) throw new Exception("Programador inexistente");
        
        red.setProgramador(p);
        daoRedSocial.insert(red);
    }

    public void eliminarRedSocial(int id) {
        daoRedSocial.delete(id);
    }
}
