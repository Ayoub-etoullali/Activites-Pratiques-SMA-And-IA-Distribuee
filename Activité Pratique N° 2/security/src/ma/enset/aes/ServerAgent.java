package ma.enset.aes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        String password = (String) getArguments()[0];
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    String encryptEncodedMsg = message.getContent();
                    System.out.println("encryptEncodedMsg = " + encryptEncodedMsg);

                    try {
                        String receivedMsg=noJWT(encryptEncodedMsg, password);
                        System.out.println("receivedMsg = "+receivedMsg);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    block();
                }
            }
        });
    }

    private String noJWT(String encryptEncodedMsg, String password) throws Exception {
        // Decode
        byte[] encryptMsg = Base64.getDecoder().decode(encryptEncodedMsg);
        System.out.println("encryptMsg = " + encryptMsg);
        String Msg = new String(encryptMsg);
        System.out.println("Msg = " + Msg);
        // Secret Key
        SecretKey secretKey = new SecretKeySpec(password.getBytes(), "AES");
        System.out.println("secretKey = " + secretKey);
        // Cipher - DECRYPT_MODE
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        System.out.println("cipher = " + cipher);
        // Decrypt
        byte[] decryptMsg = cipher.doFinal(encryptMsg);
        System.out.println("decryptMsg = " + decryptMsg);

        return new String(decryptMsg);
    }
}
