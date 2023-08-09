package algo.week3.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * info.
 * N개의 도시, 버스 M개
 * A, B, C: A -> cost: C -> B
 * 1번 도시에서 시작해 나머지 도시로 가는 가장 빠른 시간
 *
 * input
 * N(max 500), M(max 6000)
 * M개 줄 - 버스 노선 정보 A,B,C(A,B max N, -10000<=N<=10000)
 *
 * return
 * 무한히 오래전으로 돌릴 수 있으면 -1 출력
 * else 1번에서 출발해 2~N번 도시까지 가는 가장 빠른 시간 출력, 만약 못가면 -1 출력
 *
 */
public class BOJ_11657 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] arr = new int[m][3]; // arr[i] = {A, B, C}
        long[] dist = new long[n+1]; // long으로 변경 X - 출력 초과!
        final int INF = Integer.MAX_VALUE;

        // arr 초기화
		/*
		for(int i=1; i<=n; i++) {
			for (int j = 1; j <= n; j++) {
				if(i==j)
					arr[i][j]=0;
				else
					arr[i][j]=INF;
			}
		}
		*/
        // 버스 경로 입력
        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            //arr[a][b]=c;
            arr[i][0] = a;
            arr[i][1] = b;
            arr[i][2] = c;
        }

        // 1번에서 출발할 때 dist 배열 초기화
        // dist = arr[1].clone();
        for(int i=1; i<n+1; i++)
            dist[i] = INF;
        dist[1] = 0; // dist = {X, 0, INF, INF, ...}

        // 벨만-포드 알고리즘
        boolean isChanged = false;
        for(int i=1; i<=n; i++) { // 정점의 수-1 만큼 반복, 이후 한번 더 반복후 갱신되면 음수순회
            for(int j=0; j<m; j++) { // 모든 간선 탐색
                int start = arr[j][0];
                int end = arr[j][1];
                int cost = arr[j][2];
                // 1-> end = min(기존 경로, 1->start + start->end)
                if(dist[start]==INF) continue; // 만약 1->start 못가면 넘어감
                if(dist[end] > dist[start]+cost) {
                    dist[end] = dist[start]+cost;
                    if(i==n) // n번째에서 갱신 이루어지면 음수순회 확인
                        isChanged = true;
                }
            }
        }
        if(isChanged)
            System.out.println(-1);
        else {
            for(int i=2; i<=n; i++)
                System.out.println(dist[i]==INF?-1:dist[i]);
        }
    }

}