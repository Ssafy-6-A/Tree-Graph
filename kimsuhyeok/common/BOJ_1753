package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 모든 정점에는 1~V까지 번호가 매겨져 있음
 * 
 * 입력
 * 첫째 줄 : 정점의 개수 V와 간선의 개수 E
 * 둘째 줄 : 시작 정점의 번호 K
 * 셋째 줄~E+2줄 : 각 간선을 나타는 세 개의 정수 u,v,w
 * =>u에서 v로 가는 가중치 w인 간선
 * u와 v는 서로 다르고 w는 10이하의 자연수
 * 
 * 주의
 * 서로 다른 두 정점 사이에 여러 개의 간선이 존재 할 수 있다.
 * 
 * 범위
 * 1<=V<=20,000
 * 1<=E<=300,000
 * 1<=K<=V
 * 
 * @author SSAFY
 *
 */

class Node implements Comparable<Node>{
	int idx;
	int cost;
	public Node(int idx, int cost) {
		this.idx = idx;
		this.cost = cost;
	}
	
	public int compareTo(Node o) {
		return Integer.compare(this.cost, o.cost);
	};
}

public class BOJ_1753 {
	
	static ArrayList<Node>[] graph;
	static boolean[] visited;
	static int start;
	static int V;
	static int E;
	static int INF = Integer.MAX_VALUE;
	static StringBuilder sb = new StringBuilder();
	public static void Dijkstra(int start) {
		visited = new boolean[V+1];
		int[] dist = new int[V+1];
		Arrays.fill(dist, INF);
		
		//시작지점 거리 0으로 설정
		dist[start]=0;
		//우선순위큐 초기화
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.offer(new Node(start,0));
		
		while(!pq.isEmpty()) {
			Node currentNode = pq.poll();
			int currentIdx = currentNode.idx;
			int currentCost = currentNode.cost;
			
			if(visited[currentIdx]) continue;
			
			visited[currentIdx] = true;
			
			for(Node next: graph[currentIdx]) {
				if(dist[next.idx]>dist[currentIdx]+next.cost) {
					dist[next.idx] = dist[currentIdx]+next.cost;
					pq.offer(new Node(next.idx, dist[next.idx]));
				}
			}
		}
		
		for(int i=1;i<dist.length;i++) {
			if(dist[i]==INF) {
				sb.append("INF").append(System.lineSeparator());
			}
			else {
				sb.append(dist[i]).append(System.lineSeparator());
			}
		}
		
		System.out.println(sb);
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		graph = new ArrayList[V+1];
		for (int i = 0; i <= V; i++)  graph[i] = new ArrayList<>();
		start = Integer.parseInt(br.readLine());
		for(int i=0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			graph[u].add(new Node(v,w));
		}
		Dijkstra(start);
	}

}
