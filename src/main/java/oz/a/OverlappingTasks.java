package oz.a;
/*
Define a task had a starting time and end time, each task need to be executed in one slot(like a CPU).
Given a list of tasks, at least how many execution slots are needed?

Example 1:
Input: [[0, 30],[5, 10],[15, 20]]
Output: 2
Example 2:
Input: [[7,10],[2,4]]
Output: 1
*/

/*
My algorithm:
1. Put all tasks in sorted map (STM) keyed on start time.
2. Create a list of tasks (TL) -empty at the start.
3. Iterate through map STM, and
  a) take start time of current task, and compare it with end time of each task in the list TL: IF latter is less THEN remove task from TL;
  b) add current task to TL; go to next task in STM.
4. At the end, TL will have all tasks, and size of TL will be the answer.
Time complexity: O(N lg N) for sorting, and O(N) for iterating through STM. Space complexity: O(N) for STM and TL.
 */

import java.util.Comparator;
import java.util.stream.Stream;

public class OverlappingTasks {

    public static void main(String[] args) {
        int[][] tasks = {{7, 10}, {2,4}, {0, 5}, {2, 3}, {8, 15}};

        System.out.printf("There are %d overlaps", numberOfSlots3(tasks));
    }

    static int numberOfSlots(int[][] tasks) {
        int maxSlots = 0;
        if (tasks == null || tasks.length == 0)
            return maxSlots;
        for (int i = 0, n = tasks.length; i < n; i++) {
            int curStart = tasks[i][0];
            int curMax = 0;
            for (int j  = 0; j < n; j++) {
                if (tasks[j][0] <= curStart && tasks[j][1] >= curStart) {
                    curMax++;
                }
            }
            maxSlots = Math.max(curMax, maxSlots);
        }
        return maxSlots;
    }// time complexity is O(n2), no additional space, but O(N) is already allocated for tasks array

    /*
    Another known way is to iterate endpoints where Start increments the number of simultaneous tasks and End decrements them.
    We'll need to sort them first which has better time O(N lg N), but needs more space as compared to prev.
     */
    static int numberOfSlots2(int[][] tasks) {
        int maxSlots = 0;
        if (tasks == null || tasks.length == 0)
            return maxSlots;
        int[][] endpoints = new int[tasks.length * 2][2];
        for (int i = 0, j = 0; i < tasks.length; i++) {
            endpoints[j][0] = tasks[i][0];
            endpoints[j++][1] = 1;
            endpoints[j][0] = tasks[i][1];
            endpoints[j++][1] = -1;
        }
        java.util.Arrays.sort(endpoints, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int curSlots = 0;
        for (int i = 0; i < endpoints.length; i++) {
            curSlots += endpoints[i][1];
            maxSlots = Math.max(curSlots, maxSlots);
        }
        return maxSlots;
    }

    /*
    with streams
     */
    static int maxSlots = 0;
    static int curSlots = 0;

    static int numberOfSlots3(int[][] tasks) {
        if (tasks == null || tasks.length == 0)
            return 0;

        java.util.Arrays.stream(tasks).flatMap(task -> {
            int[] start = {task[0], 1};
            int[] end = {task[1], -1};
            return Stream.of(start, end);
        }).sorted(
                new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        }).forEachOrdered(
                endpoint -> {
                    curSlots += endpoint[1];
                    maxSlots = Math.max(curSlots, maxSlots);
                }
        );
        return maxSlots;
    }

}
