package com.barox.ticketflash.service;

import java.util.UUID;

public interface QrCodeService {
    /**
     * Generates a QR code PNG image encoding "{bookingId}:{qrToken}".
     *
     * @param bookingId booking UUID
     * @param qrToken   secret QR token stored on the booking
     * @return PNG image bytes
     */
    byte[] generateQrCode(UUID bookingId, UUID qrToken);
}
