package unpa.entity.materias;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
    @Table(name = "imparten")
    public class MateriaAsignadaDetails {

        @EmbeddedId
        private ImpartenId id;

        @Column(name = "Id_Pro_FK")
        private String idProfesor;

        @Column(name = "Fechap1_Imp")
        private Date fechaParcial1;

        @Column(name = "Fechap2_Imp")
        private Date fechaParcial2;

        @Column(name = "Fechap3_Imp")
        private Date fechaParcial3;

        @Column(name = "Fechaf_Imp")
        private Date fechaFinal;

        @Column(name = "Fechae1_Imp")
        private Date fechaExtra1;

        @Column(name = "Fechae2_Imp")
        private Date fechaExtra2;

        @Column(name = "Fechaesp_Imp")
        private Date fechaEspecial;

        // getters y setters
    }
