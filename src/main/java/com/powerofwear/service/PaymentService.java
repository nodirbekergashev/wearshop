package com.powerofwear.service;

import com.powerofwear.common.enums.PaymentStatus;
import com.powerofwear.dto.payment.PaymentInitiationResponseDto;
import com.powerofwear.exceptions.PaymentProcessingException;

import java.math.BigDecimal;

/**
 * Orchestration service for handling the full payment lifecycle.
 * This service coordinates between the application logic and the external Payment Gateway (PG).
 */
public interface PaymentService {

    /**
     * Initiates a payment request with the external gateway.
     * This method typically creates a session and returns a client-side token or redirect URL.
     *
     * @param orderId The internal ID of the order being paid.
     * @param amount The total amount to charge.
     * @return A DTO containing client-side information (e.g., secret, redirect URL).
     * @throws PaymentProcessingException if the gateway rejects the initiation request.
     */
    PaymentInitiationResponseDto initiatePayment(Long orderId, BigDecimal amount) throws PaymentProcessingException;

    /**
     * Processes a successful payment confirmation received from a webhook or redirect.
     * This is the critical final step where the Payment entity status is updated, and the Order is finalized.
     *
     * @param gatewayRef The unique reference ID from the payment gateway.
     * @param finalStatus The confirmed status (should be SUCCESS or REFUNDED).
     * @throws PaymentProcessingException if the payment reference is not found or status update fails.
     */
    void finalizePayment(String gatewayRef, PaymentStatus finalStatus) throws PaymentProcessingException;

    /**
     * Handles webhook events sent by the payment gateway (e.g., Stripe, PayPal).
     * This method acts as a transactional endpoint for external systems.
     *
     * @param event The raw webhook event data (already parsed).
     */
//    void handleWebhook(WebhookEventDto event);

    /**
     * Triggers a refund request to the external payment gateway.
     * * @param paymentId The internal ID of the payment to refund.
     * @param refundAmount The amount to refund.
     */
    void initiateRefund(Long paymentId, BigDecimal refundAmount);
}
