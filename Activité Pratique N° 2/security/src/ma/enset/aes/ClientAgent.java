package ma.enset.aes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ClientAgent extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String message = "Hello server";
                String password = (String) getArguments()[0];

                try {
                    doJWT(password, message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void doJWT(String password, String message) throws Exception {
        // Secret Key
        SecretKey secretKey = new SecretKeySpec(password.getBytes(), "AES");
        System.out.println("secretKey = "+secretKey);
        // Cipher - ENCRYPT_MODE
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
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
