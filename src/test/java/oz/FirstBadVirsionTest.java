package oz;

import org.junit.Test;

import static java.lang.Math.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstBadVirsionTest {
    
    int badV = 47;
    
    int lastV = 165;
    
    boolean isBadVersion(int v) {
        return v >= badV;
    }
    
    
    @Test
    public void testFindFirstNotBadVersion() {
        int steps = 0;
        int[] lr = {0, lastV};
        while (lr[0] < lr[1]) {
            findFirstNotBad(lr);
            steps++;
        };
        assertEquals(lr[0], lr[1]);

        /*
        We can zero on first bad version from the left or from the right, depending on given numbers (odd vs even) of
        the lastV and badV. Hence the following assertion should take that into account. 
         */
        assertEquals(badV, isBadVersion(lr[0]) ? lr[0] : lr[0] +1 );
        assertTrue(steps <= ceil(log(lastV) / log(2)));
    } 
    
    private int findFirstNotBad(int[] lr) {
        int mid = lr[0] + (lr[1] - lr[0]) / 2;
        if (isBadVersion(mid)) 
            lr[1] = --mid;
        else 
            lr[0] = ++mid;
        return mid;
    }
    
}
