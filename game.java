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
                //display.draw_life(alive, settings);
            }
            i++;
        }
        long stop = System.currentTimeMillis();
        System.out.println(stop - start);
        System.exit(0);
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

    // Code for quicksort taken from stack abuse and modified.
    static void quicksort(int[][] arr, int low, int high){
        if (low < high){
            int p = partition(arr, low, high);
            quicksort(arr, low, p-1);
            quicksort(arr, p+1, high);
        }
    }

    static int partition(int[][] arr, int low, int high){
        int p = low, j;
        for (j=low+1; j <= high; j++) {
            if (arr[j][0] < arr[low][0]) {
                swap(arr, ++p, j);
            } if (arr[j][0] == arr[low][0] && arr[j][1] < arr[low][1]) {
                swap(arr, ++p, j);
            }
        }

        swap(arr, low, p);
        return p;
    }

    static void swap(int[][] arr, int low, int pivot){
        int[] tmp = arr[low];
        arr[low] = arr[pivot];
        arr[pivot] = tmp;
    }

    // Code for binary search taken from stack abuse and modified.
    static int search(int[][] arrayToSearch, int[] element) {
        int lowIndex = 0;
        int highIndex = arrayToSearch.length-1;

        while (lowIndex <= highIndex - 64) {
            int midIndex = (lowIndex + highIndex) / 2;
            if (element[0] == arrayToSearch[midIndex][0]) {
                if (element[1] == arrayToSearch[midIndex][1]) {
                    return midIndex;
                } else if (element[1] < arrayToSearch[midIndex][1]) {
                    highIndex = midIndex-1;
                } else if (element[1] > arrayToSearch[midIndex][1]) {
                    lowIndex = midIndex+1;
                }
            } else if (element[0] < arrayToSearch[midIndex][0]) {
                highIndex = midIndex-1;
            } else if (element[0] > arrayToSearch[midIndex][0]) {
                lowIndex = midIndex+1;
            }
        }
        // Use linear search when the entire section to search can fit in the cache.
        // I'd assume 64 elements can fit in the cache, more probably can, but I don't feel like testing.
        // Also, what I'm saying might actually be total BS, I'm no expert.
        for (int i = lowIndex; i < highIndex + 1; i++) {
            if (Arrays.equals(arrayToSearch[i], element)) {
                return i;
            }
        }
        // Returns -1 if the value is not present in the array.
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
            if (search(alive, neighbor_cell(cell, i)) != -1) {
                alive_num++;
            }
        }
        return alive_num;
    }

    static int[][] update(int[][] alive) {
        quicksort(alive, 0, alive.length - 1);
        int[][] deadNearAlive = {};
        for (int[] cell : alive) {
            for (int i = 0; i < 8; i++) {
                int[] neighbor = neighbor_cell(cell, i);
                int location = search(alive, neighbor);
                if (location != -1) {
                    continue;
                }
                location = search(deadNearAlive, neighbor);
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

        quicksort(deadNearAlive, 0, deadNearAlive.length - 1);
        for (int[] cell : deadNearAlive) {
            int alive_num = count_alive_neighbors(alive, cell);
            if (alive_num == 3) {
                next_alive = append(next_alive, cell);
            }
        }

        return next_alive;
    }
}
