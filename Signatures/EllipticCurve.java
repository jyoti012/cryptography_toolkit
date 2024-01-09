package Signatures;

import java.math.BigInteger;
import java.util.function.Function;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EllipticCurve {
    private BigInteger a, b, p;

    public static BigInteger ONE = BigInteger.valueOf(1);
    public static BigInteger TWO = BigInteger.valueOf(2);
    public static BigInteger THREE = BigInteger.valueOf(3);
    public static BigInteger FOUR = BigInteger.valueOf(4);
    public static BigInteger ZERO = BigInteger.valueOf(0);
    public static BigInteger TWO13 = BigInteger.valueOf(8192);

    EllipticCurve(BigInteger a, BigInteger b, BigInteger p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }

    EllipticCurve(String a, String b, String p) {
        this.a = new BigInteger(a);
        this.b = new BigInteger(b);
        this.p = new BigInteger(p);
    }

    public BigInteger decrypt(BigInteger[] y1, BigInteger y2, BigInteger s) throws NoSuchAlgorithmException {
        y1 = uncompressPoint(y1[0], y1[1]);
        System.out.println(y1[1]);
        BigInteger[] R = nPd(y1[0], y1[1], s);

        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        md.update(R[0].toByteArray());
        md.update(R[1].toByteArray());

        byte[] hash = md.digest();
        BigInteger hashNumber = new BigInteger(hash);

        if (hashNumber.compareTo(ZERO) < 0) {
            hashNumber = new BigInteger(1, hashNumber.toByteArray());
        }

        BigInteger firstNBits = hashNumber.shiftRight(hashNumber.bitLength() - p.bitLength());

        return y2.subtract(firstNBits).mod(p);
    }

    public BigInteger[] ts(BigInteger n, BigInteger p) {
        // BiFunction<BigInteger, BigInteger, BigInteger> powModP = (BigInteger a,
        // BigInteger e) -> a.modPow(e, p);
        Function<BigInteger, BigInteger> ls = (BigInteger a) -> a.modPow(p.subtract(ONE).divide(TWO), p);

        if (!ls.apply(n).equals(ONE))
            return new BigInteger[] { ZERO, ZERO };

        BigInteger q = p.subtract(ONE);
        BigInteger ss = ZERO;
        while (q.and(ONE).equals(ZERO)) {
            ss = ss.add(ONE);
            q = q.shiftRight(1);
        }

        if (ss.equals(ONE)) {
            BigInteger r1 = n.modPow(p.add(ONE).divide(FOUR), p);
            return new BigInteger[] { r1, p.subtract(r1) };
        }

        BigInteger z = TWO;
        while (!ls.apply(z).equals(p.subtract(ONE)))
            z = z.add(ONE);
        BigInteger c = z.modPow(q, p);
        BigInteger r = n.modPow(q.add(ONE).divide(TWO), p);
        BigInteger t = n.modPow(q, p);
        BigInteger m = ss;

        while (true) {
            if (t.equals(ONE))
                return new BigInteger[] { r, p.subtract(r) };
            BigInteger i = ZERO;
            BigInteger zz = t;
            while (!zz.equals(BigInteger.ONE) && i.compareTo(m.subtract(ONE)) < 0) {
                zz = zz.multiply(zz).mod(p);
                i = i.add(ONE);
            }
            BigInteger b = c;
            BigInteger e = m.subtract(i).subtract(ONE);
            while (e.compareTo(ZERO) > 0) {
                b = b.multiply(b).mod(p);
                e = e.subtract(ONE);
            }
            r = r.multiply(b).mod(p);
            c = b.multiply(b).mod(p);
            t = t.multiply(c).mod(p);
            m = i;
        }
    }

    public BigInteger tsr(BigInteger n, BigInteger p) {
        BigInteger grpord = p.subtract(ONE);
        BigInteger ordpow2 = ZERO;
        BigInteger non2 = grpord;
        while (non2.and(ONE).equals(ZERO)) {
            ordpow2 = ordpow2.add(ONE);
            non2.shiftRight(1);
        }
        BigInteger g = TWO;
        for (; g.compareTo(grpord.subtract(ONE)) <= 0; g = g.add(ONE)) {
            if (!g.modPow(grpord.divide(TWO), p).equals(ONE))
                break;
        }

        g = g.modPow(non2, p);
        BigInteger gpow = ZERO;
        BigInteger ntweak = n;
        BigInteger pow2 = ZERO;
        for (; pow2.compareTo(ordpow2.add(ONE)) <= 0; pow2 = pow2.add(ONE)) {
            if (!ntweak.modPow(non2.multiply(TWO.pow(ordpow2.subtract(pow2).intValue())), p).equals(ONE)) {
                gpow = gpow.add(TWO.pow(pow2.subtract(ONE).intValue()));
                ntweak = ntweak.multiply(g.modPow(TWO.pow(pow2.subtract(ONE).intValue()), p)).mod(p);
            }
        }
        BigInteger d = TWO.modInverse(non2);
        // sqrt(a*(g**gpow))
        BigInteger tmp = g.modPow(gpow, p).multiply(n).modPow(d, p);
        // return (inverse_mod(pow(g,gpow//2,p),p)*tmp) % p #
        // sqrt(a*(g**gpow))//g**(gpow//2)
        BigInteger ret = g.modPow(gpow.divide(TWO), p).modInverse(p).multiply(tmp);
        return ret;
    }

    public BigInteger[] uncompressPoint(BigInteger px, BigInteger py) {
        BigInteger res[] = { null, null };

        BigInteger y = (px.pow(3).mod(p).add(a.multiply(px)).add(b)).mod(p); // find y^2
        BigInteger[] roots = ts(y, this.p);
        y = roots[0];
        if (py.equals(ONE) == y.testBit(y.bitLength())) {
            res[0] = px;
            res[1] = p.subtract(y);
        } else {
            res[0] = px;
            res[1] = y;
        }
        return res;
    }

    public BigInteger[] uncompressPoint(String px, String py) {
        BigInteger x = new BigInteger(px);
        BigInteger y = new BigInteger(py);
        return this.uncompressPoint(x, y);
    }

    public BigInteger[] nPd(BigInteger px, BigInteger py, BigInteger n) {
        BigInteger res[] = { null, null };
        BigInteger tmp[] = { null, null };

        try {
            if (n.equals(ZERO)) {
                res[0] = ZERO;
                res[1] = ZERO;
                return res;
            } else if (n.equals(ONE)) {
                res[0] = px;
                res[1] = py;
                return res;
            } else if (n.mod(TWO).equals(ONE)) {
                tmp = nPd(px, py, n.subtract(ONE));
                res = add(px, py, tmp[0], tmp[1]);
                return res;
            } else {
                tmp = pointDouble(px, py);
                res = nPd(tmp[0], tmp[1], n.divide(TWO));
                return res;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return res = tmp;
        }
    }

    public BigInteger[] add(BigInteger px, BigInteger py, BigInteger qx, BigInteger qy) throws Exception {
        BigInteger res[] = { null, null };
        BigInteger numerator = qy.subtract(py);
        BigInteger denominator = qx.subtract(px);
        if (denominator.equals(ZERO)) {
            throw new Exception("cannot add. reached Point Of Infinity");
        }

        numerator = numerator.mod(p);
        denominator = denominator.modInverse(p);

        BigInteger s = numerator.multiply(denominator).mod(p);
        BigInteger newx = s.pow(2).subtract(px).subtract(qx).mod(p);
        BigInteger newy = s.multiply(px.subtract(newx)).subtract(py).mod(p);
        res[0] = newx;
        res[1] = newy;
        return res;
    }

    public BigInteger[] pointDouble(BigInteger px, BigInteger py) throws Exception {
        BigInteger res[] = { null, null };
        BigInteger numerator = px.pow(2).multiply(THREE).add(a);
        BigInteger denominator = py.multiply(TWO);
        numerator = numerator.mod(p);
        denominator = denominator.modInverse(p);
        if (denominator.equals(ZERO)) {
            throw new Exception("cannot double. reached Point Of Infinity");
        }
        BigInteger s = numerator.multiply(denominator).mod(p);
        BigInteger newx = s.pow(2).subtract(px).subtract(px).mod(p);
        BigInteger newy = s.multiply(px.subtract(newx)).subtract(py).mod(p);
        res[0] = newx;
        res[1] = newy;
        return res;
    }

    public BigInteger twoPointPointOrder(BigInteger px, BigInteger py, BigInteger qx, BigInteger qy, BigInteger i) {
        while (true) {
            i = i.add(ONE);
            BigInteger newPoints[];
            try {
                newPoints = this.add(px, py, qx, qy);
                px = newPoints[0];
                py = newPoints[1];
            } catch (Exception e) {
                return i;
            }
        }
    }

    public BigInteger twoPointPointOrder(String px, String py, String qx, String qy, String i) {
        BigInteger x1 = new BigInteger(px);
        BigInteger y1 = new BigInteger(py);
        BigInteger x2 = new BigInteger(qx);
        BigInteger y2 = new BigInteger(qy);
        BigInteger mul = new BigInteger(i);

        return twoPointPointOrder(x1, y1, x2, y2, mul);
    }

    public BigInteger singlePointOrder(BigInteger px, BigInteger py) {
        BigInteger i = TWO;
        BigInteger[] res = { null, null };
        try {
            res = this.pointDouble(px, py);
        } catch (Exception e) {
            return i;
        }
        BigInteger newx = res[0];
        BigInteger newy = res[1];
        while (true) {
            i = i.add(ONE);
            BigInteger newPoints[];
            try {
                newPoints = this.add(newx, newy, px, py);
                newx = newPoints[0];
                newy = newPoints[1];
            } catch (Exception e) {
                // System.out.println("End: " + i);
                return i;
            }
        }
    }

    public BigInteger singlePointOrder(String px, String py) {
        BigInteger x = new BigInteger(px);
        BigInteger y = new BigInteger(py);
        return singlePointOrder(x, y);
    }
}

