import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 21:00
 * 풀이 완료 : 21:20
 * 풀이 시간 : 20분
 *
 * 문제 해석
 * 루트가 있는 트리가 주어지고 트리 상의 두 정점이 주어질 때 가장 가까운 공통 조상(LCA)는 아래와 같이 정의
 * - 두 노드를 모두 자손으로 가지면서 깊이가 가장 깊은 노드
 * 루트가 있는 트리와 두 노드가 주어질 때 두 노드의 LCA를 구해야 함.
 *
 * 구해야 하는 것
 * 루트가 있는 트리와 두 노드가 주어질 때 두 노드의 LCA를 구해야 함.
 *
 * 문제 입력
 * 첫 줄 : 테스트케이스의 수 T
 * 각 테스트케이스
 * 첫 줄 : 트리를 구성하는 노드의 수 N
 * 다음 줄 ~ N - 1개의 줄 : 트리를 구성하는 간선 정보, A B 형태로 A가 B의 부모라는 의미
 * 마지막 줄 : LCA를 구할 두 노드
 *
 * 제한 요소
 * 1 <= N <= 10000
 * 1 <= 노드의 번호 <= N
 *
 * 생각나는 풀이
 * LCA 구하기
 * 1. 루트를 찾는다 (그래프를 입력받을 때 부모를 저장하고, 부모가 없는 노드가 루트 노드)
 * 2. 루트부터 dfs 탐색하며 각 노드의 깊이를 저장한다
 * 3. LCA를 구할 두 노드의 깊이를 낮은 쪽에 맞춘다
 * 4. 부모가 같을 때까지 두 노드의 부모로 거슬러 올라간다
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 트리 구현
 * 2. 각 노드의 부모를 저장할 배열
 * 3. 각 노드의 깊이를 저장할 배열
 * 4. 루트 노드부터 dfs 탐색
 * 4-1. 탐색하며 현재 노드의 깊이 저장
 * 5. LCA를 구할 두 노드에 대해 깊이를 맞춤
 * 6. 같은 깊이라면 부모가 같을 때까지 부모 노드로 올라감
 */
public class BOJ_3584_LCA {
    static ArrayList<Integer>[] tree;
    static int[] depth;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(br.readLine());

        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());
            tree = new ArrayList[N + 1];
            depth = new int[N + 1]; // * 3. 각 노드의 깊이를 저장할 배열
            parent = new int[N + 1]; // * 2. 각 노드의 부모를 저장할 배열

            for (int i = 1; i <= N; i++) {
                tree[i] = new ArrayList<>();
            }
            // * 1. 입력에 따른 트리 구현
            for (int i = 0; i < N - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int par = Integer.parseInt(st.nextToken());
                int chi = Integer.parseInt(st.nextToken());

                tree[par].add(chi);
                parent[chi] = par;
            }

            for (int i = 1; i <= N; i++) {
                if (parent[i] == 0) { // 부모가 초기값인 노드 => 루트 노드
                    dfs(i, 1); // * 4. 루트 노드부터 dfs 탐색
                    break;
                }
            }

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(findLCA(a, b)).append('\n');
        }

        System.out.println(sb);
    }

    private static void dfs(int idx, int dep) {
        depth[idx] = dep; // * 4-1. 탐색하며 현재 노드의 깊이 저장
        for (int child : tree[idx]) {
            dfs(child, dep + 1);
        }
    }

    private static int findLCA(int a, int b) {
        // * 5. LCA를 구할 두 노드에 대해 깊이를 맞춤
        while (depth[a] > depth[b]) {
            a = parent[a];
        }
        // * 5. LCA를 구할 두 노드에 대해 깊이를 맞춤
        while (depth[b] > depth[a]) {
            b = parent[b];
        }

        if (a == b) return a; // a가 b의 부모거나 b가 a의 부모인 경우 자기 자신을 반환해야 함

        // * 6. 같은 깊이라면 부모가 같을 때까지 부모 노드로 올라감
        while (parent[a] != parent[b]) {
            a = parent[a];
            b = parent[b];
        }

        return parent[b];
    }
}