package com.thomazllr.algafood.domain.entity;

import com.thomazllr.algafood.domain.event.PedidoConfirmadoEvent;
import com.thomazllr.algafood.domain.exception.NegocioException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    private FormaPagamento formaPagamento;

    @ManyToOne
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id")
    private Usuario cliente;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        this.subtotal = this.itens.stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete != null ? this.taxaFrete : BigDecimal.ZERO);
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    @PrePersist
    public void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }

    private void setStatus(StatusPedido status) {

        if (getStatus().naoPodeAlterarPara(status)) {
            throw new NegocioException("Pedido de ID %s não pode ser alterado de %s para %s".formatted(getCodigo(),
                    getStatus().getDescricao(), status.getDescricao()));
        }

        this.status = status;
    }


}
