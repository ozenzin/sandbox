package oz.uber;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.*;

/**
 * dangerous driver

 3 most recent driving

 BanPoolSize = 3
 boolean isBan(string driver);

 void ban(string driver);


 Ban(P1)
 Ban(P2)
 Ban(P3)


 | P1 | P2 | P3 |

 Ban(P4)
 | P2 | P3 | P4 |


 Ban(P3)
 | P2 | P4 | P3 |

 Ban(P5)
 Ban(P6)

 | P3 | P5 | P6 |

 *
 *
 */
public class Solution {

    static ConcurrentLinkedDeque<String> banned = new ConcurrentLinkedDeque<>();

    static void ban(String dName) {
        banned.remove(dName);

        banned.add(dName);

        if (banned.size() > 3 )
            banned.removeFirst();
    }

    static boolean isBan(String dName) {
        return banned.contains(dName);
    }


    public static void main(String args[] ) throws Exception {
        ban("D1");
        ban("D2");
        ban("D3");
        System.out.printf("%s%n", banned);

        ban("D4");
        System.out.printf("%s%n", banned);

        ban("D3");
        System.out.printf("%s%n", banned);

        ban("D5");
        ban("D6");
        System.out.printf("%s%n", banned);

    }




}
