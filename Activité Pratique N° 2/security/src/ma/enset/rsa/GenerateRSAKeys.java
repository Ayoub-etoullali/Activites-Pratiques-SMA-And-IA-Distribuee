package ma.enset.rsa;

import java.security.*;
import java.util.Base64;

public class GenerateRSAKeys {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        //Encode
        String encodedPRK = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String encodedPBK = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        System.out.println("***** Private Key *****");
        System.out.println(encodedPRK);
        System.out.println("***** Public Key *****");
        System.out.println(encodedPBK);

    }
}
