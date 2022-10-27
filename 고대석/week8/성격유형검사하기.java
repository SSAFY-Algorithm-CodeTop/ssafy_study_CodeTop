import java.util.*;
// 성격 유형 검사
// 1가지 질문으로 점수를 매김
// 각 지표에서 더 높은 점수를 받은 성격 유형이 검사자의 성격 유형
// 성격 유형 점수가 같으면, 사전 순으로 빠른 성격 유형이 성격 유형
class Solution {
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        int R = 0, T = 0, C = 0, F = 0, J = 0, M = 0, A = 0, N = 0;
        for(int i = 0; i < survey.length; i++) {
            // survey와 choices따라 유형에 값을 추가
            switch(survey[i]) {
                case "RT":
                    // 성격 유형 점수 추가
                    if(choices[i] < 4) { // choices가 4보다 작으면 4-choices만큼 성격 유형에 추가
                        R += 4 - choices[i];
                    } else { // 4보다 크거나 같으면 choices-4만큼 성격 유형에 추가
                        T += choices[i] - 4;
                    }
                    break;
                case "TR":
                    if(choices[i] < 4) {
                        T += 4 - choices[i];
                    } else {
                        R += choices[i] - 4;
                    }
                    break;
                case "FC":
                    if(choices[i] < 4) {
                        F += 4 - choices[i];
                    } else {
                        C += choices[i] - 4;
                    }
                    break;
                case "CF":
                    if(choices[i] < 4) {
                        C += 4 - choices[i];
                    } else {
                        F += choices[i] - 4;
                    }
                    break;
                case "MJ":
                    if(choices[i] < 4) {
                        M += 4 - choices[i];
                    } else {
                        J += choices[i] - 4;
                    }
                    break;
                case "JM":
                    if(choices[i] < 4) {
                        J += 4 - choices[i];
                    } else {
                        M += choices[i] - 4;
                    }
                    break;
                case "AN":
                    if(choices[i] < 4) {
                        A += 4 - choices[i];
                    } else {
                        N += choices[i] - 4;
                    }
                    break;
                case "NA":
                    if(choices[i] < 4) {
                        N += 4 - choices[i];
                    } else {
                        A += choices[i] - 4;
                    }
                    break;
            }
        }
        
        // 성격 유형 점수에 따라 점수가 높은 것으로 성격 유형 결정
        if(R > T) {
            answer += "R";
        } else if(R < T) {
            answer += "T";
        } else {
            answer += "R";
        }
        if(C > F) {
            answer += "C";
        } else if(C < F) {
            answer += "F";
        } else {
            answer += "C";
        }
        if(J > M) {
            answer += "J";
        } else if(J < M) {
            answer += "M";
        } else {
            answer += "J";
        }
        if(A > N) {
            answer += "A";
        } else if(A < N) {
            answer += "N";
        } else {
            answer += "A";
        }
        
        return answer;
    }
}