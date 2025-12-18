package com.powerofwear.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.powerofwear.common.enums.OrderStatus;
import com.powerofwear.dto.address.AddressCreateDto;
import com.powerofwear.dto.order.OrderResponseDto;
@Service
public interface OrderService {

    /**
     * Creates a new order from the user's current cart. This is the most
     * critical method. It should handle payment processing, stock reduction,
     * and clearing the cart.
     *
     * @param userId             The ID of the user placing the order.
     * @return A DTO representing the newly created, permanent order.
     */
    OrderResponseDto createOrder(Long userId, AddressCreateDto addressCreateDto);

    /**
     * Retrieves a single order by its ID. Should include security checks
     * to ensure the user owns the order or is an admin.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The details of the requested order.
     */
    OrderResponseDto getOrderById(Long orderId);

    /**
     * Retrieves a paginated history of all orders for a specific user.
     *
     * @param userId The ID of the user.
     * @param page   The page number to retrieve.
     * @param size   The number of orders per page.
     * @return A paginated list of the user's orders.
     */
    Page<OrderResponseDto> getOrderHistoryForUser(Long userId, int page, int size);

    /**
     * Allows a user to cancel an order, but only if it has not yet been
     * shipped (e.g., its status is PENDING).
     *
     * @param orderId The ID of the order to cancel.
     * @return The updated order with a CANCELED status.
     */
    OrderResponseDto cancelOrder(Long orderId,String reason);


    /**
     * Retrieves all orders in the system, with filtering and pagination.
     * Intended for administrative use.
     *
     * @param status Filter by a specific order status (e.g., PENDING, SHIPPED).
     * @param page   The page number.
     * @param size   The page size.
     * @return A paginated list of all orders matching the criteria.
     */
    Page<OrderResponseDto> getAllOrders(OrderStatus status, int page, int size);

    /**
     * Updates the status of an existing order. Used by admins to manage
     * the fulfillment process (e.g., moving from PENDING to SHIPPED).
     *
     * @param orderId   The ID of the order to update.
     * @param newStatus The new status to set for the order.
     * @return The updated order DTO.
     */
    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus newStatus);

}
