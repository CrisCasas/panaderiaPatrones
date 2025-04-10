package com.panaderia.util;

import java.security.MessageDigest;

public class Encriptador {
    
    public static String encriptarSHA256(String texto){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes());

            StringBuilder hexString = new StringBuilder();
            for(byte b :hash){
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
