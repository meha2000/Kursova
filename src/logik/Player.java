package logik;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Player implements Serializable {
    private String username;
    private String passwordHash;

    private int bestScore = 0;

    public Player(String username, String plainPassword) {
        this.username = username;
        this.passwordHash = hashPassword(plainPassword);
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean checkPassword(String plainPassword) {
        return this.passwordHash.equals(hashPassword(plainPassword));
    }

    public static String hashPassword(String plainPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(plainPassword.getBytes());
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public int getBestScore() {
        return bestScore;
    }
    public void updateBestScore(int newScore) {
        if (newScore > this.bestScore) {
            this.bestScore = newScore;
        }
    }
}