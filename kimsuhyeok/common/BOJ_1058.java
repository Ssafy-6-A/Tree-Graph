package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 플로이드 워셜로 풀어보기
 * 2-친구 관계: 한다리 건너서 아는 사람까지 친구로 본다
 * 
 * @author SSAFY
 *
 */

public class BOJ_1058 {
	
	static int N;
	static char[][] board;
	static int[][] dist;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		board = new char[N][N];
		dist = new int[51][51];
		int answer = 0;
		
		for(int i=0;i<N;i++) {
			String line = br.readLine();
			for(int j=0;j<N;j++) {
				board[i][j]=line.charAt(j);
			}
		}
		
		//초기값 설정
		//dist배열에 만약 board[i][j]가 y이면 1을 넣어주고 아니면 충분히 큰 값을 넣어준다.
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(i==j) {
					dist[i][j]=0;
				}else if(board[i][j]=='Y') {
					dist[i][j]=1;
				}else {
					dist[i][j]=1000000;
				}
			}
		}
		
		print();
		
		//한다리 걸친 친구의 값 까지 계산해주면서 최적화된 값을 할당한다.
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				for(int k=0;k<N;k++) {
					if(i==j||j==k||i==k)continue;
					if(dist[j][k]>dist[i][j]+dist[i][k]) {
						dist[j][k] = dist[i][j]+dist[i][k];
					}
				}
			}
		}
		
		print();
		
		//가장 유명한 사람 찾기
		for(int i=0;i<N;i++) {
			int sum = 0;
			for(int j=0;j<N;j++) {
				if(i==j)continue;
				if(dist[i][j]<=2) {
					sum++;
				}
			}
			answer = Math.max(answer, sum);
		}
		
		System.out.println(answer);
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(dist[i][j]+" ");
			}
			System.out.println();
		}

	}

}
