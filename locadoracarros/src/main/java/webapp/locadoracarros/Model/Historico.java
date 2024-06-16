package webapp.locadoracarros.Model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Historico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idHistorico;

    @ManyToOne
    @JoinColumn(name = "idCarro") 
    private Carros carro;
    
    @ManyToOne
    @JoinColumn(name = "idCliente") 
    private Clientes cliente;

    private String localRetirada;
    private Date dataRetirada;
    private Date dataDevolu;

    // Getters and setters
    public long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(long idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Carros getCarro() {
        return carro;
    }

    public void setCarro(Carros carro) {
        this.carro = carro;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public String getLocalRetirada() {
        return localRetirada;
    }

    public void setLocalRetirada(String localRetirada) {
        this.localRetirada = localRetirada;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Date getDataDevolu() {
        return dataDevolu;
    }

    public void setDataDevolu(Date dataDevolu) {
        this.dataDevolu = dataDevolu;
    }

    
}
