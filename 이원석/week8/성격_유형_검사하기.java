package com.ssafy._2022_KAKAO_TEST;

import java.util.HashMap;

// 소요시간 : 30분
public class 성격유형검사하기 {
    public static void main(String[] args) {
        String[] survey = {"TR", "RT", "TR"};
        int[] choices = {7, 1, 3};

        String solution = solution(survey, choices);
        System.out.println(solution);
    }

    public static String solution(String[] survey, int[] choices) {
        char[] mbti = {'R', 'T', 'C', 'F', 'J', 'M', 'A', 'N'};
        int[] score = {-3, -2, -1, 0, 1, 2, 3};
        HashMap<Character, Integer> result = new HashMap<>();

        //  성격유형 해쉬맵 초기화
        for (char c : mbti) {
            result.put(c, 0);
        }

        for (int i = 0; i < survey.length; i++) {
            char[] c = survey[i].toCharArray();
            int choice = choices[i] - 1;
            char disagree = c[0];
            char agree = c[1];

            int get = score[choice];

            // 비동의
            if (get < 0) {
                // 점수 추가
                result.put(disagree, result.get(disagree) + (get * -1));
            } else if (get > 0) { // 동의
                result.put(agree, result.get(agree) + get);
            }
        }

        String answer = "";
        for (int i = 0; i < mbti.length; i += 2) {
            char c1 = mbti[i];
            char c2 = mbti[i + 1];
            int case1 = result.get(c1);
            int case2 = result.get(c2);

            if (case1 < case2) {
                answer += c2;
            } else if (case1 > case2) {
                answer += c1;
            } else { // 사전순으로
                int min = Math.min(c1, c2);
                answer += (char) min;
            }
        }
        return answer;
    }
}

// 1. 성격 유형을 분리하여 동의, 비동의 경우를 구한다.
// 2. 선택이 3 이하일 경우 비동의, 4일 경우 모르겠음, 5 이상일 경우 동의
// 2-1. 
