package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.dto.VendaDiaria;
import com.thomazllr.algafood.domain.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
}
