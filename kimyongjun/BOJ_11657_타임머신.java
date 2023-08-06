/**
 * 풀이 시작 : 17:38
 * 풀이 완료 : 18:10
 * 풀이 시간 : 32분
 *
 * 문제 해석
 * N개의 도시와 그 도시들을 연결하는 M개의 버스 노선이 있다.
 * 버스는 시작 도시, 도착 도시, 이동 시간이 있다.
 * 이동 시간은 0이거나 음수일 수 있다.
 * 1번 도시에서 출발해서 나머지 도시에 도착하는 가장 빠른 시간을 구해보자
 *
 * 구해야 하는 것
 * 1번 도시에서 출발해 2번, 3번, ... , N번 도시로 가는 가장 빠른 시간
 * 만약 시간을 무한히 되돌릴 수 있다면 첫째 줄에 -1 출력
 * 만약 i번 도시로 가는 경로가 없다면 해당 도시 차례에 -1 출력
 *
 * 문제 입력
 * 첫째 줄 : 도시의 개수 N
 * 둘째 줄 ~ M개 줄 : 버스 노선의 정보 (시작_도시 도착_도시 이동_시간)
 *
 * 제한 요소
 * 1 <= N <= 500
 * 1 <= M <= 6000
 * 1 <= 시작 도시, 도착 도시 <= N
 * -10000 <= 이동 시간 <= 10000
 *
 * 생각나는 풀이
 * 가중치가 음수가 될 수 있으므로 다익스트라는 사용 불가능
 * => 벨만 포드 알고리즘 사용해야 함
 *
 * 구현해야 하는 기능
 * 1. 입력에 따라 모든 간선 정보 저장
 * 2. 벨만-포드 알고리즘 수행
 * 2-0. 모든 정점 사이의 거리를 무한대로 초기화, 시작 지점은 0으로 초기화
 * 2-1. 모든 간선을 탐색
 * 2-2. 간선의 시작 지점이 아직 방문 불가능(거리가 무한대)인지 체크
 * 2-2-1. 거리가 무한대라면 이번 간선 과정 생략
 * 2-2-2. 거리가 무한대가 아니라면 다음 과정으로 이동
 * 2-3. 간선의 도착 도시까지의 거리 > 간선의 시작 도시 + 간선의 비용이라면 갱신
 * 2-4. 2-1 ~ 2-3 과정을 정점의 수 - 1번만큼 반복 (N개의 정점을 모두 연결하는 간선의 최소 개수 = N - 1개)
 * 2-5. 2-1 ~ 2-3 과정을 한 번 더 반복하며 최소값이 갱신되는지 체크
 * 2-5-1. 만약 갱신된다면 음수 사이클이 있다는 것이므로 -1 출력
 * 2-5-1. 갱신 안되면 음수 사이클이 없는 것이므로 각 도시까지의 최소 비용 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_11657_타임머신 {
    static int N, M;
    static long dist[]; // int형으로 하면 답이 틀림, 음수 사이클이 가능한 최소 값이 -60_000_000이고 반복 횟수가 N번이므로 6천만 * 500 = 30_000_000_000 > Integer.MAX_VALUE
    static Edge[] edges;

    static class Edge {
        int start, end, cost;

        public Edge(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        edges = new Edge[M];
        // * 1. 입력에 따라 모든 간선 정보 저장
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[i] = new Edge(start, end, cost);
        }

        // * 2. 벨만-포드 알고리즘 수행
        System.out.println(bellmanFord());
    }

    private static String bellmanFord() {
        // * 2-0. 모든 정점 사이의 거리를 무한대로 초기화, 시작 지점은 0으로 초기화
        dist = new long[N + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0L;

        // * 2-4. 2-1 ~ 2-3 과정을 N - 1번만큼 반복 (N개의 정점을 모두 연결하는 간선의 최소 개수 = N - 1개)
        for (int times = 1; times <= N; times++) {
            // * 2-1. 모든 간선을 탐색
            for (int edgeIdx = 0; edgeIdx < M; edgeIdx++) {
                Edge now = edges[edgeIdx];
                // * 2-2. 거리가 무한대라면 이번 간선 과정 생략, 그렇지 않다면 2-3 수행
                if (dist[now.start] == Long.MAX_VALUE) continue;

                // * 2-3. 간선의 도착 도시까지의 거리 > 간선의 시작 도시 + 간선의 비용이라면 갱신
                // * 2-5. N번째 반복에서 최소값이 갱신되는지 체크 => 갱신된다면 음수 사이클 있다는 뜻
                if (dist[now.end] > dist[now.start] + now.cost) {
                    if (times == N) {
                        return "-1";
                    }
                    dist[now.end] = dist[now.start] + now.cost;
                }
            }
        }

        // * 2-5-1. 갱신 안되면 음수 사이클이 없는 것이므로 각 도시까지의 최소 비용 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= N; i++) sb.append((dist[i] == Long.MAX_VALUE) ? -1 : dist[i]).append('\n');
        return sb.toString();
    }
}
