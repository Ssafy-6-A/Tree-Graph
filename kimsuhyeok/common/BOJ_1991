package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 트리 순회
 * 
 * 전위순회 : Preorder
 * => Root->Left->Right
 * 
 * 중위순회 : Inorder
 * =>Left->Root->Right
 * a
 * 후위순회 : Postorder
 * =>Left->Right->Root
 * 
 * 트리의 노드 생성은 Inner class를 이용해서 생성
 * 노드 안에 들어가는 것은 노드의 데이터, 
 * 해당 노드의 왼쪽 아래에 있는 노드의 주소값을 가지는 leftNode, 
 * 해당 노드의 오른쪽 아래에 있는 노드의 주소값을 가지는 rightNode
 * 
 * 내가 생성할 함수
 * Tree 관련 함수
 * preOrder : 전위 순회 방법을 이용하여 출력하는 함수
 * inOrder : 중위 순회 방법을 이용하여 출력하는 함수
 * postOrder : 후위 순회 방법을 이용하여 출력하는 함수
 * 
 */


public class BOJ_1991 {
	
	static StringBuilder sb = new StringBuilder();
	static int N;
	static Node head = new Node('A', null, null);
	
	//Node설정
	private static class Node{
		char data;
		Node left;
		Node right;
		public Node(char data, Node left, Node right){
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			
			char data = st.nextToken().charAt(0);
			char left = st.nextToken().charAt(0);
			char right = st.nextToken().charAt(0);
			
			createNode(head, data, left, right);
		}
		
		preOrder(head);
		sb.append(System.lineSeparator());
		inOrder(head);
		sb.append(System.lineSeparator());
		postOrder(head);
		sb.append(System.lineSeparator());
		
		System.out.println(sb);
	}
	
	private static void createNode(Node	temp, char data, char left, char right) {
		if(temp.data==data) {
//			if(left == '.') {
//				temp.left = null;
//			}else {
//				temp.left = new Node(left, null, null);
//			}
//			
//			if(right == '.') {
//				temp.right = null;
//			}else {
//				temp.right = new Node(right, null, null);
//			}
			
			//if문과 else문으로 이루어진 문장을 삼항연산자로 줄여보기
			temp.left = (left=='.'?null:new Node(left, null, null));
			temp.right = (right=='.'?null:new Node(right, null, null));
		}
		else {
			if(temp.left!=null) {
				createNode(temp.left, data, left, right);
			}
			if(temp.right!=null) {
				createNode(temp.right, data, left, right);
			}
		}
	}
	
	
	private static void preOrder(Node node) {
		if(node == null) {
			return;
		}
		
		sb.append(node.data);
		preOrder(node.left);
		preOrder(node.right);
	}
	
	private static void inOrder(Node node) {
		if(node==null) {
			return;
		}
		
		inOrder(node.left);
		sb.append(node.data);
		inOrder(node.right);
	}

	private static void postOrder(Node node) {
		if(node==null) {
			return;
		}
		
		postOrder(node.left);
		postOrder(node.right);
		sb.append(node.data);
	}

}
