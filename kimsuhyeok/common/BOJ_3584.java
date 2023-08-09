package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 자식은 index로 값을 부모로 하는 parent 배열
 * 
 * parent 배열에 해당 index가 부모로 하는 애들 값을 넣어주고
 * 스택에 root에 도달할때까지 하나씩 넣고
 * 스택에서 하나씩 pop하면서 마지막 공통인 애를 출력
 * 
 * @author SSAFY
 *
 */

public class BOJ_3584 {
	
	static int[] parent;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case=0;test_case<T;test_case++) {
			int N = Integer.parseInt(br.readLine());
			parent = new int[N+1];
			for(int i=1;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				int start = Integer.parseInt(st.nextToken());
				int end = Integer.parseInt(st.nextToken());
				
				parent[end] = start;
			}
			
			st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken());
			int num2 = Integer.parseInt(st.nextToken());
			
			Stack<Integer> stack1 = new Stack<Integer>();
			Stack<Integer> stack2 = new Stack<Integer>();
			
			//시작하는 노드 stack에 추가
			stack1.push(num);
			stack2.push(num2);
			
			//root까지 올라가기 -> 해당 인덱스의 값이 부모이기 때문에 부모가 없으면 parent배열의 값이 0이기 때문에
			while(parent[num]!=0) {
				stack1.push(parent[num]);
				num = parent[num];
			}
			//stack1, stack2 각각 넣어주기
			while(parent[num2]!=0) {
				stack2.push(parent[num2]);
				num2 = parent[num2];
			}
			
			int answer = 0;
			while(true) {
				//종료 조건
				if(stack1.isEmpty() || stack2.isEmpty()) {
					break;
				}
				//각각의 값을 pop하면서
				int first = stack1.pop();
				int second = stack2.pop();
				//두 값이 다르면 종료
				if(first!=second) {
					break;
				}
				//first와 second가 같기 때문에 answer에는 아무거나 넣어줘도 된다.
				answer = second;
			}
			sb.append(answer).append(System.lineSeparator());
		}
		System.out.println(sb);
	}

}
