import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * swexpert.SW_5656_벽돌깨기
 * author djunnni
 */
public class Solution_D {
    static int answer, N, W, H;
    static int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 };
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        int TC = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= TC; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            // 구슬 쏠 횟수
            N = Integer.parseInt(st.nextToken());
            // 너비
            W = Integer.parseInt(st.nextToken());
            // 높이
            H = Integer.parseInt(st.nextToken());

            int[][] map = new int[H + 1][W + 1];

            for(int r = 1; r <= H; r++) {
                String data = br.readLine();
                for(int c = 1, index = 0; c <= W; c++, index += 2) {
                    map[r][c] = data.charAt(index) - '0';
                }
            }
            // 지도 출력
//            for(int[] row : map) {
//                System.out.println(Arrays.toString(row));
//            }
            // 정답 초기화
            answer = Integer.MAX_VALUE;

            /**
             * 공이 어떻게 떨어질 지, 이야기 해준건 없다.
             * 단 같은 위치에 반복해서 떨어질 수 있다. 주어진 횟수가 있다.
             * 1 ~ W까지 중복 순열 문제
             */

            go(map, 0);

            System.out.println("#" + tc + " " + answer);
        }
    }

    private static boolean go(int[][] map, int count) {
        // count번째를 시작한다.
        int sum = 0;
        for(int r = 1; r <= H; r++) {
            for(int c = 1; c <= W; c++) {
                if(map[r][c] > 0) sum++;
            }
        }
        if(sum == 0) {
            answer = 0;
            return true;
        }
        if(count == N) {
            answer = Math.min(answer, sum);
            return false;
        }

        int[][] tempMap = null;
        // 1 ~ W 까지 블록 깨기 DFS, BFS
        for(int c = 1; c <= W; c++) {
            // 시작벽돌 찾기
            int r = 1;
            while(r <= H && map[r][c] == 0) ++r;
            if(r == H + 1) {
                continue;
            } else {
                tempMap = copyMap(map);
                boom(tempMap, r, c, tempMap[r][c]);
                down(tempMap);
                if(go(tempMap, count + 1)) return true;
            }
        }
        return false;
    }

    private static void down(int[][] map) {
        for(int c = 1; c <= W; c++) {
            int r = H;
            while(r > 0) {
                if(map[r][c] == 0) {
                    int nr = r - 1;
                    while(nr > 0 && map[nr][c] == 0) nr--;
                    map[r][c] = map[nr][c];
                    map[nr][c] = 0;
                }
                r--;
            }
        }
    }

    private static void boom(int[][] map, int r, int c, int scale) {
        map[r][c] = 0;
        if(scale == 1) return;
        for(int i = 0; i < dr.length; i++) {
            int nr = r;
            int nc = c;

            for(int k = 1; k < scale; k++) {
                nr += dr[i];
                nc += dc[i];

                if(isOver(nr, nc) || map[nr][nc] == 0) {
                    continue;
                }
                boom(map, nr, nc, map[nr][nc]);
            }
        }
    }

    private static int[][] copyMap(int[][] map) {
        int temp[][] = new int[H + 1][W + 1];

        for(int i = 1; i <= H; i++) {
            for(int j = 1; j <= W; j++) {
                temp[i][j] = map[i][j];
            }
        }

        return temp;
    }
    private static boolean isOver(int r, int c) {
        if(r < 1 || c < 1 || r > H || c > W) {
            return true;
        }
        return false;
    }
}
