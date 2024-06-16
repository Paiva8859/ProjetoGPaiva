package webapp.locadoracarros.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Repository.ClientesRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientesController {

    private final ClientesRepository clientesRepository;

    public ClientesController(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @PostMapping("/cadastrar-clientes")
    public String cadastrarCliente(@ModelAttribute Clientes cliente) {
        clientesRepository.save(cliente);
        return "internas/clientes";
    }

    @GetMapping("/edita-cliente/{idCliente}")
    public String editarCliente(@PathVariable Long idCliente, Model model) {
        Clientes cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + idCliente));
        model.addAttribute("cliente", cliente);
        return "internas/edita-clientes";
    }

    @GetMapping("/editar-cliente/{idCliente}")
    public String exibirFormularioEditarCliente(@PathVariable Long idCliente, Model model) {
        Clientes cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + idCliente));
        model.addAttribute("cliente", cliente);
        return "internas/edita-clientes";
    }

    @PostMapping("/editar-cliente/{idCliente}")
    public String editarCliente(@PathVariable Long idCliente, @ModelAttribute Clientes cliente) {
        Clientes clienteToUpdate = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + idCliente));
        clienteToUpdate.setNome(cliente.getNome());
        clienteToUpdate.setCpf(cliente.getCpf());
        clienteToUpdate.setEmail(cliente.getEmail());
        clienteToUpdate.setEndereco(cliente.getEndereco());
        clienteToUpdate.setTelefone(cliente.getTelefone());
        clienteToUpdate.setDataNas(cliente.getDataNas());
        clientesRepository.save(clienteToUpdate);
        return "internas/lista-clientes";
    }

    @GetMapping("/deletar-cliente/{idCliente}")
    public String deletarCliente(@PathVariable Long idCliente) {
        clientesRepository.deleteById(idCliente);
        return "internas/lista-clientes";
    }

    @GetMapping("/clientes-frequentes")
public String clientesFrequentes(Model model) {
    List<Clientes> clientesComMaisDeUmAluguel = clientesRepository.findClientesComMaisDeUmAluguel();
    List<Object[]> clientesComMaisAlugueis = clientesRepository.findClienteComMaisAlugueis();

    model.addAttribute("clientesComMaisDeUmAluguel", clientesComMaisDeUmAluguel);

    if (!clientesComMaisAlugueis.isEmpty()) {
        Object[] clienteMaisAlugueis = clientesComMaisAlugueis.get(0); // Pega o primeiro array/tupla
        Long clienteId = (Long) clienteMaisAlugueis[0]; // Obtém o ID do cliente
        Clientes cliente = clientesRepository.findById(clienteId).orElse(null); // Busca o cliente pelo ID

        model.addAttribute("clienteComMaisAlugueis", cliente);
        model.addAttribute("clienteMaisAlugouCount", clienteMaisAlugueis[1]); // Contagem de aluguéis
    } else {
        model.addAttribute("clienteComMaisAlugueis", null);
        model.addAttribute("clienteMaisAlugouCount", 0);
    }

    return "internas/clientes-frequentes";
}

}