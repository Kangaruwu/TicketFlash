package com.barox.ticketflash.service.impl;

import com.barox.ticketflash.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_SIZE = 300;

    @Override
    public byte[] generateQrCode(UUID bookingId, UUID qrToken) {
        String content = bookingId.toString() + ":" + qrToken.toString();
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix matrix = writer.encode(
                content,
                BarcodeFormat.QR_CODE,
                QR_SIZE,
                QR_SIZE,
                Map.of(EncodeHintType.MARGIN, 2)
            );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toByteArray();
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code for booking " + bookingId, e);
        }
    }
}
