package com.example.giaybongda.utils;

import java.nio.charset.StandardCharsets;

public final class VietQR {// Hàm chính để tạo chuỗi mã QR chuẩn VietQR
    public static String build(String bankBin, String accountNumber, long amount, String description) {
        StringBuilder sb = new StringBuilder();

        // 1. Payload Format Indicator (00)
        sb.append(formatTLV("00", "01"));
        // 2. Point of Initiation Method (01) - 12: Dynamic (dùng 1 lần)
        sb.append(formatTLV("01", "12"));

        // 3. Merchant Account Information (38)
        // GUID (00) + Payment Network (01)
        String guid = formatTLV("00", "A000000727");
        String bankInfo =
                formatTLV("00", bankBin) +
                        formatTLV("01", accountNumber);
        String paymentNetwork = formatTLV("01", bankInfo);
        sb.append(formatTLV("38", guid + paymentNetwork));

        // 4. Transaction Currency (53) - 704 (VND)
        sb.append(formatTLV("53", "704"));

        // 5. Transaction Amount (54)
        if (amount > 0) {
            sb.append(formatTLV("54", String.valueOf(amount)));
        }

        // 6. Country Code (58)
        sb.append(formatTLV("58", "VN"));

        // 7. Additional Data Field (62) - Nội dung chuyển khoản
        if (description != null && !description.isEmpty()) {
            // Tag 08 là Purpose of Transaction (Nội dung)
            String content = formatTLV("08", description);
            sb.append(formatTLV("62", content));
        }

        // 8. CRC16 (63)
        sb.append("6304"); // ID 63, length 04
        String crc = getCRC16(sb.toString());
        sb.append(crc);

        return sb.toString();
    }

        // Hàm format theo chuẩn TLV (Tag - Length - Value)
        private static String formatTLV(String tag, String value) {
            if (value == null) value = "";
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            String length = String.format("%02d", bytes.length);
            return tag + length + value;
        }

        // Hàm tính toán CRC16 (CCITT-FALSE)
        private static String getCRC16(String data) {
            int crc = 0xFFFF;          // Initial value
            int polynomial = 0x1021;   // Polynomial for CCITT

            for (byte b : data.getBytes(StandardCharsets.US_ASCII)) {
                for (int i = 0; i < 8; i++) {
                    boolean bit = ((b >> (7 - i) & 1) == 1);
                    boolean c15 = ((crc >> 15 & 1) == 1);
                    crc <<= 1;
                    if (c15 ^ bit) crc ^= polynomial;
                }
            }
            crc &= 0xFFFF;
            return String.format("%04X", crc).toUpperCase();
}
}