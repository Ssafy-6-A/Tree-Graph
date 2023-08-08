package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 풀이 시작 : 20:15
 * 풀이 완료 : 20:37
 * 풀이 시간 : 22분
 *
 * 문제 해석
 * 이진 트리가 주어질 때 preOrder, inOrder, postOrder 경로 출력
 *
 * 구해야 하는 것
 * 각각 순회 방법으로 순회했을 때 경로
 *
 * 문제 입력
 * 첫째 줄 : 노드의 수 N
 * 둘째 줄 ~ N개 줄 : 각 노드, 왼쪽 자식, 오른쪽 자식 주어짐. 만약 '.'이라면 자식이 없는 것
 *
 * 제한 요소
 * 루트는 항상 A
 * 1 <= N <= 26
 *
 * 생각나는 풀이
 * 문제에 답이 있다
 * 트리를 어떻게 구현해야 하나??
 * => Node 정보를 저장한 클래스의 배열로 tree 구현
 *
 * 구현해야 하는 기능
 * 1. Node 정보 저장하는 클래스
 * 1-1. 필드 : 자신의 알파벳, 자식 노드
 * 2. Node를 저장할 배열
 * 3. 트리 순회 구현
 * 3-1. 전위 순회
 * 3-2. 중위 순회
 * 3-3. 후위 순회
 */
public class BOJ_1991_트리순회 {
    static StringBuilder sb = new StringBuilder();
    static Node[] tree;
    static class Node { // * 1. Node 정보 저장하는 클래스
        // * 1-1. 필드 : 자신의 알파벳, 자식 노드
        char alphabet;
        Node left, right;

        void setValue(char alphabet, char leftC, char rightC) {
            this.alphabet = alphabet;
            this.left = leftC == '.' ? null : tree[leftC - 'A'];
            this.right = rightC == '.' ? null : tree[rightC - 'A'];
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        tree = new Node[N]; // * 2. Node를 저장할 배열
        for (int i = 0; i < N; i++) {
            tree[i] = new Node();
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            char nowAlphabet = st.nextToken().charAt(0);

            Node now = tree[nowAlphabet - 'A'];
            char left = st.nextToken().charAt(0);
            char right = st.nextToken().charAt(0);

            now.setValue(nowAlphabet, left, right); // Node 정보 저장
        }
        // * 3. 트리 순회 구현
        preOrder(tree[0]);
        sb.append('\n');
        inOrder(tree[0]);
        sb.append('\n');
        postOrder(tree[0]);
        sb.append('\n');

        System.out.println(sb);
    }

    // * 3-1. 전위 순회
    private static void preOrder(Node node) {
        sb.append(node.alphabet);
        if (node.left != null) preOrder(node.left);
        if (node.right != null) preOrder(node.right);
    }
    // * 3-2. 중위 순회
    private static void inOrder(Node node) {
        if (node.left != null) inOrder(node.left);
        sb.append(node.alphabet);
        if (node.right != null) inOrder(node.right);
    }
    // * 3-3. 후위 순회
    private static void postOrder(Node node) {
        if (node.left != null) postOrder(node.left);
        if (node.right != null) postOrder(node.right);
        sb.append(node.alphabet);
    }
}