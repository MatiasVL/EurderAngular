package com.switchfully.order.api.orders;

import com.switchfully.order.api.orders.dtos.ItemGroupDto;
import com.switchfully.order.domain.items.prices.Price;
import com.switchfully.order.domain.orders.orderitems.OrderItem;
import com.switchfully.order.infrastructure.exceptions.EntityNotFoundException;
import com.switchfully.order.service.items.ItemService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.switchfully.order.domain.items.ItemTestBuilder.anItem;
import static com.switchfully.order.domain.orders.orderitems.OrderItemTestBuilder.anOrderItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class OrderItemMapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OrderItemMapper orderItemMapper;
    private ItemService itemServiceMock;

    @Before
    public void setupService() {
        itemServiceMock = Mockito.mock(ItemService.class);
        orderItemMapper = new OrderItemMapper(itemServiceMock);
    }

    @Test
    public void toDto() {
        UUID itemId = UUID.randomUUID();
        ItemGroupDto itemGroupDto = orderItemMapper.toDto(
                anOrderItem()
                        .withOrderedAmount(10)
                        .withShippingDateBasedOnAvailableItemStock(20)
                        .withItemId(itemId)
                        .withItemPrice(Price.create(BigDecimal.valueOf(45.50)))
                        .build());

        assertThat(itemGroupDto).isNotNull();
        assertThat(itemGroupDto.getItemId()).isEqualTo(itemId.toString());
        assertThat(itemGroupDto.getOrderedAmount()).isEqualTo(10);
    }

    @Test
    public void toDomain() {
        UUID itemId = UUID.randomUUID();
        Price itemPrice = Price.create(BigDecimal.valueOf(45.95));
        when(itemServiceMock.getItem(itemId))
                .thenReturn(anItem().withPrice(itemPrice).build());

        OrderItem orderItem = orderItemMapper.toDomain(
                new ItemGroupDto()
                        .withItemId(itemId.toString())
                        .withOrderedAmount(2));

        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getItemId()).isEqualTo(itemId);
        assertThat(orderItem.getItemPrice()).isEqualTo(itemPrice);
        assertThat(orderItem.getOrderedAmount()).isEqualTo(2);
        assertThat(orderItem.getShippingDate()).isNotNull().isOfAnyClassIn(LocalDate.class);
    }

    @Test
    public void toDomain_givenItemIdThatDoesNotExist_thenThrowException() {
        UUID itemId = UUID.randomUUID();
        when(itemServiceMock.getItem(itemId))
                .thenReturn(null);

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("During mapping to an order of an item group (for creating a new " +
                "order), the following entity was not found: Item with id = " + itemId.toString());

        orderItemMapper.toDomain(
                new ItemGroupDto()
                        .withItemId(itemId.toString())
                        .withOrderedAmount(2));
    }

}