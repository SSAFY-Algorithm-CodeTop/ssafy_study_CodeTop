import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SW_4008_숫자만들기 {

	public static int[] map;
	public static int[] startMap;
	public static int N;
	public static int maxValue;
	public static int minValue;

	public static void recur(int cur, char[] culcMap, int[] culc) {
		if (cur == (N - 1)) // 연산자는 결국 N-1개 만큼 든다.
		{
			for (int i = 0; i < N - 1; i++) {
				char c = culcMap[i];
				switch (c) {
				case '+':
					map[i + 1] = map[i] + map[i + 1];
					break;
				case '-':
					map[i + 1] = map[i] - map[i + 1];
					break;
				case '*':
					map[i + 1] = map[i] * map[i + 1];
					break;
				case '/':
					if (map[i] < 0) {
						map[i + 1] = -(-map[i] / map[i + 1]);
					} else {
						map[i + 1] = map[i] / map[i + 1];
					}
					break;
				}
			}
			if (map[N - 1] < minValue) {
				minValue = map[N - 1];
			}
			if (map[N - 1] > maxValue) {
				maxValue = map[N - 1];
			}
			for (int i = 0; i < N; i++) {
				map[i] = startMap[i];
			}
			return;
		}

		for (int i = 0; i < 4; i++) {
			if (culc[i] > 0) {
				if (i == 0)
					culcMap[cur] = '+';
				else if (i == 1)
					culcMap[cur] = '-';
				else if (i == 2)
					culcMap[cur] = '*';
				else if (i == 3)
					culcMap[cur] = '/';
				culc[i]--;
				recur(cur + 1, culcMap, culc);
				culcMap[cur] = 0;
				culc[i]++;
			}
			continue;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(in.readLine());
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(in.readLine());
			map = new int[N];
			startMap = new int[N];
			char[] culcMap = new char[N - 1];
			StringTokenizer st = new StringTokenizer(in.readLine(), " ");
			int[] culc = new int[4]; // + - * / 의 개수
			for (int i = 0; i < 4; i++) {
				culc[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(in.readLine(), " ");
			for (int n = 0; n < N; n++) {
				map[n] = Integer.parseInt(st.nextToken());
				startMap[n] = map[n];
			}
			maxValue = Integer.MIN_VALUE;
			minValue = Integer.MAX_VALUE;
			recur(0, culcMap, culc);
			int result=maxValue-minValue;
			sb.append("#").append(tc).append(" ").append(result).append("\n");
//			sb.append(maxValue).append("\n").append(minValue);
		}
		System.out.printf(sb.toString());
	}

}
