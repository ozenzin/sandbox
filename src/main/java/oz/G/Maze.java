package oz.G;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * <pre>
 *         Given a matrix with only 0s and 1s, where 0 represents a wall and 1 represents a path, return if there is a path from first row to last row.
 *
 * 0 1 0 1 0
 * 0 1 1 0 1
 * 1 0 1 0 0
 * 0 0 1 0 0
 * 0 0 1 0 0
 * </pre>
 */
public class Maze {

    public static void main(String[] args) {
        int[][] maze = {{0,1,0,1,0}, {0,1,1,0,1}, {1,0,1,0,0}, {0,0,1,0,0}, {0,0,1,0,0}};
        for (int[] row : maze) {
            for (int val : row) {
                System.out.printf(" %d ", val);
            }
            System.out.printf("%n");
        }
        System.out.printf("%n");

        System.out.printf("Path is %s%n", path(maze));

        for (int[] row : maze) {
            for (int val : row) {
                System.out.printf(val < 0 ? "%d " : " %d ", val);
            }
            System.out.printf("%n");
        }
    }

    static class Coordinates {
        int r, c;

        Coordinates(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "{r=" + r + ", c=" + c + '}';
        }
    }


    static List<Coordinates> path(int[][] maze) {
        LinkedList<Coordinates> path = new LinkedList<>();
        if (maze != null && maze.length != 0 && Arrays.stream(maze).allMatch(row -> row.length > 0))

            for (int i = 0, n = maze[0].length; i < n; i++) {

                if (maze[0][i] > 0) {
                    if (dfs(maze, 0, i, path))
                        return path;
                }
            }

        return path;
    }

    static boolean dfs(int[][] maze, int r, int c, LinkedList<Coordinates> path) {
        Coordinates cur = new Coordinates(r, c);
        path.add(cur);
        maze[r][c] = -1;//to avoid cycle

        if (r == maze.length - 1)
            return true;

        else {

            for (Coordinates next : Arrays.asList(
                    new Coordinates(r - 1, c),
                    new Coordinates(r, c + 1),
                    new Coordinates(r + 1, c),
                    new Coordinates(r, c - 1))) {

                if (next.r >= 0 && next.r < maze.length &&
                        next.c >= 0 && next.c < maze[r].length &&
                        maze[next.r][next.c] > 0) {

                    if (dfs(maze, next.r, next.c, path)) {
                        return true;
                    }

                }

            }
            path.remove(cur);
            return false;
        }
    }


}
