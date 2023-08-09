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

/* BOJ_11657 : 타임머신
 * 
 * N개의 도시가 있을때, 한 도시에서 출발해 다른도시에 도착하는 버스가 M개 있다.
 * 각 버스는 A,B,C로 나타낼 수 있으며 순서대로 시작도시, 도착도시, 걸리는 시간을 의미한다.
 * 시간 C가 음수일수도 있다. C=0은 순간이동 하는경우, C<0은 타임머신으로 돌아가는경우.
 * 1번에서 출발하여 도시로 가는 가장 빠른시간을 구하는 프로그램을 작성하라.
 * 
 * input : N, M주어지고 M줄의 ABC주어짐
 * output : 음의싸이클이 있으면 -1 출력한다. 없으면 1번도시에서 출발하여 2~N도시로 가는 가장 빠른시간을 순서대로 출력. (경로없으면 -1)
 * 
 * 풀이과정 : 음의 가중치가 있으므로 벨만포드 알고리즘을 사용
 */

public class BOJ_11657 {
	static int N, M; //비용저장배열, 정점(도시)수, 간선(버스)수
	static long dist[]; //음수사이클이 최댓값으로 추어질때 int형을 초과할수있음
	static List<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
	static final int INF = 987654321;
	static boolean isMinusCycle = false;
	
	static class Node{ //기준노드에서 vex노드까지 오는데 걸리는 cost를 저장하는 Node클래스.
		int vex, cost;
		public Node(int vex, int cost) {
			this.vex = vex;
			this.cost = cost;
		}
	}
	
	// 벨만포드 알고리즘을 사용하여 음의 가중치가 있는 최단거리 탐색
	private static void solutoin(int start) { //출발노드 = 1
		//일단 출발노드의 dist를 초기화해준다.
		dist[start] = 0;
		//정점 갯수만큼 반복 - 음의 싸이클 존재여부 확인 (간선수+1만큼 반복 = N)
		for(int i=0; i<N; i++) {
			for(int j=1; j<=N; j++) { //0번그래프는 비어있음
				for(Node node : graph.get(j)) {
					if(dist[j] == INF) break; //현재 기준노드가 무한대면 더이상 전개하는게 의미없음
					if(dist[node.vex] > dist[j]+node.cost) { //dist에 저장된 경로보다 현재 탐색하는 경로가 나으면 갱신
						dist[node.vex] = dist[j]+node.cost;
						if(i == N-1) isMinusCycle = true; //마지막 반복때 또 값이 갱신되려고 한다면 마이너스 사이클인것
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		dist = new long[N+1];
		//Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(dist, INF);
		
		//그래프 만들기 1~N까지 각 정점의 리스트 만든다.
		for(int i=0; i<=N; i++) graph.add(new ArrayList<Node>());
		//그래프의 간선정보를 저장한다.
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			graph.get(Integer.parseInt(st.nextToken()))
					.add(new Node(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
		}
		
		solutoin(1);
		StringBuilder sb = new StringBuilder();
		if(isMinusCycle) sb.append("-1");
		else {
			for(int i=2; i<dist.length; i++) {
				if(dist[i] == INF) sb.append("-1"+"\n");
				else sb.append(dist[i]+"\n");
			}
		}
		System.out.println(sb);
	}
}
