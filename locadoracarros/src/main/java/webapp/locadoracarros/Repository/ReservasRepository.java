package webapp.locadoracarros.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import webapp.locadoracarros.Model.Reservas;

public interface ReservasRepository extends CrudRepository<Reservas, Long> {
    List<Reservas> findAll();
}
