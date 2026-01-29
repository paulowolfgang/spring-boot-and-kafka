package br.dev.paulovictorsilva.shop_validator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ShopDto
{
    private String identifier;
    private LocalDate dateShop;
    private String status;
    private List<ShopItemDto> items = new ArrayList<>();
}
