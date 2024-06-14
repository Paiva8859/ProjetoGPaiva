package webapp.locadoracarros.Controller;

import java.sql.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.ClientesRepository;
import webapp.locadoracarros.Repository.CarrosRepository;
import webapp.locadoracarros.Repository.ReservasRepository;

@Controller
public class ReservasController {

    private final ReservasRepository reservasRepository;
    private final ClientesRepository clientesRepository;
    private final CarrosRepository carrosRepository;

    public ReservasController(ReservasRepository reservasRepository, ClientesRepository clientesRepository,
            CarrosRepository carrosRepository) {
        this.reservasRepository = reservasRepository;
        this.clientesRepository = clientesRepository;
        this.carrosRepository = carrosRepository;
    }

    @PostMapping("/reservar-carro")
    public String cadastrarReserva(@RequestParam("cliente") Long clienteId,
            @RequestParam("carro") Long carroId,
            @RequestParam("localRetirada") String localRetirada,
            @RequestParam("dataRetirada") Date dataRetirada,
            @RequestParam("dataDevolu") Date dataDevolu) {
        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + clienteId));
        Carros carro = carrosRepository.findById(carroId)
                .orElseThrow(() -> new IllegalArgumentException("Carro inválido: " + carroId));
        Reservas reserva = new Reservas();
        reserva.setCliente(cliente);
        reserva.setCarro(carro);
        reserva.setLocalRetirada(localRetirada);
        reserva.setDataRetirada(dataRetirada);
        reserva.setDataDevolu(dataDevolu);

        reservasRepository.save(reserva);

        return "internas/carros";
    }

    @GetMapping("/edita-reserva/{idReserva}")
    public String editarReserva(@PathVariable Long idReserva, Model model) {
        Reservas reserva = reservasRepository.findById(idReserva).orElseThrow();
        model.addAttribute("reserva", reserva);
        return "internas/edita-reserva";
    }

    @PostMapping("/editar-reserva/{idReserva}")
    public String editarReservaPost(@PathVariable Long idReserva, @ModelAttribute Reservas reserva) {
        Reservas reservaToUpdate = reservasRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada: " + idReserva));
        reservaToUpdate.setLocalRetirada(reserva.getLocalRetirada());
        reservaToUpdate.setDataRetirada(reserva.getDataRetirada());
        reservaToUpdate.setDataDevolu(reserva.getDataDevolu());
        reservasRepository.save(reservaToUpdate);
        return "internas/lista-reservas";
    }

    @GetMapping("/deletar-reserva/{idReserva}")
    public String deletarReserva(@PathVariable Long idReserva) {
        reservasRepository.deleteById(idReserva);
        return "internas/lista-reservas";
    }
}
