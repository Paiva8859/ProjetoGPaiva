package webapp.locadoracarros.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webapp.locadoracarros.Model.Clientes;

import java.util.List;

@Repository
public interface ClientesRepository extends CrudRepository<Clientes, Long> {
    @Query("SELECT c FROM Clientes c WHERE c.idCliente IN (SELECT r.cliente.idCliente FROM Reservas r GROUP BY r.cliente.idCliente HAVING COUNT(r.cliente.idCliente) > 1)")
    List<Clientes> findClientesComMaisDeUmAluguel();

    @Query("SELECT r.cliente.idCliente, COUNT(r.cliente.idCliente) as count FROM Reservas r GROUP BY r.cliente.idCliente ORDER BY count DESC")
    List<Object[]> findClienteComMaisAlugueis();
}
