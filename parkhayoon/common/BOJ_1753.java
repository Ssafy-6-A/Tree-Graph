package algo.week3.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
##########미완성 코드##########
백준 1753번 최단경로(골드4) - 다익스트라 알고리즘
참고 FAQ: https://www.acmicpc.net/board/view/34516

info.
방향그래프가 주어지면 주어진 시작점에서 다른 모든 정점으로의 최단 경로를 구하는 프로그램을 작성
단, 모든 간선의 가중치는 10 이하의 자연수

input
첫째 줄에 정점의 개수 V와 간선의 개수 E(1 ≤ V ≤ 20,000, 1 ≤ E ≤ 300,000)
모든 정점에는 1부터 V까지 번호가 매겨져 있다고 가정
둘째 줄에는 시작 정점의 번호 K(1 ≤ K ≤ V)
셋째 줄부터 E개의 줄에 걸쳐 각 간선을 나타내는 세 개의 정수 (u, v, w)가 순서대로
이는 u에서 v로 가는 가중치 w인 간선이 존재한다는 뜻
u와 v는 서로 다르며 w는 10 이하의 자연수
서로 다른 두 정점 사이에 여러 개의 간선이 존재할 수도 있음에 유의

return
첫째 줄부터 V개의 줄에 걸쳐, i번째 줄에 i번 정점으로의 최단 경로의 경로값을 출력
시작점 자신은 0으로 출력하고, 경로가 존재하지 않는 경우에는 INF를 출력

idea
다익스트라 알고리즘 구현 문제


 */
public class BOJ_1753 {

    static int V, E, K, arr[][];
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] arge) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        arr = new int[V+1][V+1];

        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());

        // 배열 초기화
        for(int i=1; i<=V; i++) {
            for(int j=1; j<=V; j++) {
                if (i==j) // 현재점은 0
                    arr[i][j] = 0;
                else // 나머지는 INF로 초기화
                    arr[i][j] = INF;
            }
        }

        for(int i=0; i<E; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            arr[x][y] = Integer.parseInt(st.nextToken());
            // arr[y][x] = arr[x][y]; // 대각선 대칭  - 방향성이 없는 노드의 경우 사용
        }

        dijkstra(K);

    }
    /*
     * 다익스트라 알고리즘
     *
     * 1) 시작점에서부터의 거리 측정
     * dist[] = {0, 2, 3, INF, 1}
     * 2) 거리 비교
     */
    static void dijkstra(int value) {

        int[] dist = arr[value]; // dist[i] == arr[value][i]
        for(int i=1; i<=V; i++) { // value -> i
            if (i==value) continue; // i->i(제자리 돌기) 패스
            for(int j=1; j<=V; j++) { // value -> j -> i
                if (i==j || dist[j]+arr[j][i]<0) continue; // value->i->i 또는 오버플로 발생 시 패스
                dist[i] = Math.min(dist[i], dist[j]+arr[j][i]); // 현재까지 최단거리와 특정 점 j를 거친 거리 중 최소값 저장
                // arr[i][value] = dist[i]; // dist[i] == arr[value][i], 대칭점에 숫자 대입 - 방향성이 없는 노드의 경우 사용
            }
        }

        for(int i=1; i<=V; i++)
            System.out.println(dist[i]);
    }

    // 메모리 초과 코드
	/*
	static void dijkstra(int value) { // 이중 for문 사용 - O(N^2) = (2*10^4)^2

		int[] dist = arr[value]; // dist[i] == arr[value][i]
		for(int i=1; i<=V; i++) { // value -> i
			if (i==value) continue; // i->i(제자리 돌기) 패스
			for(int j=1; j<=V; j++) { // value -> j -> i
				if (i==j || dist[j]+arr[j][i]<0) continue; // value->i->i 또는 오버플로 발생 시 패스
				dist[i] = Math.min(dist[i], dist[j]+arr[j][i]); // 현재까지 최단거리와 특정 점 j를 거친 거리 중 최소값 저장
				// arr[i][value] = dist[i]; // dist[i] == arr[value][i], 대칭점에 숫자 대입 - 방향성이 없는 노드의 경우 사용
			}
		}
		for(int i=1; i<=V; i++)
			System.out.println(dist[i]);
	}
	*/

}