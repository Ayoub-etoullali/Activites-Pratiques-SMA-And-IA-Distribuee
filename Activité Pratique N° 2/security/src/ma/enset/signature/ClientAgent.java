package ma.enset.signature;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ClientAgent extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String document = "document 1";
                String encodedPRK = (String) getArguments()[0];
                try {
                    doJWT(encodedPRK, document);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void doJWT(String encodedPRK, String document) throws Exception {
        // Decoded
        byte[] decodedPRK = Base64.getDecoder().decode(encodedPRK);
        System.out.println("decodedPRK = "+decodedPRK);
        // KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        System.out.println("keyFactory = "+keyFactory);
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPRK));
        System.out.println("privateKey = "+privateKey);
        // Signature
        Signature signature=Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(document.getBytes());
        byte[] sign = signature.sign();
        // Encoded
        String encodedSign = Base64.getEncoder().encodeToString(sign);
        System.out.println("encodedSign = "+encodedSign);
        String encodedDoc = Base64.getEncoder().encodeToString(document.getBytes());
        System.out.println("encodedSign = "+encodedSign);
        // Document Signé
        String docSign=encodedDoc+"__.__"+encodedSign;
        System.out.println("docSign = "+docSign);
        // send Message
        ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
        aclMessage.setContent(docSign);
        aclMessage.addReceiver(new AID("server", AID.ISLOCALNAME));
        send(aclMessage);
    }
}
