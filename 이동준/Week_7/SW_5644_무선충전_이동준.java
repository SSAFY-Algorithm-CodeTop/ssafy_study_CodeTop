import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * swexpert.SW_5644_무선 충전
 * author djunnni
 * 0.6sec, 256MB
 * 
 * 풀이 방법: DFS를 이용한(결국은 step 밟기!) 최대 MAX score를 전달해서 해결
 *
 */
public class SW_5644_무선충전_이동준 {
    static int MAP_SIZE = 10, M, A, max;
    static int moveA[], moveB[];
    static ArrayList<int[]> BCs;
    static int dm[][] = { { 0, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0 } };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        // 테스트케이스 수
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            // 총 이동시간 M (100)
            M = Integer.parseInt(st.nextToken());
            // BC의 개수(8)
            A = Integer.parseInt(st.nextToken());

            // 1 ~ M초까지 이동거리를 저장.
            moveA = new int[M + 1];
            moveB = new int[M + 1];

            // A 이동
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i <= M; i++) {
                moveA[i] = Integer.parseInt(st.nextToken());
            }
            // B 이동
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i <= M; i++) {
                moveB[i] = Integer.parseInt(st.nextToken());
            }

            // AP 정보
            BCs = new ArrayList<>();
            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());

                BCs.add(new int[] {
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken())
                });
            }
            // 출력 포인트
            // for(int[] bc : BCs) {
            // System.out.println(Arrays.toString(bc));
            // }
            max = 0;

            play(
                    new int[] { 1, 1 },
                    new int[] { 10, 10 },
                    0, 0);

            // System.out.println(Arrays.toString(chargeA));
            // System.out.println(Arrays.toString(chargeB));

            System.out.println("#" + tc + " " + max);
        }
    }

    static void play(int[] spotA, int[] spotB, int time, int sum) {
        if (time > M) {
            // int sum = Arrays.stream(chargeA).sum() + Arrays.stream(chargeB).sum();
            max = Math.max(sum, max);
            return;
        }

        // A의 현재 위치
        int move[] = dm[moveA[time]];
        int AcurrentR = spotA[0] + move[0];
        int AcurrentC = spotA[1] + move[1];
        // B의 현재 위치
        move = dm[moveB[time]];
        int BcurrentR = spotB[0] + move[0];
        int BcurrentC = spotB[1] + move[1];

        // TODO: 현재 위치에 있는 충전 포인트 잡기
        ArrayList<Integer> chargesA = new ArrayList<>();
        ArrayList<Integer> chargesB = new ArrayList<>();

        for (int i = 0; i < BCs.size(); i++) {
            int[] bc = BCs.get(i);
            int distanceA = getDistance(AcurrentR, AcurrentC, bc[0], bc[1]);
            int distanceB = getDistance(BcurrentR, BcurrentC, bc[0], bc[1]);
            // System.out.println("------------ time: "+time + ",BC: " + (i +1)+ "
            // ---------------");
            // System.out.println(distanceA);
            // A, B가 BC랑 거리가 작거나 같다면 후보로 넣습니다.
            if (distanceA <= bc[2]) {
                chargesA.add(i);
            }
            // System.out.println(chargesA);
            if (distanceB <= bc[2]) {
                chargesB.add(i);
            }
            // System.out.println(distanceB);
            // System.out.println(chargesB);
        }
        int _sum = 0;
        if (chargesA.size() > 0 && chargesB.size() == 0) {
            // 둘 중 A만 있을 경우
            for (int bcIndex : chargesA) {
                _sum = Math.max(_sum, BCs.get(bcIndex)[3]);
            }
        } else if (chargesA.size() == 0 && chargesB.size() > 0) {
            // 둘 중 B만 있을 경우
            for (int bcIndex : chargesB) {
                _sum = Math.max(_sum, BCs.get(bcIndex)[3]);
            }
        } else if (chargesA.size() > 0 && chargesB.size() > 0) {
            // A와 B 둘다 있을 경우
            int aIdx = -1;
            int bIdx = -1;
            for (int AofBcIndex : chargesA) {
                for (int BofBcIndex : chargesB) {
                    int __sum = 0;
                    if (AofBcIndex == BofBcIndex) {
                        __sum = BCs.get(AofBcIndex)[3];
                        if (_sum < __sum) {
                            aIdx = AofBcIndex;
                            bIdx = BofBcIndex;
                            _sum = __sum;
                        }
                        continue;
                    }

                    __sum = BCs.get(AofBcIndex)[3] + BCs.get(BofBcIndex)[3];
                    if (_sum < __sum) {
                        aIdx = AofBcIndex;
                        bIdx = BofBcIndex;
                        _sum = __sum;
                    }
                }
            }
        }

        // 다음 플레이 진행
        play(
                new int[] { AcurrentR, AcurrentC },
                new int[] { BcurrentR, BcurrentC },
                time + 1, sum + _sum);
    }

    static int getDistance(int userR, int userC, int cR, int cC) {
        return Math.abs(userR - cR) + Math.abs(userC - cC);
    }
}