package tree_graph;

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
 * 풀이 방법 : 완전탐색으로 각 노드의 친구를 카운트
 */
public class BOJ_1058 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		int N, max=100000000, answer=0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		char[][] arr = new char[N][N];
		int[] result = new int[N]; //각 노드의 친구수를 저장한다
		boolean[][] check = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			String line = br.readLine();
			for(int j=0; j<N; j++) {
				arr[i][j] = line.charAt(j);
			}
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(arr[i][j] == 'Y') { //i의 친구일때
					if(!check[i][j]) { //이전에 체크한노드도 아닐때
						result[i]++;
						check[i][j]=true;
					}
					// 친구의 친구가 있는지도 체크해준다. i=본인, j=친구, k=친구의친구
					for(int k=0; k<N; k++) {
						if(!check[i][k] && i!=k && arr[j][k]=='Y') {
							result[i]++;
							check[i][k]=true;
						}
					}
				}
			}
		}
		
		Arrays.sort(result);
		System.out.println(result[N-1]);

	}
}
