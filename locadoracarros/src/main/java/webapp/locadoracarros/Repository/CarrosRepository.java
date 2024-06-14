package webapp.locadoracarros.Repository;

import org.springframework.data.repository.CrudRepository;
import webapp.locadoracarros.Model.Carros;

public interface CarrosRepository extends CrudRepository<Carros, Long> {
    Iterable<Carros> findAll();
}
