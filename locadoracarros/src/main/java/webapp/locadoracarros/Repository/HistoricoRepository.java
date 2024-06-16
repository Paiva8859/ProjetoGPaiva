package webapp.locadoracarros.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Model.Historico;
import webapp.locadoracarros.Model.Clientes;

@Repository
public interface HistoricoRepository extends CrudRepository<Historico, Long> {
    
    List<Historico> findAll();

    List<Historico> findByDataRetirada(LocalDate dataPesquisa);

    List<Historico> findByClienteAndDataRetiradaBetween(Clientes cliente, LocalDate dataInicial, LocalDate dataFinal);

    List<Historico> findByCarro(Carros carro);

    @Query("SELECT h FROM Historico h WHERE h.dataRetirada BETWEEN :dataInicio AND :dataFim")
    List<Historico> findByDataRetiradaBetween(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);
}
