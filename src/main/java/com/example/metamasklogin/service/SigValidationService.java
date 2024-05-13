package com.example.metamasklogin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;

import static java.util.Arrays.copyOfRange;

@Service
@Slf4j
public class SigValidationService {

    public boolean isSignatureValid(String signature, String address, Integer nonce) {
        // Compose the message with nonce
        String message = "Signing a message to login: %s".formatted(nonce);

        // Extract the ‘r’, ‘s’ and ‘v’ components
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }
        byte[] r = copyOfRange(signatureBytes, 0, 32);
        byte[] s = copyOfRange(signatureBytes, 32, 64);
        Sign.SignatureData data = new Sign.SignatureData(v, r, s);

        // Retrieve public key

        try {
            return assertAddressIsEqualToRecover(address, message, data);
        } catch (SignatureException e) {
            log.debug("Failed to recover public key", e);
            return false;
        }
    }

    private boolean assertAddressIsEqualToRecover(String address, String message, Sign.SignatureData data) throws SignatureException {
        BigInteger publicKey = Sign.signedPrefixedMessageToKey(message.getBytes(), data);
        // Get recovered address and compare with the initial address
        String recoveredAddress = "0x" + Keys.getAddress(publicKey);
        return address.equalsIgnoreCase(recoveredAddress);
    }
}
