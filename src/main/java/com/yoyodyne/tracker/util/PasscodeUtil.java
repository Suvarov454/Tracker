package com.yoyodyne.tracker.util;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public final class PasscodeUtil {

    private final int iterations;
    private final int keyLength;
    private final SecretKeyFactory skf;

    /**
     * Declare the number of iterations and the length of the key to be used
     * to generate cryptographically strong hashes.
     *
     * @param iterations the <code>int</code> number of times that the hash
     *   should be applied. Hashing the hashed output makes it difficult for
     *   blackhats to leverage the knowledge of one password's plain text into
     *   a brute force attack on unknown passcodes.
     * @param keyLength the <code>int</code> length of the key used to produce
     *   the hash.
     * @param algorithm the <code>String</code> name of the key algorithm to
     *   be used.
     */
    public PasscodeUtil (int iterations, int keyLength, String algorithm) throws Exception {
	this.iterations = iterations;
	this.keyLength = keyLength;
	this.skf = SecretKeyFactory.getInstance( algorithm );
    }
    
    /**
     * Generate a cryptographically secure hash of the given plain text using
     * the given seed to initialize the process. Game studios should plan on
     * passing the plain text password that the player enters along with the
     * previously stored seed and compare the resulting hash to the previously
     * stored passcode.
     *
     * @param plainText the plain <code>String</code> value to be hashed into
     *   a passcode.
     * @param seed the <code>UUID</code> used to initialize the hashing process
     *   for a given user. Each user should be given their own unique seed.
     * @return a <code>String</code> containing the encoded value of the
     *   cryptographic hash of the plaintext.
     */
    public String hash (String plainText, UUID seed) throws Exception {
	// Given the seed, generate a random salt. The size of the salt in
	// bytes is the one eighth the length of the key in bits (doy).
	byte[] seedArray = PasscodeUtil.convertToBytes( seed );
	SecureRandom rng = new SecureRandom( seedArray );
	byte[] salt = new byte[ keyLength / 8 ];
	rng.nextBytes( salt );

	// Followed example https://www.owasp.org/index.php/Hashing_Java
	PBEKeySpec spec = new PBEKeySpec( plainText.toCharArray(), salt, iterations, keyLength );
	SecretKey key = skf.generateSecret( spec );
	byte[] res = key.getEncoded( );
	return  Base64.getEncoder().encodeToString( res );
 
    }

    /**
     * Convert the given UUID to an array of bytes.
     *
     * @param seed the <code>UUID</code> that will be used to seed the RNG.
     * @return the <code>byte[]</code> representation of the UUID.  The bytes
     *   are ordered from MSB to LSB.
     */
    public static byte[] convertToBytes (UUID seed) {
	ByteBuffer buf = ByteBuffer.allocate(Long.BYTES*2);
	buf.putLong( seed.getMostSignificantBits() );
	buf.putLong( seed.getLeastSignificantBits() );
	return buf.array();
    }

}
