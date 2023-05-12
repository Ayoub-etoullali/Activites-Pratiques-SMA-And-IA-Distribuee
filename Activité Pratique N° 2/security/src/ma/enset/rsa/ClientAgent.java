package ma.enset.rsa;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClientAgent extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String message = "Hello server";
                String encodedPBK = (String) getArguments()[0];
                try {
                    doJWT2(encodedPBK, message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
    private void doJWT2(String encodedPBK, String message) throws Exception {
        // Decoded
        byte[] decodedPBK = Base64.getDecoder().decode(encodedPBK);
        System.out.println("decodedPBK = "+decodedPBK);
        // KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        System.out.println("keyFactory = "+keyFactory);
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodedPBK));
        System.out.println("publicKey = "+publicKey);
        // Cipher - ENCRYPT_MODE
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        System.out.println("cipher = "+cipher);
        // Encrypt
        byte[] encryptMsg = cipher.doFinal(message.getBytes());
        System.out.println("encryptMsg = "+encryptMsg);
        // Encoded
        String encryptEncodedMsg = Base64.getEncoder().encodeToString(encryptMsg);
        System.out.println("encryptEncodedMsg = "+encryptEncodedMsg);
        // send Message
        ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
        aclMessage.setContent(encryptEncodedMsg);
        aclMessage.addReceiver(new AID("server", AID.ISLOCALNAME));
        send(aclMessage);
    }
}
