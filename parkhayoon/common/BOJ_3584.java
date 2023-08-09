package algo.week3.common;
/*
 * input
 * 테스트 케이스 개수 T
 * 각 테스트 케이스 마다
 * 첫째 줄: 노드의 수 N(2~10000)
 * N-1개 줄: 간선 정보(A, B - A가 B의 부모, 1이상 N이하 정수)
 * 마지막 줄: 가장 가까운 공통 조상을 구할 두 노드
 *
 * return
 * 각 테스트 케이스 별로 두 노드의 가장 가까운 공통 조상 출력
 *
 * class Node {
 * 		int value; // 노드 번호
 * 		Node parent; // 부모 노드 - 최대 1개
 * 		LinkedList<Node> children; // 자식 노드 - 여러 개
 * }
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

class Node {
    int value;
    int height;
    int parent;
    ArrayList<Integer> children = new ArrayList<Integer>();

    Node(int value, int height, int parent, ArrayList<Integer> children) {
        this.value = value;
        this.height = height;
        this.parent = parent;
        this.children = children;
    }
	/*
	int value;
	int height;
	ArrayList<Node> children = new ArrayList<Node>();

	public Node(int value, int height) {
		this.value = value;
		this.height = height;
	}

	public Node(int value, int height, ArrayList<Node> children) {
		this.value = value;
		this.height = height;
		this.children = children;
	}
	*/
}

public class BOJ_3584 {

    static Stack<Integer> dfsStack;
    static int child1Parent;
    static int child2Parent;
    static Node[] tree;
    static int child1, child2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int t = Integer.parseInt(st.nextToken());
        for(int tc = 0; tc<t; tc++) {

            dfsStack = new Stack<Integer>();
            child1Parent = 0;
            child2Parent = 0;

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            tree = new Node[n+1];
            /*
             * tree 형태
             * {null, Node(value, parent, childList), Node(value, parent, childList), ...}
             */

            for(int i=0; i<n-1; i++) {

                st = new StringTokenizer(br.readLine());
                int parentValue = Integer.parseInt(st.nextToken());
                int childValue = Integer.parseInt(st.nextToken());

                // parentValue가 이미 tree에 있는 경우 이미 있는 parent에 child만 추가
                if(tree[parentValue]!=null) {
                    // tree[parentValue].height = tree[tree[parentValue].height].height+1;
                    tree[parentValue].children.add(childValue);
                    if(tree[childValue]!=null) { // child가 이미 있는 경우 높이 갱신
                        tree[childValue].height = tree[parentValue].height+1;
                        tree[childValue].parent = parentValue;
                    }
                    else { // child가 없는 경우
                        Node newChildNode = new Node(childValue, tree[parentValue].height+1, parentValue, new ArrayList<Integer>());
                        tree[childValue] = newChildNode;
                    }
                }

                // parentValue가 tree에 없는 경우 새로 parent를 추가
                else {
                    Node newNode = new Node(parentValue, 0, 0, new ArrayList<Integer>());
                    newNode.children.add(childValue);
                    tree[parentValue] = newNode;
                    if(tree[childValue]!=null) { // child가 이미 있는 경우 높이 갱신
                        tree[childValue].height = tree[parentValue].height+1;
                        tree[childValue].parent = parentValue;
                    }
                    else { // child가 없는 경우
                        Node newChildNode = new Node(childValue, tree[parentValue].height+1, parentValue, new ArrayList<Integer>());
                        tree[childValue] = newChildNode;
                    }
                }
                //System.out.println(Arrays.toString(tree));
            }

            // root 탐색
            int root = 0;
            for(int i=1; i<=n; i++) {
                if(tree[i].parent==0) {
                    root=i;
                    break;
                }
            }

            st = new StringTokenizer(br.readLine());
            child1 = Integer.parseInt(st.nextToken());
            child2 = Integer.parseInt(st.nextToken());

            // DFS 탐색
            DFS(root);
            ArrayList<Integer> child1ParentList = new ArrayList<Integer>();
            child1ParentList.add(child1);
            ArrayList<Integer> child2ParentList = new ArrayList<Integer>();
            child2ParentList.add(child2);

            while(child1Parent!=0) {
                //System.out.print(child1Parent);
                child1ParentList.add(child1Parent);
                child1Parent = tree[child1Parent].parent;
            }
            //System.out.println();
            while(child2Parent!=0) {
                //System.out.print(child2Parent);
                child2ParentList.add(child2Parent);
                child2Parent = tree[child2Parent].parent;
            }
            //System.out.println();
            int maxHeight = -1;
            int result = 0;
            for(int p1 : child1ParentList) {
                for(int p2 : child2ParentList) {
                    //System.out.println(p2+" "+tree[p2].height);
                    if(p1==p2 && maxHeight <= tree[p2].height) {
                        maxHeight = tree[p2].height;
                        result = tree[p2].value;
                    }
                }
            }
            if(child1==child2)
                result = child1;
            System.out.println(result);

        }
		/*
		int t = Integer.parseInt(st.nextToken());
		for(int tc = 0; tc<t; t++) {
			LinkedList<Node> tree = new LinkedList<Node>();

			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());

			for(int i=0; i<n-1; i++) {
				st = new StringTokenizer(br.readLine());
				int parentValue = Integer.parseInt(st.nextToken());
				int childValue = Integer.parseInt(st.nextToken());

				if(tree.isEmpty()) { // 초기값 설정
					Node newParent = new Node(parentValue, 1);
					Node newChild = new Node(childValue, 0);
					newParent.children.add(newChild);
					tree.add(newParent);
					tree.add(newChild);
				}
				else { // tree에 노드가 있을 때
					// tree 탐색

				}
			}
		}
		*/
    }

    static void DFS(int node) {
        dfsStack.push(node);
        // node가 child인 경우 모든 parent 저장
        if(node==child1) {
            child1Parent = tree[node].parent;
        } else if(node==child2) {
            child2Parent = tree[node].parent;
        }
        // child가 있는 경우
        if(tree[node].children!=null) {
            for(int child : tree[node].children)
                DFS(child);
        }
        // child가 없는 경우 pop
        dfsStack.pop();
    }

}