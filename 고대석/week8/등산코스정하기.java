import java.util.*;

// 1. 다익스트라 : 보통 가중치의 합이 최소가 되는 경우
// 등산코스 정하기는 가중치의 최대값을 최소로 만든 경우
// 여러 경우가 있을 때 산봉우리 번호가 작은 것을 우선

// 2. 모든 경로 탐색 -> 시간초과

// 3. 출입구 -> 산봉우리 단방향 경로 
// 왔던 길이 최적 -> 돌아가는 길도 똑같으면서 최적 -> 왕복 고려 x 

class Solution {
    static List<List<Node>> graph;
    
    static class Node {
        int x, w;
        
        Node(int x, int w) {
            this.x = x;
            this.w = w;
        }
    }
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        graph = new ArrayList<>();
        for(int i = 0; i < n+1; i++) {
            graph.add(new ArrayList<>());
        } // 그래프 생성
        
        for(int[] path : paths) {
            int i = path[0]; // 출발지
            int j = path[1]; // 도착지
            int w = path[2]; // 소요시간
            
            // 출입구 -> 산봉우리 단방향 경로 
            // 출발지가 출입구이거나 도착지가 산봉우리
            if (isGate(i, gates) || isSummit(j, summits)) {
                graph.get(i).add(new Node(j, w));
            } else if (isGate(j, gates) || isSummit(i, summits)) { // 도착지가 출입구이거나 출발지가 산봉우리
                graph.get(j).add(new Node(i, w));
            } else {
                // 일반적인 경우 양방향
                graph.get(i).add(new Node(j, w));
                graph.get(j).add(new Node(i, w));
            }
        }
        return bfs(n, gates, summits);
    }
    
    private static int[] bfs(int n, int[] gates, int[] summits) {
        Queue<Node> q = new LinkedList<>();
        int[] intensity = new int[n+1];
        
        Arrays.fill(intensity, Integer.MAX_VALUE);
        
        // 출입구를 모두 큐에 넣음
        for(int gate : gates) {
            q.offer(new Node(gate, 0));
            intensity[gate] = 0;
        }
        
        while(!q.isEmpty()) {
            Node node = q.poll();
            
            // 현재 가중치가 저장된 가중치보다 클 때 
            if(node.w > intensity[node.x]) continue;
            
            for(int i = 0; i < graph.get(node.x).size(); i++) {
                Node nn = graph.get(node.x).get(i);
                
                int max = Math.max(intensity[node.x], nn.w);
                if(intensity[nn.x] > max) {
                    intensity[nn.x] = max;
                    q.add(new Node(nn.x, max));
                }
            }
        }
        
        int summitNum = Integer.MAX_VALUE; // 산봉우리의 번호
        int min = Integer.MAX_VALUE; // intensity의 최솟값
        
        // 산봉우리 번호가 가장 낮은 등산 코스 선택
        Arrays.sort(summits);
        
        for(int summit : summits) {
            if(intensity[summit] < min) {
                summitNum = summit;
                min = intensity[summit];
            }
        }
        
        return new int[]{summitNum, min};
    }
    
    // x가 출입구인지 확인
    private static boolean isGate(int x, int[] gates) {
        for(int gate : gates) {
            if(gate == x) return true;
        }
        return false;
    }
    
    // x가 산봉우리인지 확인
    private static boolean isSummit(int x, int[] summits) {
        for(int summit : summits) {
            if(summit == x) return true;
        }
        return false;
    }
}