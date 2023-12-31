package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * sample input
 * V E
 * 5 6
 * 출발 노드
 * 1
 * 
 * u,v,w
 * 5 1 1
 * 1 2 2
 * 1 3 3
 * 2 3 4
 * 2 4 5
 * 3 4 6
 * 시간복잡도 O(n*log(n))
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

public class Dijkstra_Algorithm2 {
	
	static ArrayList<Node>[] graph;
	static int INF = Integer.MAX_VALUE;
	
	public static void Dijkstra(int n,int start){
		//check 배열, dist 배열 초기화 및 dist 배열 INF로 채워주고
		boolean[] check = new boolean[n + 1];
		int[] dist = new int[n + 1];
		int INF = Integer.MAX_VALUE;
		Arrays.fill(dist, INF);
		
		//우선순위큐의 우선순위는 가중치가 작은것
		dist[start] = 0;
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(start, 0));
		
		while(!pq.isEmpty()) {
			int nowVertex = pq.poll().idx;
			
			if(check[nowVertex]) continue;
			check[nowVertex] = true;
			
			//idx와 연결된 정점 비교
			for(Node next : graph[nowVertex]) {
				if(dist[next.idx] > dist[nowVertex]+ next.cost) {
					dist[next.idx] = dist[nowVertex] + next.cost;
					
					pq.offer(new Node(next.idx, dist[next.idx]));
				}
			}
		}
		
		//최소거리 출력
		for(int i : dist) {
			if(i == INF) System.out.print(0 + " ");
			else System.out.print(i+" ");
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		//정점의 개수, 간선의 개수
		int n = Integer.parseInt(bf.readLine());
		int m = Integer.parseInt(bf.readLine());
		
		graph = new ArrayList[n+1];
		for (int i = 0; i <= n; i++)  graph[i] = new ArrayList<>();
		StringTokenizer st;
		for(int i = 0 ; i < m; i++) {
			st = new StringTokenizer(bf.readLine());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[v].add(new Node(w, cost));
		}

		int start = Integer.parseInt(bf.readLine());
		Dijkstra(n, start);
	}

}
