package kimsuhyeok.group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_9663 {

	static int N;
	static int cnt=0;
	static int[][] board;
	static boolean[][] visited;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		board = new int[N][N];
		visited = new boolean[N][N];
		dfs(0);
		System.out.println(cnt);
	}
	
	private static void dfs(int depth){
		if(depth == N) {
			cnt++;
			return;
		}
		
		for(int i=0;i<N;i++) {
			if(!visited[depth][i]) {
				visited[depth][i]=true;
				board[depth][i]=1;
				
				//퀸의 공격방향 체크
				check_queen_Attack(depth,i);
				
				dfs(depth+1);
				
				//이전 상태로 돌아가기
				board[depth][i]=0;	//퀸 빼기
				init_visit();	//방문 배열 초기화
				
				//퀸 위치 복원
				for(int j=0;j<N;j++) {
					for(int k=0;k<N;k++) {
						if(board[j][k]==1) {
							check_queen_Attack(j,k);
						}
					}
				}	
			}
		}
	}
	
	private static void check_queen_Attack(int a, int b) {
		//아래 방향
		for(int i=a;i<N;i++) {
			visited[i][b]=true;
		}
		
		//왼쪽 대각선
		int c = a;
		int d = b;
		while(0<=d && d<N & 0<=c & c<N) {
			visited[c++][d--]=true;
		}
		
		//오른쪽 대각선
		while((0<=a && a<N & (0<=b&&b<N))) {
			visited[a++][b++] = true;
		}
	}
	
	private static void init_visit() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				visited[i][j]=false;
			}
		}
	}

}
