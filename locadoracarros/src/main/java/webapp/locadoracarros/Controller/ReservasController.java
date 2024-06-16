package webapp.locadoracarros.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.ClientesRepository;
import webapp.locadoracarros.Repository.CarrosRepository;
import webapp.locadoracarros.Repository.ReservasRepository;
import webapp.locadoracarros.Repository.HistoricoRepository;
import webapp.locadoracarros.Model.Historico; // Importe necessário para o histórico

@Controller
public class ReservasController {

    private final ReservasRepository reservasRepository;
    private final ClientesRepository clientesRepository;
    private final CarrosRepository carrosRepository;
    private final HistoricoRepository historicoRepository; // Adicionando o histórico repository

    public ReservasController(ReservasRepository reservasRepository, ClientesRepository clientesRepository,
            CarrosRepository carrosRepository, HistoricoRepository historicoRepository) {
        this.reservasRepository = reservasRepository;
        this.clientesRepository = clientesRepository;
        this.carrosRepository = carrosRepository;
        this.historicoRepository = historicoRepository; // Inicializando o histórico repository
    }

    @PostMapping("/reservar-carro")
    public String cadastrarReserva(@RequestParam("cliente") Long clienteId,
            @RequestParam("carro") Long carroId,
            @RequestParam("localRetirada") String localRetirada,
            @RequestParam("dataRetirada") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataRetirada,
            @RequestParam("dataDevolu") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataDevolu) {
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
        carro.setDisponivel(false);
        reservasRepository.save(reserva);

        // Criando e salvando o histórico com os mesmos dados da reserva
        Historico historico = new Historico();
        historico.setCliente(cliente);
        historico.setCarro(carro);
        historico.setLocalRetirada(localRetirada);
        historico.setDataRetirada(dataRetirada);
        historico.setDataDevolu(dataDevolu);
        historicoRepository.save(historico);

        return "index";
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
        Reservas reserva = reservasRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada com ID: " + idReserva));
        Carros carro = reserva.getCarro();
        carro.setDisponivel(true);
        carrosRepository.save(carro);
        reservasRepository.deleteById(idReserva);

        return "internas/lista-reservas";
    }

    @GetMapping("/pesquisar-reservas")
    public String pesquisarReservasPorData(
            @RequestParam("dataPesquisa") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataPesquisa, Model model) {

        List<Reservas> reservas = reservasRepository.findByDataRetirada(dataPesquisa);

        model.addAttribute("reservas", reservas);

        return "internas/lista-reservas-data";
    }

    @GetMapping("/lista-reservas-data")
    public String listaReservasData() {
        return "internas/lista-reservas-data";
    }

    @GetMapping("/lista-carros-cliente")
    public String listaCarrosCliente(Model model) {
        model.addAttribute("reservas", reservasRepository.findAll());
        return "internas/lista-carros-cliente";
    }

    @GetMapping("/lista-reservas-data-cliente")
    public String listaReservasDataCliente(Model model) {
        List<Clientes> clientes = (List<Clientes>) clientesRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "internas/lista-reservas-data-cliente";
    }

    @GetMapping("/pesquisar-reservas-data-cliente")
    public String pesquisaReservasDataCliente(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("dataRetirada") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataRetirada,
            @RequestParam("dataDevolu") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataDevolu,
            Model model) {

        List<Clientes> clientes = (List<Clientes>) clientesRepository.findAll();
        model.addAttribute("clientes", clientes);

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId));

        List<Reservas> reservas = reservasRepository.findByClienteAndDataRetiradaBetween(cliente, dataRetirada,
                dataDevolu);

        model.addAttribute("cliente", cliente);
        model.addAttribute("reservas", reservas);

        return "internas/lista-reservas-data-cliente";
    }

    @GetMapping("/carros-nunca-reservados")
    public String carrosNuncaReservados(Model model) {
        // Busca todos os carros cadastrados
        List<Carros> carrosCadastrados = (List<Carros>) carrosRepository.findAll();

        // Busca os carros que nunca foram reservados
        List<Carros> carrosNuncaReservados = new ArrayList<>();
        for (Carros carro : carrosCadastrados) {
            // Verifica se há reservas para o carro na tabela de histórico
            List<Historico> historicoCarro = historicoRepository.findByCarro(carro);
            if (historicoCarro.isEmpty()) {
                carrosNuncaReservados.add(carro);
            }
        }

        model.addAttribute("carrosNuncaReservados", carrosNuncaReservados);

        return "internas/carros-nunca-reservados";
    }

    @GetMapping("/receita-data")
    public String receitaData() {
        return "internas/receita-data";
    }
    

    @GetMapping("/calcular-receita")
    @ResponseBody
    public String calcularReceita(
            @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        if (dataInicial == null || dataFinal == null || dataFinal.isBefore(dataInicial)) {
            return "0";
        }

        LocalDateTime dataInicio = dataInicial.atStartOfDay();
        LocalDateTime dataFim = dataFinal.atTime(LocalTime.MAX); // Use o final do dia da dataFinal

        List<Historico> historicoList = historicoRepository.findByDataRetiradaBetween(dataInicio, dataFim);

        BigDecimal receitaTotal = BigDecimal.ZERO;

        for (Historico historico : historicoList) {
            LocalDate dataRetirada = historico.getDataRetirada().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate dataDevolu = historico.getDataDevolu().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long diasAluguel = ChronoUnit.DAYS.between(dataRetirada, dataDevolu) + 1;

            BigDecimal receitaHistorico = BigDecimal.valueOf(diasAluguel * 40);

            receitaTotal = receitaTotal.add(receitaHistorico);
        }

        return receitaTotal.toString();
    }
}
