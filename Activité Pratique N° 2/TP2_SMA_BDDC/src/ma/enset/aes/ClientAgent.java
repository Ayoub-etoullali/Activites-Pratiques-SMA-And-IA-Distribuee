package ma.enset.aes;

import jade.core.AID;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour((OneShotBehaviour)()-> {
            String message = "Hi! Server";
            String encodePbk = (String) getArguments()[0];
            byte[] decodedPbk = Base64.getDecoder().decode(encodePbk);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodedPbk));
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] encryptMsg = cipher.doFinal(message.getBytes());
                String encryptEncodedMsg = Base64.getEncoder().encodeToString(encryptMsg);
                ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                aclMessage.setContent(encryptEncodedMsg);
                aclMessage.addReceiver(new AID("server", AID.ISLOCALNAME));
                send(aclMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        )
    }
}
