package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/* BOJ_3584 : 가장 가까운 공통조상
 * 
 * 풀이방법 : 인접리스트를 만들어서 역순으로 조상찾기
 * 보통 인접리스트는 부모 -> 자식노드로 만들지만, 조상찾기이므로 자식 -> 부모의 역방향 관계로 그려보자
 * 그리고 처음 주어진 num에 대해서 루트노드까지 먼저 탐색하며 체크해둔 뒤, 다음 num에 대해 루트까지 돌다 check만나면 종료
 */

public class BOJ_3584 {
	static int N, result, root=0;
	static boolean check[];
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	static List<ArrayList<Integer>> list;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			N = Integer.parseInt(br.readLine());
			check = new boolean[N+1];
			list = new ArrayList<ArrayList<Integer>>();
			
			for(int j=0; j<=N; j++) list.add(new ArrayList<Integer>()); //list접근은 0부터니까 N+1개 만들어줘야함
			for(int j=0; j<N-1; j++) { //N-1개의 간선정보가 주어짐
				st = new StringTokenizer(br.readLine());
				int parent = Integer.parseInt(st.nextToken());
				int child = Integer.parseInt(st.nextToken());
				list.get(child).add(parent);
			}
			
			st = new StringTokenizer(br.readLine());
			int num1 = Integer.parseInt(st.nextToken());
			int num2 = Integer.parseInt(st.nextToken());
			
			//루트노드 찾기 - 부모노드 없는 노드가 루트노드임
			for(int j=0; j<list.size(); j++) {
				if(list.get(j).size() == 0) root = j;
			}
			//num1과 num2의 공통조상을 찾아야한다.
			check[num1] = true;
			solution(num1); //첫번째 노드의 부모경로 체크
			check[num2] = true;
			solution(num2); //두번째 노드의 부모경로 체크 - 이전의 체크노드와 마주치면 공통조상임
			sb.append(result+"\n");
		}
		System.out.print(sb);
	}
	
	private static void solution(int num) {
		//num이라는 원소에서 시작하여 root노드까지의 경로를 찾아 체크한다.
		if(num==root) {
			check[num] = true;
			result = num; //루트노드가 공통조상일수도 있음
			return; //루트노드 도착하면 멈춤
		}
		int parent = list.get(num).get(0); //부모는 오직 하나
		if(!check[parent]) {
			check[parent] = true;
			solution(parent);
		}else { //이미 체크되어있다 = 공통조상이다 = 멈춤
			result = parent;
			return;
		}
		

	}
}
