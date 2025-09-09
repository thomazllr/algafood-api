package com.thomazllr.algafood.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class FotoProduto {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    public Long getRestauranteId() {
        if (produto != null) {
            return produto.getRestaurante().getId();
        }
        return null;
    }

}
