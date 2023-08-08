package group;
/**
 * 풀이 시작 :
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 캐슬 디펜스
 * N * M 격자판에서 수행
 * 각 칸에 포함된 적의 수는 최대 하나
 * 격자판의 N번행 바로 아래의 모든 칸에는 성이 있음
 * 성이 있는 위치에 궁수 3명 배치하려고 함
 * 각 턴마다 궁수는 적 하나를 공격하고, 모든 궁수는 동시에 공격함
 * 궁수가 공격하는 적은 거리가 D 이하인 적 중에 가장 가까운 적, 같은 거리라면 가장 왼쪽
 * 같은 적을 여러 명의 궁수가 공격할 수 있음
 * 궁수의 공격이 끝나면 적이 아래로 한 칸 이동
 * 성이 있는 칸으로 이동하는 경우 사라짐
 *
 * 구해야 하는 것
 * 궁수의 공격으로 제거할 수 있는 적의 최대 수
 *
 * 문제 입력
 * 첫째 줄 : 행의 수 N, 열의 수 M, 궁수의 사거리 D
 * 둘째 줄 ~ N개 줄 : 격자판의 상태 (0 = 빈 칸, 1 = 적)
 *
 * 제한 요소
 * 3 <= N, M, 15
 * 1 <= D <= 10
 *
 * 생각나는 풀이
 * 궁수를 놓을 수 있는 모든 조합을 탐색한 후 시뮬레이션해서 가장 많이 제거하는 경우를 찾음
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 게임판의 초기 상태 저장
 * 2. 궁수를 놓을 위치 조합 탐색
 * 3. 모든 궁수의 위치가 정해졌다면 모든 적이 사라질 때까지 매 턴 시뮬레이션
 * 4. 공격할 대상을 탐색하는 건 BFS, 우선순위 큐 이용으로 같은 거리 적에 대해 우선순위 처리
 * 5. 모든 궁수의 공격 타겟을 설정한 후 적 제거 수행
 * 6. 모든 적이 제거되었다면 최댓값 갱신
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_17135_캐슬디펜스 {
	static int N, M, D, maxKilled = 0, nowKilled = 0, numOfEnemies = 0;
	
	static char[][] map;
	static int[] dx = {-1, 0, 0}; // 상좌우
	static int[] dy = {0, -1, 1}; // 상좌우
	static Node[] archer = new Node[3];
	static ArrayList<Node> enemies = new ArrayList<>();

	static class Node implements Comparable<Node> {
		int x, y, dist;

		public Node(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int getDist(Node o) {
			return Math.abs(this.x - o.x) + Math.abs(this.y - o.y);
		}
		
		@Override
		public int compareTo(Node o) {
			if (this.dist == o.dist) {
				return this.y - o.y;
			}
			return this.dist - o.dist;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		// * 1. 입력에 따른 게임판의 초기 상태 저장
		map = new char[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = st.nextToken().charAt(0);
				if (map[i][j] == '1') numOfEnemies++;
			}
		}
		
		for (int i = 0; i < 3; i++) {
			archer[i] = new Node(N - 1, 0, 1);
		}
		
		generateArcherIdx(0, 0);
		System.out.println(maxKilled);
	}

	private static void generateArcherIdx(int depth, int idx) {	// * 2. 궁수를 놓을 위치 조합 탐색
		if (depth == 3) {
			maxKilled = Math.max(maxKilled, simulation()); // * 6. 모든 적이 제거되었다면 최댓값 갱신
			if (maxKilled == numOfEnemies) {
				System.out.println(numOfEnemies);
				System.exit(0);
			}
			return;
		}
		
		for (int i = idx; i < M; i++) {
			archer[depth].y = i;
			generateArcherIdx(depth + 1, i + 1);
		}
	}

	private static int simulation() { // * 3. 모든 궁수의 위치가 정해졌다면 모든 적이 사라질 때까지 매 턴 시뮬레이션
		char[][] nowMap = new char[N][];

		for (int i = 0; i < map.length; i++) {
			nowMap[i] = map[i].clone();
		}
		
		nowKilled = 0;
		
		for (int i = 0; i < N; i++) {
			Node[] selected = new Node[3];
			for (int j = 0; j < 3; j++) {
				selected[j] = bfs(archer[j], nowMap);
			}

			for (int j = 0; j < selected.length; j++) { // * 5. 모든 궁수의 공격 타겟을 설정한 후 적 제거 수행
				Node now = selected[j];
				if (now == null) continue;
				if (nowMap[now.x][now.y] == '1') {
					nowMap[now.x][now.y] = '0';
					nowKilled++;
					if (nowKilled == numOfEnemies) {
						return nowKilled;
					}
				}
			}
			
			for (int j = N - 1; j > 0; j--) {
				nowMap[j] = nowMap[j - 1].clone();
			}
			
			Arrays.fill(nowMap[0], '0');
						
		}	
		
		return nowKilled;
	}

	private static Node bfs(Node start, char[][] nowMap) {
		// * 4. 공격할 대상을 탐색하는 건 BFS, 우선순위 큐 이용으로 같은 거리 적에 대해 우선순위 처리
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[][] visited = new boolean[N][M];
		visited[start.x][start.y] = true;
		pq.offer(start);
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			
			if (now.dist > D) continue;
			
			if (nowMap[now.x][now.y] == '1') {
				return new Node(now.x, now.y);
			}
			
			for (int i = 0; i < 3; i++) {
				int nextX = now.x + dx[i];
				int nextY = now.y + dy[i];
				
				if (!isInRange(nextX, nextY) || visited[nextX][nextY]) continue;
				pq.offer(new Node(nextX, nextY, now.dist + 1));
			}
		}
		
		return null;
	}

	private static boolean isInRange(int x, int y) {
		return x >= 0 && y >= 0 && y < M;
	}
}