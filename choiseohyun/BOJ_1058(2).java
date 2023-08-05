import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/** 친구
 * 가장 유명한 사람을 구하는 방법 - 각 사람의 2-친구를 구한다.
 * 어떤 사람 A가 또다른 사람 B의 2-친구가 되기 위해서는
 * 1. 두사람의 친구이거나 2. A와 친구이고 B와친구인 C가 존재해야한다,
 * 여기서 가장 유명한 사람은 2-친구의 수가 가장 많은 사람이다.
 * 가장 유명한 사람의 2-친구의 수를 출력하는 프로그램을 작성하라.
 * A와 B가 친구면 B와 A도 친구이고 A와 A는 친구가 아니다.
 * 
 * input : 사람수 N(<=50), N개 줄에 각 사람이 친구이면 Y 아니면 N이 주어진다.
 * output : 가장 유명한 사람의 2-친구수를 출력
 * 
 * 풀이 방법 : 인접리스트로 풀어보려다 실패 ㅎ 검색해보니 플루이드 워샬 사용하래서 그렇게함
 * 플루이드 와샬 = 모든노드에서 모든노드까지의 최단경로를 찾는 알고리즘
 * 따라서 arr에서 1로 표현되어있다면 친구인것이고, 2로 표현되어있다면 경유노드가 하나 있는것임 즉 2-친구
 * 2-친구를 찾으므로 값이 2이하인 arr을 카운트
 */
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		int N, arr[][], max=100000000, answer=0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		
		// 플루이드 와샬로 풀어보자
		
		// 인접노드와의 정보를 arr에 채우기
		for(int i=0; i<N; i++) {
			String line = br.readLine()
;			for(int j=0; j<N; j++) {
				if(line.charAt(j)=='Y') arr[i][j] = 1;
				else if(i!=j) arr[i][j]=max; //i=j일땐 0이 맞고, i!=j이면서 친구가 아니면 무한대로 본다
			}
		}
		
		for(int k=0; k<N; k++) {
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(i==j || j==k || i==k) continue; //대각선방향과 경유노드가 이미 포함된 노드는 무시
					else if(arr[i][j]>arr[i][k]+arr[k][j]) {
						arr[i][j] = arr[i][k]+arr[k][j];
					}
				}
			}
		}
		
		for(int i=0; i<N; i++) {
			int tmp = 0;
			for(int j=0; j<N; j++) {
				if(i==j) continue;
				else if(arr[i][j]<=2) tmp++;
			}
			answer = Math.max(answer, tmp);
		}
		System.out.println(answer);
	}
}
