package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 벨만-포드
 * 그래프 최단 경로 구하는 알고리즘
 * 하나의 정점에서 출발하는 최단 거리를 구함(출발지만 정함)
 * 음수 사이클 없어야 함(음수 가중치 허용)
 * 시간 복잡도: O(n*m)
 * 동적 계획법 사용, relaxtion 기법
 * 
 * 
 * dist는 출발지로부터 최단거리를 기록하는 1차원 배열
 * 출발지는 0, 나머지는 INF(무한)으로 초기화
 * 정점 n개만큼 다음의 동작을 반복
 * 1. 간선 m개를 하나씩 체크 
 * 현재 간선 가중치를 cost(v,w)라고 하면
 * 나오는 정점: v
 * 들어오는 정점: w
 * dist[v]는 지금까지 계산한 출발지에서 v까지의 최소거리 -> dist[v]가 무한이 아니면 2를 진행
 * 
 * 2. dist[v] = min(a,b)
 * a : dist[v] : 지금까지 계산한 v에 도착하는 최단거리
 * b : dist[w]+cost(w,v) : w에 도착하는 최단거리 + w에서 v가는 간선의 가중치
 */


/**
 * N개의 도시
 * 한 도시에서 출발하여 다른 도시에 도착하는 버스 M개
 * 
 * 각 버스는 A,B,C로 표현 가능
 * A: 시작도시
 * B: 도착도시
 * C: 버스 타고 이동하는데 걸린 시간
 * 
 * C가 음수일수도 있음
 * 
 * C=0인경우 : 순간이동 하는 경우
 * C<0인 경우: 타임머신 타고 뒤로 가는 경우
 * 
 * 목표
 * 1번 도시에서 출발해서 나머지 도시로 가는 가장 빠른 시간
 * 
 * 입력
 * 첫째줄에 N,M 주어짐
 * N: 도시의 개수
 * M: 버스 노선의 개수
 * N의 범위: 1<=N<=500
 * M의 범위: 1<=M<=6000
 */



class Bus{
	int start;
	int end;
	int time;
	public Bus(int start, int end, int time) {
		this.start=start;
		this.end=end;
		this.time=time;
	}
}

public class BOJ_11657 {
	
	static final int INF = Integer.MAX_VALUE;
	static int N;
	static int M;
	static Bus[] busArr;
	static long[] dist;    //dist배열의 자료형을 long으로 바꿔줘야함 => N이 500, M이 6000이면 3,000,000(3백만)번 도는데 간선이 -10,000에서 10,000이므로 30억번 돌아서 int를 넘어서버림 -> 이거땜에 출력초과뜸
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		busArr = new Bus[M+1];
		dist = new long[N+1];
		
		for(int i=1;i<=N;i++) {
			dist[i]=INF;
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			busArr[i]=new Bus(A,B,C);
		}
		
		//음수 사이클이 있으면
		if(!bellmanFord()) {
			sb.append("-1").append(System.lineSeparator());
		}
		//음수 사이클이 없으면
		else {
			//2번 도시부터 시작하기 때문에
			for(int i=2;i<=N;i++) {
				if(dist[i]==INF) {
					sb.append("-1").append(System.lineSeparator());
				}
				else {
					sb.append(dist[i]).append(System.lineSeparator());
				}
			}
		}
		
		System.out.println(sb);
		
	}
	
	private static boolean bellmanFord() {
		//dist의 시작지점 0으로 초기화 해주고
		dist[1]=0;
		
		//도시의 개수-1번 수행
		for(int i=1;i<N;i++) {
			for(int j=0;j<M;j++) {
				//버스 간선
				Bus bus = busArr[j];
				
				//버스의 시작지점을 인덱스로 가지는 dist의 값이 무한이 아니고
				//버스의 끝 지점을 인덱스로 가지는 dist의 값이 버스의 시작점으로 가지는 dist의 값+bus의 가중치보다 크면
				if(dist[bus.start]!=INF && dist[bus.end]>dist[bus.start]+bus.time) {
					//더 짧은 거로 없데이트
					dist[bus.end]=dist[bus.start]+bus.time;
				}
			}
		}
		
		//음수 사이클 확인
		
		for(int i=0;i<M;i++) {
			Bus bus = busArr[i];
			if(dist[bus.start]!=INF && dist[bus.end]>dist[bus.start]+bus.time) {
				//다 돌았는데 갱신되는게 있다면 음수 사이클이 있다는 것
				//따라서 return false
				return false;
			}
		}
		
		
		return true;
	}

}
