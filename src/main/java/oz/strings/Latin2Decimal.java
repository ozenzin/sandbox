package oz.strings;

import java.util.function.Supplier;

public class Latin2Decimal {

    private static int result;

    public static void main(String... args) {
        if (args != null && args.length > 0) {
            String latinNumber = args[0].toUpperCase();
            int p = latinNumber.length();
            while (p > 1) {
                Latin digit = Latin.valueOf(latinNumber.substring(p - 1, p));
                Latin nextLeftDigit = Latin.valueOf(latinNumber.substring(p - 2, p - 1));
                result += digit.getDecimalValue(nextLeftDigit);
                if (digit.reducedBy(nextLeftDigit)) {
                    p--;
                }
                p--;
            }
            if (p > 0) {
                result += Latin.valueOf(latinNumber.substring(p - 1, p)).getDecimalValue(null);
            }
        }

        System.out.println(result);
    }

    private enum Latin implements Supplier<Latin> {
        I(1) {
            @Override
            public Latin get() {
                throw new IllegalArgumentException("Nonsense: there's no latin digit less than " + I);
            }
        },
        V(5) {
            @Override
            public Latin get() {
                return I;
            }
        },
        X(10) {
            @Override
            public Latin get() {
                return I;
            }
        }, L(50) {
            @Override
            public Latin get() {
                return X;
            }
        },
        C(100) {
            @Override
            public Latin get() {
                return X;
            }
        }, D(500) {
            @Override
            public Latin get() {
                return C;
            }
        },
        M(1000) {
            @Override
            public Latin get() {
                return C;
            }
        };

        protected final int decimal;

        Latin(int decimal) {
            this.decimal = decimal;
        }

        boolean reducedBy(Latin nextLeft) {
            if (nextLeft != null && nextLeft.compareTo(this) < 0) {
                if (get().equals(nextLeft))
                    return true;
                else
                    throw new IllegalArgumentException(nextLeft + " cannot precede " + this);
            }
            return false;
        }

        int getDecimalValue(Latin nextLeft) {
            if (reducedBy(nextLeft))
                return this.decimal - nextLeft.decimal;
            else
                return decimal;
        }

    }

}
