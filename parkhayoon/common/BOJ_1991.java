package algo.week3.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
문제: 백준 1991번 트리 순회

info.
이진 트리를 입력받아
전위 순회(preorder traversal),
중위 순회(inorder traversal),
후위 순회(postorder traversal)한 결과를 출력

전위 순회한 결과 : (루트) (왼쪽 자식) (오른쪽 자식)
중위 순회한 결과 : (왼쪽 자식) (루트) (오른쪽 자식)
후위 순회한 결과 : (왼쪽 자식) (오른쪽 자식) (루트)

input
첫째 줄에는 이진 트리의 노드의 개수 N(1 ≤ N ≤ 26)
둘째 줄부터 N개의 줄에 걸쳐 각 노드와 그의 왼쪽 자식 노드, 오른쪽 자식 노드가 주어짐
노드의 이름은 A부터 차례대로 알파벳 대문자로 매겨지며, 항상 A가 루트 노드가 됨
자식 노드가 없는 경우에는 .으로 표현

return
첫째 줄에 전위 순회, 둘째 줄에 중위 순회, 셋째 줄에 후위 순회한 결과를 출력
각 줄에 N개의 알파벳을 공백 없이 출력

idea
사용 알고리즘: Tree
기초 Tree 구현 문제

Tree 예제 보면서 따라가기

A B C
B D .
C E F
E . .
F . G
D . .
G . .

A를 value로 입력
A의 좌측이 비어있으므로 leftNode에 B 추가
A의 우측이 비어있으므로 rightNode에 C 추가
B를 value로 입력
시작 Node의 value가 null이 아니므로 탐색
시작 Node의 leftNode가 B, value랑 동일 -> 해당 노드로 가서 추가
B의 좌측에 D 추가
B의 우측은 "."은 입력 받으므로 null로 지정
...
 */

public class BOJ_1991 {

    static class Node {
        char value;
        Node leftNode;
        Node rightNode;

        public Node(char value) { // Node의 값을 입력 받아 새로운 노드 생성
            this.value = value;
        }
    }

    public static void addNode(Node node, char value, char left, char right) {

        // 첫 입력 부분
        if (root == null) { // tree가 비어있을 때(root 노드 삽입)
            root = new Node(value); // 비어있는 tree에 들어갈 경우 시작 노드를 생성
            if(left!='.') // '.'인 경우는 비우고, '.'이 아니면 값 삽입
                root.leftNode = new Node(left);
            if(right!='.')
                root.rightNode = new Node(right);
        }
        
        // Node 탐색 부분
        else if(node == null) { // 재귀로 돌면서 node가 비어있으면(바닥 node인 경우) 재귀 종료
            return;
        }
        else if (node.value == value) { // 만약 현재 노드에 검색한 값이 있으면 왼쪽, 오른쪽 노드 삽입
            if(left!='.')
                node.leftNode = new Node(left);
            if(right!='.')
                node.rightNode = new Node(right);
        }
        else { // 노드에 값이 있지만 내가 찾는 값이 없는 경우 탐색 시작
            addNode(node.leftNode, value, left, right);
            addNode(node.rightNode, value, left, right);
        }
    }

    static Node root;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        // root = new Node('\0');

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            char value = st.nextToken().charAt(0);
            char leftValue = st.nextToken().charAt(0);
            char rightValue = st.nextToken().charAt(0);
            addNode(root, value, leftValue, rightValue);
        }

        preorder(root);
        System.out.println();
        inorder(root);
        System.out.println();
        postorder(root);
    }

    static void preorder(Node node) { // 전위탐색
        if (node == null) // 바닥 노드 체크
            return;
        System.out.print(node.value); // 1) 중앙
        preorder(node.leftNode); // 2) 좌측
        preorder(node.rightNode); // 3) 우측
    }

    static void inorder(Node node) { // 중위탐색
        if (node == null)
            return;
        inorder(node.leftNode); // 1) 좌측
        System.out.print(node.value); // 2) 중앙
        inorder(node.rightNode); // 3) 우측
    }

    static void postorder(Node node) { // 후위탐색
        if (node == null)
            return;
        postorder(node.leftNode); // 1) 좌측
        postorder(node.rightNode); // 2) 우측
        System.out.print(node.value); // 3) 중앙
    }

}
