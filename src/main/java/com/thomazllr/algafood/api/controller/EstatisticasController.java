package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.entity.dto.VendaDiaria;
import com.thomazllr.algafood.domain.filter.VendaDiariaFilter;
import com.thomazllr.algafood.domain.service.VendaQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(
            VendaDiariaFilter filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        String normalized = timeOffset == null ? "+00:00" : timeOffset.trim();

        normalized = normalized.replace(' ', '+').replace('_', '+');

        if (!normalized.startsWith("+") && !normalized.startsWith("-")) {
            normalized = "+" + normalized;
        }

        if (normalized.matches("^[+-]\\d{4}$")) {
            normalized = normalized.substring(0, 3) + ":" + normalized.substring(3);
        }

        if (!normalized.matches("^[+-]\\d{2}:\\d{2}$")) {
            normalized = "+00:00";
        }

        return vendaQueryService.consultarVendasDiarias(filtro, normalized);
    }
}