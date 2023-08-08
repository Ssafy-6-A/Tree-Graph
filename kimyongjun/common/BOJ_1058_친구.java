package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 풀이 시작 : 21:19
 * 풀이 완료 : 21:37
 * 풀이 시간 : 18분
 *
 * 문제 해석
 * A와 B가 2-친구이려면
 * 1. A와 B가 친구
 * 2. A의 친구이면서 B의 친구인 C가 있음
 *
 * 구해야 하는 것
 * 2-친구 수가 가장 많은 사람의 2-친구 수
 * <p>
 * 문제 입력
 * 첫째 줄 : 사람의 수 N
 * 둘째 줄 ~ N개의 줄 : 각 사람이 친구인지 아닌지 (input[i][j] = Y이면 친구, N이면 친구 아님)
 *
 * 제한 요소
 * 1 <= N <= 50
 *
 * 생각나는 풀이
 * 모든 친구 사이의 거리가 1인 그래프로 볼 수 있음
 * N <= 50이고 제한시간이 2초이므로 모든 그래프 정점 간의 거리를 계산하는 플로이드-워셜 알고리즘 사용 가능
 * O(N^3) = 50^3 = 125,000 < 2억
 * 2-친구의 수 => 한 정점에서 거리가 2 이하로 갈 수 있는 다른 정점의 수
 *
 * 구현해야 하는 기능
 * 1. 입력값에 따른 인접행렬 그래프 생성
 * 2. 플로이드 워셜 알고리즘 수행
 * 3. 모든 정점에서 2-친구 수 탐색
 */
public class BOJ_1058_친구 {
    static int N, INF = 100;
    static int[][] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        dist = new int[N][N]; // 거리 저장 배열
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < N; j++) {
                if (input.charAt(j) == 'Y') { // 둘이 친구 사이이면
                    dist[i][j] = 1; // 거리 1
                } else dist[i][j] = INF; // 친구 아니면 거리 무한((간선이 가질 수 있는 최대 비용 * 정점 수)보다 큰 수)
            }
            dist[i][i] = 0; // 자기 자신으로 가는 비용은 0
        }

        floydWarshall();
        System.out.println(findMaxFriendNum());
    }

    // Floyd-Warshall 알고리즘
    // 가장 바깥쪽 = 중간 지점
    // 안쪽 2개 = 시작 지점 & 끝 지점
    private static void floydWarshall() {
        for (int mid = 0; mid < N; mid++) {
            for (int start = 0; start < N; start++) {
                for (int end = 0; end < N; end++) {
                    if (dist[start][end] > dist[start][mid] + dist[mid][end]) {
                        dist[start][end] = dist[start][mid] + dist[mid][end];
                    }
                }
            }
        }
    }

    // 가장 많은 2-친구 수 찾는 메서드
    private static int findMaxFriendNum() {
        int maxNum = 0;
        for (int i = 0; i < N; i++) {
            int nowCnt = 0;
            for (int j = 0; j < N; j++) {
                if (i == j) continue;
                if (dist[i][j] <= 2) nowCnt++;
            }
            maxNum = Math.max(maxNum, nowCnt);
        }

        return maxNum;
    }
}