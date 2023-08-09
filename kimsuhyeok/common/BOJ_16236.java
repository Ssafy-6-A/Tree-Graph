package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * NxN 크기의 공간
 * 물고기 : M마리
 * 아기 상어: 1마리
 * 한 칸에는 물고기 최대 1마리 존재
 * 
 * 아기상어와 물고기 모두 크기 속성을 가지고 있음 -> 크기는 자연수
 * 
 * 기본 아기 상어의 크기는 2
 * 아기상어는 1초에 상하좌우로 인접한 한칸씩 이동(사방탐색)
 * 
 * 아기상어는 자기보다 크기가 큰 물고기가 있는 칸 이동 x
 * 나머지칸은 이동 가능
 * 
 * 아기상어는 자기보다 크기가 작은 물고기만 먹을 수 있다.
 * -> 크기가 같은 물고기는 먹기 x but 지나는 갈 수 있음
 * 
 * 이동 결정 방법
 * 1. 더 이상 먹을 수 있는 물고기가 공간에 없다면 엄마상어에게 도움 요청 -> 끝나는 조건
 * 2. 먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 감
 * 3. 먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기 먹으러 감
 * 3-1. 거리는 아기 상어가 있는 칸에서 물고기가 있는 칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값
 * 3-2. 거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면 가장 왼쪽에 있는 물고기
 * 
 * 즉 같은 거리면 위에 있는 물고기 선택, 같은 거리면서 위에 여러마리 있으면 그 중 가장 왼쪽 선택
 * 
 * 아기상어는 이동과 동시에 물고기를 먹고 물고기를 먹으면 해당 칸은 빈칸이 됨
 * 아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때마다 크기가 1 증가
 * 
 * 목표
 * 아기 상어가 몇 초 동안 엄마상어에게 도움을 요청 x하고 물고기를 잡아먹을 수 있는지 구하기
 * 
 * 입력
 * 첫째 줄: N (2<=N<=20) -> 공간의 크기
 * 둘째 줄: N개의 줄에 공간 상태
 * 공간 상태
 * 0: 빈칸
 * 1,2,3,4,5,6: 칸에 있는 물고기 크기
 * 9: 아기상어 위치
 * 
 * 아기상어는 한마리만 있음
 * 
 */

class Node{
	int x;
	int y;
	public Node(int x, int y) {
		this.x=x;
		this.y=y;
	}
}

public class BOJ_16236 {

	static int[][] arr;
	static int N;
	static StringBuilder sb = new StringBuilder();
	static Queue<Node> queue;
	//시계방향으로 북,동,남,서
	static final int[] dx = {-1,0,1,0};
	static final int[] dy = {0,1,0,-1};
	static int[][] visited;
	//상어 크기
	static int sharkSize=2;
	//상어 x좌표
	static int sharkX;
	//상어 y좌표
	static int sharkY;
	
	//가장 짧은 거리
	static int minDist;
	//가장 짧은 거리의 y좌표
	static int minY;
	//가장 짧은 거리의 x좌표
	static int minX;
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		N = Integer.parseInt(br.readLine());
		
		arr = new int[N][N];
		queue = new LinkedList<Node>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				arr[i][j]=Integer.parseInt(st.nextToken());
				//입력받다가 만약 상어면
				if(arr[i][j]==9) {
					sharkX = i;
					sharkY = j;
					//상어가 이동할테니까 0으로 초기화
					arr[i][j]=0;
				}
			}
		}
		
		print();
		//먹은 물고기의 수
		int eatCnt=0;
		//아기 상어가 버틴 시간
		int time = 0;
		
		//상어가 물고기를 먹을 때마다 bfs를 실행해야 함
		while(true) {
			visited = new int[N][N];
			minX = Integer.MAX_VALUE;
	        minY = Integer.MAX_VALUE;
	        minDist = Integer.MAX_VALUE;
			
			bfs(sharkX, sharkY);
			
			//bfs를 돌면서 minX랑 minY값이 변함
			//만약 둘 중 한나라도 초기값이라면 탐색할 곳이 없는 거
			if(minX != Integer.MAX_VALUE || minY != Integer.MAX_VALUE) {
				eatCnt++;
				arr[minX][minY] = 0;
				sharkX = minX;
				sharkY = minY;
				time += visited[minX][minY];
				
				//자기 몸집만큼 먹었으면 진화
				if(sharkSize==eatCnt) {
					//몸집 늘려주고
					sharkSize++;
					//먹은 횟수 초기화
					eatCnt = 0;
				}
			}
			else {
				break;
			}
		}
		
		sb.append(time);
		System.out.println(sb);
		
	}
	
	private static void bfs(int sharkX, int sharkY) {
		//queue에 추가해주고
		queue.offer(new Node(sharkX, sharkY));
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			int x = node.x;
			int y = node.y;
			
			for(int d=0;d<4;d++) {
				int nx = x+dx[d];
				int ny = y+dy[d];
				
				//경계값 체크 + arr[nx][ny]가 shark의 크기보다 작거나 같아서 이동할 수 있음 + 아직 방문하지 않은 상태
				if(nx>=0 && ny>=0 && nx<N && ny<N && arr[nx][ny]<=sharkSize && visited[nx][ny]==0) {
					//방문 배열 및 길이 체크
					visited[nx][ny] = visited[x][y]+1;
					
					//먹을 물고기가 있는지 확인
					//빈 공간이 아니고 아기상어의 크기보다 작을 떄
					if(arr[nx][ny]!=0 && arr[nx][ny]<sharkSize) {
						//만약 visited[nx][ny]의 거리가 최소거리이면 갱신
						if(minDist>visited[nx][ny]) {
							minDist = visited[nx][ny];
							//그리고 해당 최소거리의 x좌표, y좌표 갱신
							minX = nx;
							minY = ny;
						}
						//만약 최소거리가 같으면 x좌표 확인 -> 이때 같은게 많으면 y좌표 확인
						else if(minDist == visited[nx][ny]) {
							//만약 nx가 더 위에 있으면 갱신
							if(minX>nx) {
								minX = nx;
								minY = ny;
							}
							//만약 x좌표도 같으면 y좌표 확인
							if(minX==nx) {
								//ny가 더 왼쪽에 있으면 갱신
								if(minY>ny) {
									minX = nx;
									minY = ny;
								}
							}
						}
						
						queue.offer(new Node(nx,ny));
					}
				}
			}
		}
		
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
}
