package br.dev.paulovictorsilva.shop_validator.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.dev.paulovictorsilva.shop_validator.dto.ShopDto;
import br.dev.paulovictorsilva.shop_validator.dto.ShopItemDto;
import br.dev.paulovictorsilva.shop_validator.model.Product;
import br.dev.paulovictorsilva.shop_validator.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveKafkaMessage
{
    private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";
    private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, ShopDto> kafkaTemplate;

    @KafkaListener(topics = SHOP_TOPIC_NAME, groupId = "group")
    public void listenShopTopic(ShopDto shopDto)
    {
        try
        {
            log.info("Compra recebida no tópico: {}.", shopDto.getIdentifier());

            boolean success = true;

            for(ShopItemDto item : shopDto.getItems())
            {
                Product product = productRepository.findByIdentifier(item.getProductIdentifier());

                if(!isValidShop(item, prodct))
                {
                    shopError(shopDto);
                    success = false;
                    break;
                }
            }

            if (success)
            {
                shopSuccess(shopDto);
            }
        } catch (Exception e)
        {
            log.error("Erro no processamento da compra {}.", shopDto.getIdentifier());
        }
    }

    private boolean isValidShop(ShopItemDto item, Product product)
    {
        return product != null && product.getAmount() >= item.getAmount();
    }

    private void shopError(ShopDto shopDto)
    {
        log.info("Erro no processamernto da compra {}.", shopDto.getIdentifier());

        shopDto.setStatus("ERROR");

        kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, shopDto);
    }

    private void shopSuccess(ShopDto shopDto)
    {
        log.info("Compra {} efetuadsa com sucesso.", shopDto.getIdentifier());

        shopDto.setStatus("SUCCESS");

        kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, shopDto);
    }
}
