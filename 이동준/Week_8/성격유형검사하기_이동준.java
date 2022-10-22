import java.util.*;

/**
 * @author djunnni
 *         소요시간: 03:14 ~ 03:37
 */
class Solution {
    public String solution(String[] survey, int[] choices) {
        String[][] jipyo = { { "R", "T" }, { "C", "F" }, { "J", "M" }, { "A", "N" } }; // 지표 정보
        Map<String, Integer> map = new HashMap<>();
        for (String data[] : jipyo) { // 지표값들 0 초기화
            map.put(data[0], 0);
            map.put(data[1], 0);
        }

        for (int i = 0; i < survey.length; i++) {
            String[] _survey = survey[i].split("");
            int[] data = getValue(choices[i]);
            if (data[0] != -1) { // 점수를 줄 수 있다면
                map.put(_survey[data[0]], map.get(_survey[data[0]]) + data[1]);
            }
        }
        // System.out.println(map);
        String answer = makeMBTI(jipyo, map);

        return answer;
    }

    public int[] getValue(int choice) {
        if (choice < 4) {
            return new int[] { 0, Math.abs(choice - 4) };
        } else if (choice > 4) {
            return new int[] { 1, Math.abs(choice - 4) };
        }
        return new int[] { -1, -1 };
    }

    public String makeMBTI(String[][] jipyo, Map<String, Integer> map) {
        String answer = "";

        for (String[] types : jipyo) {
            String L = types[0];
            String R = types[1];

            int Lrate = map.get(L);
            int Rrate = map.get(R);

            if (Lrate >= Rrate) {
                answer += L;
            } else {
                answer += R;
            }
        }

        return answer;
    }
}