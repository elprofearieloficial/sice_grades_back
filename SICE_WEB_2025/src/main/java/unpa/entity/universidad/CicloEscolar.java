package unpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ciclosescolares")
@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class CicloEscolar {

    @Id
    private String idCic;

    private LocalDate inicioCic;
    private LocalDate finCic;
    private LocalDate iniciosemsantaCic;
    private LocalDate finsemsantaCic;
    private LocalDate inicioveranoCic;
    private LocalDate finveranoCic;
    private LocalDate inicionavidadCic;
    private LocalDate finnavidadCic;
    private String diasdescansoCic;
}

