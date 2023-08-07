package algo.week3.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
##########미완성 코드##########
info.
N개의 도시
한 도시에서 출발하여 다른 도시에 도착하는 버스가 M개
각 버스는 A, B, C로 나타낼 수 있는데, A는 시작도시, B는 도착도시, C는 버스를 타고 이동하는데 걸리는 시간
시간 C가 양수가 아닌 경우: C = 0인 경우는 순간 이동을 하는 경우, C < 0인 경우는 타임머신으로 시간을 되돌아가는 경우
1번 도시에서 출발해서 나머지 도시로 가는 가장 빠른 시간을 구하는 프로그램

input
첫째 줄에 도시의 개수 N (1 ≤ N ≤ 500), 버스 노선의 개수 M (1 ≤ M ≤ 6,000)
둘째 줄부터 M개의 줄에는 버스 노선의 정보 A, B, C (1 ≤ A, B ≤ N, -10,000 ≤ C ≤ 10,000)

return
만약 1번 도시에서 출발해 어떤 도시로 가는 과정에서 시간을 무한히 오래 전으로 되돌릴 수 있다면 첫째 줄에 -1을 출력
그렇지 않다면 N-1개 줄에 걸쳐 각 줄에 1번 도시에서 출발해 2번 도시, 3번 도시, ..., N번 도시로 가는 가장 빠른 시간을 순서대로 출력
만약 해당 도시로 가는 경로가 없다면 대신 -1을 출력

idea
벨만-포드
매 반복마다 모든 간선을 확인하면서 최단거리 탐색
음수 간선에서도 사용 가능
1) 출발 노드 설정
2) 최단 거리 테이블 초기화
3-1) 모든 간선 E개 1개씩 확인
3-2) 각 간선을 거쳐 다른 노드로 가는 비용 계산하여 최단 거리 테이블 갱신
3-3) 음수 간선 순환 발생 시 한 번 더 수행 시 갱신됨

현재 문제에서는 Start == 1로 고정된 벨만-포드
 */
public class BOJ_11657 {

    static int N, M, arr[][], dist[];
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N+1][N+1];
        dist = new int[N+1];

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            arr[x][y] = Integer.parseInt(st.nextToken());
        }

        bellmanford();

        if(false) { // if 음수 순회 : -1 출력
            System.out.println(-1);
        }
        else {
            for(int i=2; i<=N; i++) {
                if(dist[i]==INF)
                    System.out.println(-1);
                else
                    System.out.println(dist[i]);
            }
        }
    }

    static void bellmanford() {

        int start = 1; // 시작점: 1번 도시
        // 현재 거리 배열 초기화
        for(int search=1; search<=N; search++){
            if(start==search)
                dist[search] = 0;
            else
                dist[search] = INF;
        }
        System.out.println(Arrays.toString(dist));
        // start에서 search로 가는 최단거리 구하기
        for(int search=1; search<=N; search++) {
            for(int k=1; k<=N; k++) { // k를 방문해서 가는 방법
                if(arr[start][k] != INF && arr[k][search] != INF) // k를 방문하려면 k와 연결되어 있어야 함
                    dist[search] = Math.min(arr[start][search], arr[start][k] + arr[k][search]);
            }
        }
        System.out.println(Arrays.toString(dist));
    }

}
