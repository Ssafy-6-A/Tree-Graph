package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * 
 * @author SSAFY
 *
 */

public class Dijkstra_Algorithm {
	
	static int[][] arr;
	static int INF = Integer.MAX_VALUE;
	static int V;
	static int E;
	static int[] dist;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		//노드의 개수
		V = Integer.parseInt(st.nextToken());
		//간선의 개수
		E = Integer.parseInt(st.nextToken());
		
		//출발지점
		int start = Integer.parseInt(br.readLine());
		
		arr = new int[V+1][V+1];
		boolean[] visited = new boolean[V+1];
		dist = new int[V+1];
		
		for(int i=1;i<V+1;i++) {
			for(int j=1;j<V+1;j++) {
				if(i!=j) {
					arr[i][j]=INF;
				}
			}
		}
		
		for(int i=1;i<E+1;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			arr[u][v]=arr[v][u]=w;
		}
		
		print();
		
		for(int i=0;i<V+1;i++) {
			dist[i]=INF;
		}
		
		//시작 노드 초기화
		dist[start]=0;
		visited[start]=true;
		
		for(int i=1;i<V+1;i++) {
			if(!visited[i]&&arr[start][i]!=INF) {
				dist[i]=arr[start][i];
			}
		}
		
		for(int k=0;k<V-1;k++) {
			int min = INF;
			int min_idx = -1;
			
			//노드 최솟값 찾기
			for(int i=1;i<=V;i++) {
				if(!visited[i]) {
					if(dist[i]<min) {
						min = dist[i];
						min_idx = i;
					}
				}
			}
			
			//최솟값인 노드 방문처리
			visited[min_idx]=true;
			
			for(int i=1;i<=V;i++) {
				//방문 안했고 INF값이 아닐때
				if(!visited[i] && arr[min_idx][i] != INF) {
					//만약 dist[min_idx]+arr[min_idx][i]가 dist[i]보다 작으면 갱신
					if(dist[min_idx]+arr[min_idx][i]<dist[i]) {
						dist[i]=dist[min_idx]+arr[min_idx][i];
					}
				}
			}
			printDist();
		}

	}
	
	public static void print() {
		for(int i=1;i<=V;i++) {
			for(int j=1;j<=V;j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static void printDist() {
		System.out.println("============================");
		for(int i=1;i<V+1;i++) {
			System.out.print(dist[i]+" ");
		}
		System.out.println();
	}

}
