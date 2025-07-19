package com.thomazllr.algafood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao;

    private LocalDateTime dataCancelamento;

    private LocalDateTime dataEntrega;

    @ManyToOne
    private FormaPagamento formaPagamento;

    @ManyToOne
    private Restaurante restaurante;

    @ManyToOne
    private Usuario cliente;

    @JsonIgnore
    @Embedded
    private Endereco enderecoEntrega;

    private StatusPedido status;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();
}
