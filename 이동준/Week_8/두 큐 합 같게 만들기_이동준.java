import java.util.*;

/**
 * @author djunnni
 *         소요시간 02:34 ~ 03:11
 *         풀이 : DFS 시작 (오버플로) => While(조건문 반복)
 */

class Solution {
    static int answer;

    public int solution(int[] queue1arr, int[] queue2arr) {
        answer = Integer.MAX_VALUE;

        LinkedList<Integer> que1 = new LinkedList<>();
        LinkedList<Integer> que2 = new LinkedList<>();
        for (int i = 0; i < queue1arr.length; i++) {
            que1.offer(queue1arr[i]);
        }
        for (int i = 0; i < queue2arr.length; i++) {
            que2.offer(queue2arr[i]);
        }

        int totalSize = queue1arr.length + queue2arr.length;
        long que1Sum = queueSum(que1);
        long que2Sum = queueSum(que2);

        if (que1Sum != que2Sum) {
            int count = 0;
            while (que1Sum != que2Sum) {
                if (count > totalSize * 2 || count > answer) {
                    break;
                }
                if (que1Sum > que2Sum) {
                    int v = que1.poll();
                    que2.offer(v);
                    que1Sum -= v;
                    que2Sum += v;
                } else if (que1Sum < que2Sum) {
                    int v = que2.poll();
                    que1.offer(v);
                    que1Sum += v;
                    que2Sum -= v;
                }
                count++;
                if (que1Sum == que2Sum) {
                    answer = Math.min(answer, count);
                    break;
                }
            }
        } else {
            return 0;
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    // public void compare(LinkedList<Integer> que1, long que1Sum,
    // LinkedList<Integer> que2, long que2Sum, int count, int totalSize) {
    // if(count > totalSize || count > answer) {
    // return;
    // }
    // if(que1Sum > que2Sum) {
    // int v = que1.poll();
    // que2.offer(v);
    // que1Sum = (long) que1Sum - v;
    // que2Sum = (long) que2Sum + v;
    // compare(que1, que1Sum, que2, que2Sum, count + 1, totalSize);
    // } else if(que1Sum < que2Sum) {
    // int v = que2.poll();
    // que1.offer(v);
    // que1Sum = (long) que1Sum + v;
    // que2Sum = (long) que2Sum - v;
    // compare(que1, que1Sum, que2, que2Sum, count + 1, totalSize);
    // } else {
    // answer = Math.min(answer, count);
    // return;
    // }
    // }
    public long queueSum(LinkedList<Integer> que) {
        return que.stream().mapToInt(i -> i).sum();
    }
}