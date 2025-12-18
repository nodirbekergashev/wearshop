package com.powerofwear.common.enums;

/**
 * Represents the lifecycle of a customer's order.
 */
public enum OrderStatus {

    /**
     * The order has been placed by the customer but has not yet been processed.
     * This is the initial state for most new orders.
     */
    PENDING,

    /**
     * The payment attempt failed. The order is on hold.
     */
    PAYMENT_FAILED,

    /**
     * Payment has been confirmed, and the order is being prepared for shipment.
     */
    PROCESSING,

    /**
     * The order has been handed over to the shipping carrier and is in transit.
     */
    SHIPPED,

    /**
     * The shipping carrier has confirmed the delivery of the order.
     */
    DELIVERED,

    /**
     * The order is fully finished, and the return period has expired.
     * This is a final success state.
     */
    COMPLETED,

    /**
     * The order was cancelled either by the customer or an administrator.
     * This is a final failure state.
     */
    CANCELLED,

    /**
     * The customer has requested to return one or more items from the order.
     */
    RETURN_REQUESTED,

    /**
     * The returned items have been received by the seller.
     */
    RETURNED
}
