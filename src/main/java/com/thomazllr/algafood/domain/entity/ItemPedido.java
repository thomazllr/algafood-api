package com.thomazllr.algafood.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Pedido pedido;


    public void calcularPrecoTotal() {
        if (precoUnitario == null || quantidade == null) {
            this.precoTotal = BigDecimal.ZERO;
        } else {
            this.precoTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
    }

}
