package oz;

import java.util.Arrays;

public class TrappingWater {

    public static void main(String[] args) {
        Integer[] histogram = new Integer[args.length];
        for (int i = 0, n = args.length; i < n; i++) {
            histogram[i] = Integer.valueOf(args[i]);
        }

        trappingWater1(histogram);

        trappingWater2(histogram);
    }

    private static void trappingWater1(Integer... histogram) {

        int highMark = 0, candidateWater = 0, water = candidateWater;

        //left-to-right, catching pools like i_I
        for (int i = 0, n = histogram.length; i < n; i++) {
            int delta = highMark - histogram[i];
            if (delta > 0) {
                candidateWater += delta;
            } else {
                highMark = histogram[i];
                water += candidateWater;
                candidateWater = 0;
            }
        }

        highMark = 0;
        candidateWater = 0;
        //now going right-to-left catching pools like I_j
        for (int i = histogram.length; i-- > 0; ) {
            int delta = highMark - histogram[i];
            if (delta < 0) {
                highMark = histogram[i];
                water += candidateWater;
                candidateWater = 0;
            } else {
                candidateWater += delta;
            }
        }

        System.out.printf("There's %d of water in %d pads of %s%n", water, Arrays.asList(histogram));
    }

    private static void trappingWater2(Integer... histogram) {

        int l2rHighMark = 0, l2rCandidateWater = 0, r2lHighMark = 0, r2lCandidateWater = 0, water = 0, numberOfPods = 0;

        //moving simultaneously left-too-right and right-to-left
        for (int i = 0, k = histogram.length -1; i < histogram.length; i++, k--) {
            int l2rDelta = l2rHighMark - histogram[i];
            if (l2rDelta > 0) {
                l2rCandidateWater += l2rDelta;
            } else {
                l2rHighMark = histogram[i];
                if (l2rCandidateWater > 0) {
                    water += l2rCandidateWater;
                    numberOfPods++;
                    l2rCandidateWater = 0;
                }
            }

            int r2lDelta = r2lHighMark - histogram[k];
            if (r2lDelta < 0) {
                r2lHighMark = histogram[k];
                if (r2lCandidateWater > 0) {
                    water += r2lCandidateWater;
                    numberOfPods++;
                    r2lCandidateWater = 0;
                }
            } else {
                r2lCandidateWater += r2lDelta;
            }
        }

        System.out.printf("There's %d of water in %d pads of %s%n", water, numberOfPods, Arrays.asList(histogram));
    }


}
