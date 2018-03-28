package com.switchfully.order.service.orders;

import com.switchfully.order.IntegrationTest;
import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerRepository;
import com.switchfully.order.domain.items.Item;
import com.switchfully.order.domain.items.ItemRepository;
import com.switchfully.order.domain.orders.Order;
import com.switchfully.order.domain.orders.OrderRepository;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;
import static com.switchfully.order.domain.items.ItemTestBuilder.anItem;
import static com.switchfully.order.domain.orders.OrderTestBuilder.anOrder;
import static com.switchfully.order.domain.orders.orderitems.OrderItemTestBuilder.anOrderItem;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceIntegrationTest extends IntegrationTest {

    @Inject
    private OrderService orderService;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private ItemRepository itemRepository;

    @After
    public void resetDatabase() {
        orderRepository.reset();
        customerRepository.reset();
        itemRepository.reset();
    }

    @Test
    public void createOrder() {
        Item existingItem = itemRepository.save(anItem().build());
        Customer existingCustomer = customerRepository.save(aCustomer().build());
        Order orderToCreate = anOrder()
                .withCustomerId(existingCustomer.getId())
                .withOrderItems(anOrderItem()
                        .withItemId(existingItem.getId())
                        .build())
                .build();

        Order createdOrder = orderService.createOrder(orderToCreate);

        assertThat(createdOrder.getId()).isNotNull();
        assertThat(createdOrder).isEqualToComparingFieldByFieldRecursively(orderToCreate);
    }

    @Test
    public void getOrdersForCustomer(){
        Customer existingCustomer1 = customerRepository.save(aCustomer().build());
        Customer existingCustomer2 = customerRepository.save(aCustomer().build());
        Item existingItem1 = itemRepository.save(anItem().build());
        Item existingItem2 = itemRepository.save(anItem().build());
        Order order1 = orderRepository.save(anOrder()
                .withOrderItems(anOrderItem().withItemId(existingItem1.getId()).build(),
                        anOrderItem().withItemId(existingItem2.getId()).build())
                .withCustomerId(existingCustomer1.getId()).build());
        Order order2 = orderRepository.save(anOrder()
                .withOrderItems(anOrderItem().withItemId(existingItem2.getId()).build())
                .withCustomerId(existingCustomer2.getId()).build());
        Order order3 = orderRepository.save(anOrder()
                .withOrderItems(anOrderItem().withItemId(existingItem1.getId()).build())
                .withCustomerId(existingCustomer2.getId()).build());

        List<Order> ordersForCustomer = orderService.getOrdersForCustomer(existingCustomer2.getId());

        assertThat(ordersForCustomer).containsExactlyInAnyOrder(order2, order3);
    }

}