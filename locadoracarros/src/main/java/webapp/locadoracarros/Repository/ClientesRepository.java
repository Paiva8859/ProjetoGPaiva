package webapp.locadoracarros.Repository;

import org.springframework.data.repository.CrudRepository;
import webapp.locadoracarros.Model.Clientes;

public interface ClientesRepository extends CrudRepository<Clientes, Long> {
    
}
