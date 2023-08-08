package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/**
 * 풀이 시작 :
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 방향그래프가 주어졌을 때 시작점에서 모든 정점으로의 최단경로 구하기
 *
 * 구해야 하는 것
 * 시작점에서 모든 정점으로의 최단 경로
 *
 * 문제 입력
 * 첫째 줄 : 정점의 개수 V, 간선의 개수 E
 * 둘째 줄 : 시작 정점 번호 K
 * 셋째 줄 ~ E개의 줄 : 간선의 정보, u -> v인 비용 w인 간선이 u v w형태로
 *
 * 제한 요소
 * 1 <= V <= 20000
 * 1 <= E <= 300000
 * 1 <= w <= 10
 *
 * 생각나는 풀이
 * 다익스트라 기초
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 그래프 구현
 * 2. 다익스트라 알고리즘
 * 2-1. 출발 노드 설정
 * 2-2. 최단 거리 테이블 INF로 초기화, 출발 노드는 0
 * 2-3. 방문하지 않은 노드 중 최단 거리가 가장 짧은 노드 선택
 * 2-4. 해당 노드를 거쳐 다른 노드로 가는 비용을 계산하여 최단 거리 테이블 갱신
 * 2-5. 2-3 ~ 2-4 과정 반복
 */
public class BOJ_1753_최단경로 {
	static int V, INF = 200001; // INF : 그래프에서 나올 수 없는 큰 값, 간선 최대 비용 = 10, N <= 20000이므로 20만을 넘기면 됨
	static int[] dist;
	static ArrayList<Edge>[] graph;
	
	static class Edge implements Comparable<Edge> {
		int end, cost;
		
		public Edge(int end, int cost) {
			this.end = end;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return this.cost - o.cost;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(br.readLine());
		
		graph = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++) {
			graph[i] = new ArrayList<>();
		}
		// * 1. 입력에 따른 그래프 구현
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[s].add(new Edge(e, cost)); // 방향 그래프이므로 방향 맞춰서 그래프 구현
		}
		
		dijkstra(start);
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= V; i++) {
			if (dist[i] == INF) {
				sb.append("INF");
			} else sb.append(dist[i]);
			sb.append('\n');
		}
		
		System.out.println(sb);
	}

	// * 2. 다익스트라 알고리즘
	private static void dijkstra(int start) { // * 2-1. 출발 노드 설정
		dist = new int[V + 1];
		Arrays.fill(dist, INF); // * 2-2. 최단 거리 테이블 INF로 초기화, 출발 노드는 0
		dist[start] = 0;
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.offer(new Edge(start, 0));
		
		while (!pq.isEmpty()) { // * 2-5. 2-3 ~ 2-4 과정 반복
			Edge now = pq.poll(); // * 2-3. 방문하지 않은 노드 중 최단 거리가 가장 짧은 노드 선택
			
			if (now.cost > dist[now.end]) continue; // 이미 짧은 거리로 갱신(방문)되었다면 continue
			for (Edge next : graph[now.end]) { // * 2-4. 해당 노드를 거쳐 다른 노드로 가는 비용을 계산하여 최단 거리 테이블 갱신
				if (dist[next.end] > dist[now.end] + next.cost) {
					dist[next.end] = dist[now.end] + next.cost;
					pq.offer(new Edge(next.end, dist[next.end]));
				}
			}
		}
		
	}
}