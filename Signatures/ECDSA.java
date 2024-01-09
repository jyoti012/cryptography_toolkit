package Signatures;

import java.math.BigInteger;

public class ECDSA {
    public static void main(String[] args) throws Exception {
        EllipticCurve ec = new EllipticCurve("1", "3", "199");
        BigInteger q = new BigInteger("197"),
                Px = new BigInteger("1"),
                Py = new BigInteger("76"),
                x = new BigInteger("29"), // private key
                h = new BigInteger("68"),
                k = new BigInteger("153");

        BigInteger[] publicKey = ec.nPd(Px, Py, x);

        BigInteger[] signature = sign(ec, Px, Py, h, x, k, q);
        System.out.println(signature[0] + ", " + signature[1]);

        System.out.println(verify(ec, signature[0], signature[1], h, q, Px, Py, publicKey[0], publicKey[1]));

        // -------------------

        // ec = new EllipticCurve("2", "2", "17");
        // q = new BigInteger("19");
        // Px = new BigInteger("5");
        // Py = new BigInteger("1");
        // h = new BigInteger("26");
        // publicKey[0] = new BigInteger("0");
        // publicKey[1] = new BigInteger("6");
        // BigInteger r = new BigInteger("9"),
        //         s = new BigInteger("17");

        // System.out.println(verify(ec, r, s, h, q, Px, Py, publicKey[0], publicKey[1]));

        // -------------------


        ec = new EllipticCurve("1", "3", "199");
        q = new BigInteger("197");
        Px = new BigInteger("1");
        Py = new BigInteger("76");
        h = new BigInteger("68");
        publicKey[0] = new BigInteger("113");
        publicKey[1] = new BigInteger("191");
        BigInteger r = new BigInteger("185"),
        s = new BigInteger("78");
        signature = sign(ec, Px, Py, h, x, k, q);
        System.out.println(signature[0] + ", " + signature[1]);
        System.out.println(verify(ec, r, s, h, q, Px, Py, publicKey[0], publicKey[1]));
    }

    public static BigInteger[] sign(EllipticCurve ec, BigInteger Px, BigInteger Py, BigInteger h, BigInteger x,
            BigInteger k, BigInteger q) {
        BigInteger[] ret = { null, null };
        BigInteger[] kP = ec.nPd(Px, Py, k);
        ret[0] = kP[0]; // r = xcoord(kP)
        ret[1] = h.add(x.multiply(ret[0])).multiply(k.modInverse(q)).mod(q); // s = (h + xr)k^-1 mod q
        return ret;
    }

    public static boolean verify(EllipticCurve ec, BigInteger r, BigInteger s, BigInteger h, BigInteger q, BigInteger Px,
            BigInteger Py, BigInteger Yx, BigInteger Yy) throws Exception {
        BigInteger a = h.multiply(s.modInverse(q)).mod(q); // a = hs^-1 mod q
        BigInteger b = r.multiply(s.modInverse(q)).mod(q); // b = rs^-1 mod q
        BigInteger[] aP = ec.nPd(Px, Py, a);
        BigInteger[] bY = ec.nPd(Yx, Yy, b);
        BigInteger[] V = ec.add(aP[0], aP[1], bY[0], bY[1]); // V = aP + bY
        System.out.println("to verify: " + V[0] + ", " + V[1] + " = (" + aP[0] + ", " + aP[1] + ") + (" + bY[0] + ", "
                + bY[1] + ")");
        return V[0].equals(r);
    }
}

