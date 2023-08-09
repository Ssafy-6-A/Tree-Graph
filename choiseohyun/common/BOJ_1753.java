package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

/* BOJ_1753 : 최단경로
 * 
 * 방향그래프가 주어질때 주어진 시작점에서 모든정점으로의 최단경로 구하기
 * 
 * 풀이 : 문제가 다익스트라의 정의 그자체.. 우선순위 큐를 이용하여 푼다
 */

public class BOJ_1753 {
	static List<ArrayList<Node>> graph;
	static int V,E,result[];
	
	static class Node implements Comparable<Node>{
		int vertex;
		int len;
		public Node(int vertex, int len) {
			this.vertex = vertex;
			this.len = len;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.len - o.len;
		}
		
		
	}

	private static void solution(int start) {
		result[start] = 0;
		PriorityQueue<Node> q  = new PriorityQueue<Node>();
		q.offer(new Node(start,0));
		
		while(!q.isEmpty()) {
			Node curr = q.poll();
			if(curr.len > result[curr.vertex]) continue; //현재 경로가 이전에 저장된 경로보다 더 긴 경로라면 무시
			for(Node next : graph.get(curr.vertex)) { //이전에 저장된 경로보다 작은 경로라면
				// 현재 curr 노드에 연결된 그래프를 순회하며 그 비용을 result에 저장한다.
				if(result[next.vertex]>curr.len+next.len) {
					result[next.vertex] = curr.len+next.len;
					q.offer(new Node(next.vertex, curr.len+next.len));
				}
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken()); //정점의 갯수.. 1부터 V까지
		E = Integer.parseInt(st.nextToken()); //간선의 갯수
		result = new int[V+1];
		Arrays.fill(result, Integer.MAX_VALUE);
		graph = new ArrayList<ArrayList<Node>>();
		for(int i=0; i<=V; i++) graph.add(new ArrayList<Node>());
		
		int start = Integer.parseInt(br.readLine()); //시작정점번호
		for(int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			graph.get(Integer.parseInt(st.nextToken())).add(new Node(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
		}
		
		solution(start);
		
		StringBuilder sb = new StringBuilder();
		for(int i=1; i<result.length; i++) {
			if(result[i] == Integer.MAX_VALUE) sb.append("INF"+"\n");
			else sb.append(result[i]+"\n");
		}
		System.out.println(sb);
	}


}
