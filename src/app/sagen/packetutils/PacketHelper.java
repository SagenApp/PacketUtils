package app.sagen.packetutils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class PacketHelper {

    private static Key AES_KEY;

    static {
        try {
            AES_KEY = new SecretKeySpec("Sagen.App-KEY-1234653asdfasdfasd".getBytes(StandardCharsets.UTF_8), "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPacket(Packet packet) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(packet);
            os.flush();
            String output = encodeBase64(encrypt(out.toByteArray()));
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Packet getPacket(String data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(derypt(decodeBase64(data)));
            ObjectInputStream is = new ObjectInputStream(in);
            Packet output = (Packet) is.readObject();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, AES_KEY);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] derypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, AES_KEY);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String encodeBase64(byte[] data) {
        return new String(Base64.getEncoder().encode(data));
    }

    private static byte[] decodeBase64(String data) {
        return Base64.getDecoder().decode(data.getBytes());
    }
}
