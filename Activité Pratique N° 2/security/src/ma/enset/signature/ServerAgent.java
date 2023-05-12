package ma.enset.signature;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {

    @Override
    protected void setup() {
        String encodedPBK = (String) getArguments()[0];

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage DocSign = receive();
                if (DocSign != null) {
                    String encodedDocSign = DocSign.getContent();
                    System.out.println("encodedDoc = " + encodedDocSign);

                    try {
                        noJWT(encodedDocSign, encodedPBK);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    block();
                }
            }
        });
    }

    private void noJWT(String encodedDocSign, String encodedPBK) throws Exception {
        // Split
        String encodedDoc=encodedDocSign.split("__.__")[0];
        System.out.println("encodedDoc = " + encodedDoc);
        String encodedSign=encodedDocSign.split("__.__")[1];
        System.out.println("encodedSign = " + encodedSign);
        //Encoded
        byte[] decodedPBK=Base64.getDecoder().decode(encodedPBK);
        System.out.println("decodedPBK = " + decodedPBK);
        byte[] document=Base64.getDecoder().decode(encodedDoc);
        System.out.println("document = " + document);

        /** TEST : cas de signature non ok **/
//        document="khkvhjfdsvhjkd".getBytes();
        /*****************************/

        byte[] sign=Base64.getDecoder().decode(encodedSign);
        System.out.println("sign = " + sign);
        // KeyFactory
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        System.out.println("keyFactory = " + keyFactory);
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodedPBK));
        System.out.println("publicKey = " + publicKey);
        // Signature
        Signature signature=Signature.getInstance("SHA256withRSA");
        System.out.println("signature = " + signature);
        signature.initVerify(publicKey);
        signature.update(document);
        boolean verify = signature.verify(sign);
        System.out.println("verify = " + verify);
        // Print
        System.out.println(verify?"\n\nSignature ok":"\n\nSignature non ok");
    }
}
