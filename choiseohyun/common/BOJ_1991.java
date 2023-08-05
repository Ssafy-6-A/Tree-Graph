package tree_graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/** 트리 순회
 * 이진트리를 입력받아 전위, 중위, 후위 순회를 출력한 결과를 작성하라
 * input: 노드의 개수 N, N개에 줄에 걸친 왼,오 자식노드(알파벳대문자) A는 루트노드이며 자식없으면 .
 * 
 * 풀이방법 : Node를 만들어서 트리를 만든다. (Node의 필드로 연결된 Node의 주솟값을 넣는다)
 * 완성된 트리를 재귀로 돌며 그 self값을 프린트한다.
 */
public class BOJ_1991 {
	private static class Node{
		char self;
		Node lt,rt;
		public Node(char self) {
			this.self = self;
		}
		@Override
			public String toString() {
				return self+" ";
			}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Node[] arr = new Node[N];
		for(int i=0; i<N; i++) arr[i] = new Node((char) ('A'+i));
		
		for(int i=0; i<N; i++) {
			char[] line = br.readLine().toCharArray(); // A, ,B, ,C 이런식으로 생김
			arr[line[0]-'A'].lt = line[2]=='.'? null : arr[line[2]-'A'];
			arr[line[0]-'A'].rt = line[4]=='.'? null : arr[line[4]-'A'];
		}
		StringBuilder sb = new StringBuilder();
		sb.append(preOrder(arr[0])+"\n");
		sb.append(inOrder(arr[0])+"\n");
		sb.append(postOrder(arr[0]));
		System.out.println(sb);

	}

	// 전위 : 부모-좌-우
	private static String preOrder(Node node) {
		String result = "";
		result += node.self;
		if(node.lt!=null) result+=preOrder(node.lt);
		if(node.rt!=null) result+=preOrder(node.rt);
		return result;
	}

	// 중위 : 왼-부모-좌
	private static String inOrder(Node node) {
		String result = "";
		if(node.lt!=null) result+=inOrder(node.lt);
		result += node.self;
		if(node.rt!=null) result+=inOrder(node.rt);
		return result;
	}

	// 후위 : 좌-우-부모
	private static String postOrder(Node node) {
		String result = "";
		if(node.lt!=null) result+=postOrder(node.lt);
		if(node.rt!=null) result+=postOrder(node.rt);
		result += node.self;
		return result;
	}
}
