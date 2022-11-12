package game_of_life;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class game {
    public static void main(String[] args) {
        settings settings = new settings();
        display display = new display(settings);
        run(display, settings);
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    static void test(display display, settings settings) {
        // int[][] alive = {};
        int[][] alive = {{-5, -4}, {-4, -4}, {-6, -3}, {-2, -3}, {-7, -2}, {-1, -2}, {7, -2}, {-17, -1},
                {-16, -1}, {-7, -1}, {-3, -1}, {-1, -1}, {0, -1}, {5, -1}, {7, -1}, {-17, 0}, {-16, 0},
                {-7, 0}, {-1, 0}, {3, 0}, {4, 0}, {-6, 1}, {-2, 1}, {3, 1}, {4, 1}, {17, 1},
                {18, 1}, {-5, 2}, {-4, 2}, {3, 2}, {4, 2}, {17, 2}, {18, 2}, {5, 3},
                {7, 3}, {7, 4}};
        int i = 0;
        long start = System.currentTimeMillis();
        while (i < 1000) {
            if (settings.gameState == 1) {
                alive = update(alive);
                display.draw_life(alive, settings);
            }
            i++;
        }
        long stop = System.currentTimeMillis();
        System.out.println(stop - start);
        //System.exit(0);
    }

    static void run(display display, settings settings) {
        // int[][] alive = {};
        int[][] alive = {{-5, -4}, {-4, -4}, {-6, -3}, {-2, -3}, {-7, -2}, {-1, -2}, {7, -2}, {-17, -1},
                {-16, -1}, {-7, -1}, {-3, -1}, {-1, -1}, {0, -1}, {5, -1}, {7, -1}, {-17, 0}, {-16, 0},
                {-7, 0}, {-1, 0}, {3, 0}, {4, 0}, {-6, 1}, {-2, 1}, {3, 1}, {4, 1}, {17, 1},
                {18, 1}, {-5, 2}, {-4, 2}, {3, 2}, {4, 2}, {17, 2}, {18, 2}, {5, 3},
                {7, 3}, {7, 4}};
        while (settings.running) {
            if (settings.gameState == 1) {
                alive = update(alive);
                wait(settings.sleepTime);
                display.draw_life(alive, settings);
            }
        }
    }

    static int check(int[][] arr, int[] toCheckValue) {
        // This is inefficient. Implement quick sort and binary search.
        for (int i = 0; i < arr.length; i++) {
            if (Arrays.equals(arr[i], toCheckValue)) {
                return i;
            }
        }
        return -1;
    }

    static int[][] append(int[][] arr, int[] element) {
        List<int[]> list = new ArrayList<>(Arrays.asList(arr));
        list.add(element);
        return list.toArray(new int[0][]);
    }

    static int[] neighbor_cell(int[] cell, int num) {
        int[][] neighbors = {{1, 1}, {0, 1}, {-1, 1}, {1, 0}, {-1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        return new int[]{cell[0] + neighbors[num][0], cell[1] + neighbors[num][1]};
    }

    static int count_alive_neighbors(int[][] alive, int[] cell) {
        int alive_num = 0;
        for (int i = 0; i < 8; i++) {
            if (check(alive, neighbor_cell(cell, i)) != -1) {
                alive_num++;
            }
        }
        return alive_num;
    }

    static int[][] update(int[][] alive) {
        int[][] deadNearAlive = {};
        for (int[] cell : alive) {
            for (int i = 0; i < 8; i++) {
                int[] neighbor = neighbor_cell(cell, i);
                int location = check(alive, neighbor);
                if (location != -1) {
                    continue;
                }
                location = check(deadNearAlive, neighbor);
                if (location == -1) {
                    deadNearAlive = append(deadNearAlive, neighbor);
                }
            }
        }

        int[][] next_alive = {};
        for (int[] cell : alive) {
            int alive_num = count_alive_neighbors(alive, cell);
            if (alive_num == 2 || alive_num == 3) {
                next_alive = append(next_alive, cell);
            }
        }

        for (int[] cell : deadNearAlive) {
            int alive_num = count_alive_neighbors(alive, cell);
            if (alive_num == 3) {
                next_alive = append(next_alive, cell);
            }
        }

        return next_alive;
    }
}
