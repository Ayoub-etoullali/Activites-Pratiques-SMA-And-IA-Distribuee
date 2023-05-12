package ma.enset.rsa;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {

    @Override
    protected void setup() {
        String encodedPRK = (String) getArguments()[0];

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    String encryptEncodedMsg = message.getContent();
                    System.out.println("encryptEncodedMsg = " + encryptEncodedMsg);

                    try {
                        String receivedMsg=noJWT(encryptEncodedMsg, encodedPRK);
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
    private String noJWT(String encryptEncodedMsg, String encodedPRK) throws Exception {
        // Decode
        byte[] encryptMsg = Base64.getDecoder().decode(encryptEncodedMsg); // Base64.getUrlDecoder()
        byte[] decodedPRK = Base64.getDecoder().decode(encodedPRK);
        System.out.println("encryptMsg = " + encryptMsg);
        // KeyFactory
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        System.out.println("keyFactory = " + keyFactory);
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPRK));
        System.out.println("privateKey = " + privateKey);
        // Cipher - DECRYPT_MODE
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        System.out.println("cipher = " + cipher);
        // Decrypt
        byte[] decryptMsg = cipher.doFinal(encryptMsg);
        System.out.println("decryptMsg = " + decryptMsg);

        return new String(decryptMsg);
    }
}
