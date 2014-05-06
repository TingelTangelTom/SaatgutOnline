package controller;
/**
 * <p>Die Klasse <code>PasswortHashController</code>
 * kann sichere Hashes von Passwoertern erstellen
 * und Passwoerter und dazugehoerige Hashes vergleichen.
 * Als Algorithmus kommt 
 * PBKDF_2 (Password-Based Key Derivation Function 2) 
 * in Verbindung mit der SecretKeyFactory Klasse zum
 * Einsatz. Die Anzahl an Iterationen sowie die 
 * verwendete Schluessel sowie Salt-Laenge kann
 * intern eingestellt werden.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see https://crackstation.net/hashing-security.htm
 */
import java.security.SecureRandom;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import model.PasswortHashModel;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswortHashController
{
    public static final String PBKDF2_ALGORITHMUS = "PBKDF2WithHmacSHA1";

    // Die Variablen koennen auch nachtraeglich angepasst werden,
    // ohne dass die bisherigen Hashes ihre Gueltigkeit verlieren.
    private static final int SALT_BYTE_GROESSE = 24;
    private static final int HASH_BYTE_GROESSE = 24;
    private static final int PBKDF2_ITERATIONEN = 1000;

    private static final int ITERATION_INDEX = 0;
    private static final int SALT_INDEX = 1;
    private static final int PBKDF2_INDEX = 2;
    /**
     * Liefert einen versalzenen PBKDF2-Hash des Passworts
     *
     * @param   passwort    das zu hashende Passwort
     * @return  versalzener PBKDF2-Hash des Passworts
     */
    public static String erstellePasswortHash(String passwort)
    {
        try {
			return erstellePasswortHash(passwort.toCharArray());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    /**
     * Liefert einen versalzenen PBKDF2-Hash des Passworts
     *
     * @param   passwort     das zu hashende Passwort als char-Array
     * @return               versalzener PBKDF2-Hash des Passworts
     */
    public static String erstellePasswortHash(char[] passwort)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Einen zufaelligen Salt generieren
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_GROESSE];
        random.nextBytes(salt);

        // Passwort -> Hash
        byte[] hash = pbkdf2(passwort, salt, PBKDF2_ITERATIONEN, HASH_BYTE_GROESSE);
        // Format iterationen:salt:hash
        return PBKDF2_ITERATIONEN + ":" + toHex(salt) + ":" +  toHex(hash);
    }

    /**
     * Passwort-Ueberpruefung ueber den Hash.
     *
     * @param   passwort        Das zu pruefende Passwort
     * @param   korrekterHash   Hashwert des richtigen Passworts
     * @return                  true wenn das Passwort stimmt, sonst false
     */
    public static boolean validierePasswort(String passwort, String korrekterHash)
    {
        try {
			return validierePasswort(passwort.toCharArray(), korrekterHash);
		} catch (NoSuchAlgorithmException e) {
		} catch (InvalidKeySpecException e) {
		}
		return false;
    }

    /**
     * Passwort-Ueberpruefung ueber den Hash.
     *
     * @param   passwort        Das zu pruefende Passwort als char-Array
     * @param   korrekterHash   Hashwert des richtigen Passworts
     * @return                  true wenn das Passwort stimmt, sonst false
     */
    public static boolean validierePasswort(char[] password, String correctHash)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Den Hash in die Bestandteile aufsplitten
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        // Den Hash des Passworts berechnen, wobei der gleich salt,
        // Iterations-Anzahl und Hashlaenge verwerndet werden
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        // Ueberpruefung
        return slowEquals(hash, testHash);
    }

    /**
     * Byte-Arrays werden verglichen. Diese Vergleichsmethode verhindert das
     * extrahieren von Hashes durch Online-Timing-Angriffe, die eine spaeteres
     * Offline-Dekodieren beabsichtigen.
     * 
     * @param   a       erstes Byte-Array
     * @param   b       zeites Byte-Array 
     * @return          true, wenn die Byte-Array ttrue sind, sonst false
     */
    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     *  Berechnet den PBKDF2-Hash eines Passworts.
     *
     * @param   passwort    zu hashendes Passwort
     * @param   salt        der Salt
     * @param   iterations  Anzahl an Iterationen (Verlangsamungs-Faktor)
     * @param   bytes       Die Laenge des zu berechnenden Hashes in Bytes
     * @return              PBDKF2-Hash des Passworts
     */
    private static byte[] pbkdf2(char[] passwort, byte[] salt, int iterations, int bytes)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(passwort, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHMUS);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * Konvertiert einen String von Hexadezimal in ein Byte Array
     *
     * @param   hex         der Hex-String
     * @return              in ein Byte-Array dekodierter Hex-String
     */
    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    /**
     * Konvertiert ein Byte-Array in einen hexadezimales String
     *
     * @param   array       zu konvertierendes Byte-Aarray
     * @return              Laenge*2 Char-String, der das Byte-Array kodiert
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) 
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

}