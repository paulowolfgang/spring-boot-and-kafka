package br.dev.paulovictorsilva.shop_api.controller;

import br.dev.paulovictorsilva.shop_api.dto.ShopDto;
import br.dev.paulovictorsilva.shop_api.model.Shop;
import br.dev.paulovictorsilva.shop_api.model.ShopItem;
import br.dev.paulovictorsilva.shop_api.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController
{
    private final ShopRepository shopRepository;

    @GetMapping
    public List<ShopDto> getShop()
    {
        return shopRepository
                .findAll()
                .stream()
                .map(shop -> ShopDto.convert(shop))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ShopDto saveShop(@RequestBody ShopDto shopDto)
    {
        shopDto.setIdentifier(UUID.randomUUID().toString());
        shopDto.setDateShop(LocalDate.now());
        shopDto.setStatus("PENDING");
        shopDto.setBuyerIdentifier(UUID.randomUUID().toString());

        Shop shop = Shop.convert(shopDto);

        for(ShopItem shopItem : shop.getItems())
        {
            shopItem.setShop(shop);
        }

        return ShopDto.convert(shopRepository.save(shop));
    }
}
