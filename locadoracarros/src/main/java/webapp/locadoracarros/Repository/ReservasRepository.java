package webapp.locadoracarros.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Model.Reservas;

public interface ReservasRepository extends CrudRepository<Reservas, Long> {
    List<Reservas> findAll();

    List<Reservas> findByDataRetirada(Date dataPesquisa);

    List<Reservas> findByClienteAndDataRetiradaBetween(Clientes cliente, Date dataInicial, Date dataFinal);
}
