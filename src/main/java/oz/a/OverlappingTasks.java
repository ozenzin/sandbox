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

public class OverlappingTasks {

    public static void main(String[] args) {
        int[][] tasks = {{7, 10}, {2,4}};

        System.out.printf("There are %d overlaps", numberOfSlots(tasks));
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

}
