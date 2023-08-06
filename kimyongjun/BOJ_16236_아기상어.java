/**
 * 풀이 시작 : 20:55
 * 풀이 완료 : 21:39
 * 풀이 시간 : 44분
 *
 * 문제 해석
 * N * N 크기의 공간에 물고기 M마리와 아기 상어 1마리가 있다.
 * 한 칸에는 물고기가 최대 한 마리 존재한다.
 * 아기 상어와 물고기는 모두 크기를 가지고 있다. 아기 상어의 크기는 2부터 시작한다.
 * 아기 상어는 1초에 상하좌우 한 칸 이동 가능하다.
 * 아기 상어는 자기보다 큰 물고기가 있는 칸은 지나가지 못한다.
 * 아기 상어는 자기보다 작은 물고기만 먹을 수 있다.
 * 아기 상어는 먹을 수 있는 가장 가까운 물고기를 먹으러 간다. 만약 먹을 수 있는 물고기가 없다면 종료된다.
 * 아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때마다 크기가 1 커진다.
 *
 * 구해야 하는 것
 * 아기 상어가 몇 초 동안 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는지
 *
 * 문제 입력
 * 첫째 줄 : 공간의 크기 N
 * 둘째 줄 ~ N개의 줄 : 공간의 상태
 *  0 : 빈 칸
 *  1,2,3,4,5,6 : 물고기의 크기
 *  9 : 아기 상어의 위치
 *
 * 제한 요소
 * 2 <= N <= 20
 *
 * 생각나는 풀이
 * 아기 상어의 위치와 크기를 담은 클래스를 정의한다.
 * 먹을 수 있는 물고기가 없을 때까지 아래 과정을 반복한다.
 * 1. 현재 아기 상어의 위치에서 BFS 탐색을 통해 가장 먼저 만나는 먹이를 찾는다.
 * 2. 먹이를 찾았다면 먹이의 위치로 이동하고 이동 소요 시간을 더한다. 먹은 물고기의 수를 증가시킨다.
 * 2-1. 만약 먹은 물고기의 수가 현재 상어의 크기와 같아지면 먹은 수를 0으로 초기화하고 상어의 크기를 1 증가시킨다.
 * 2-2. 탐색 우선순위는 거리, 같다면 행 좌표 작은 순, 같다면 열 좌표 작은 순이어야 함.
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 초기 맵 저장
 * 2. 상어의 상태를 저장하는 클래스
 * 3. 현재 상어의 위치에서부터 BFS 탐색
 * 3-1. 이동 가능한 칸 : 현재 상어의 크기보다 작거나 같은 칸
 * 3-2. 먹이가 있는 칸 : 1 <= 칸에 적힌 수 < 현재 상어의 크기 인 칸
 * 3-3. 탐색 순서 ?? 일반 Queue이면 안된다. PriorityQueue를 사용해서 먹을 먹이의 우선순위를 정해야 함.
 * 4. 가장 처음 발견한 먹이의 위치로 상어 이동 후 먹은 먹이 수 증가, 거리만큼 시간 증가
 * 4-1. 먹은 먹이 수가 상어 크기와 같다면 먹은 먹이 수 0으로 갱신 후 상어 크기 1 증가
 * 5. 3 ~ 4번 과정을 먹을 먹이가 없을 때까지 반복
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_16236_아기상어 {
    static int N, sharkSize = 2, totalTime = 0, eatenFood = 0; // sharkSize = 상어 크기, totalTime = 종료까지 시간, eatenFood = 현재 크기에서 먹은 물고기 수
    static int[][] water;
    static Shark shark = null;
    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};

    static class Shark implements Comparable<Shark> { // * 2. 상어의 상태를 저장하는 클래스
        int x, y, time;

        public Shark(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        @Override
        public int compareTo(Shark o) { // PriorityQueue 사용하기 위해 compareTo Override
            if (this.time == o.time) { // 시간 같다면
                if (this.x == o.x) { // 행 좌표 같다면
                    return this.y - o.y; // 열 좌표 작은 순
                }
                return this.x - o.x; // 행 좌표 작은 순
            }
            return this.time - o.time; // 시간 작은 순 (가까운 순)
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        // * 1. 입력에 따른 초기 맵 저장
        water = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                water[i][j] = Integer.parseInt(st.nextToken());
                if (water[i][j] == 9) {
                    shark = new Shark(i, j, 0);
                    water[i][j] = 0;
                }
            }
        }

        while (true) { // * 5. 3 ~ 4번 과정을 먹을 먹이가 없을 때까지 반복
            if (!findFood()) break;
        }

        System.out.println(totalTime);
    }

    private static boolean findFood() { // * 3. 현재 상어의 위치에서부터 BFS 탐색
        PriorityQueue<Shark> pq = new PriorityQueue<>(); // * 3-3. 탐색 순서 ?? 일반 Queue이면 안됨. PriorityQueue를 사용해서 먹을 먹이의 우선순위를 정해야 함.
        boolean[][] visited = new boolean[N][N];
        pq.offer(shark);
        visited[shark.x][shark.y] = true;

        while (!pq.isEmpty()) {
            Shark now = pq.poll();

            if (isFood(now)) { // * 3-2. 먹이가 있는 칸 : 1 <= 칸에 적힌 수 < 현재 상어의 크기 인 칸
                eatFood(now);
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = now.x + dx[i];
                int nextY = now.y + dy[i];

                // * 3-1. 이동 가능한 칸 : 현재 상어의 크기보다 작거나 같은 칸
                if (!isInRange(nextX, nextY) || visited[nextX][nextY] || sharkSize < water[nextX][nextY]) continue;
                pq.offer(new Shark(nextX, nextY, now.time + 1));
                visited[nextX][nextY] = true;
            }
        }

        return false;
    }

    private static void eatFood(Shark now) { // * 4. 가장 처음 발견한 먹이의 위치로 상어 이동 후 먹은 먹이 수 증가, 거리만큼 시간 증가
        water[now.x][now.y] = 0;
        totalTime += now.time;
        shark = new Shark(now.x, now.y, 0);

        // * 4-1. 먹은 먹이 수가 상어 크기와 같다면 먹은 먹이 수 0으로 갱신 후 상어 크기 1 증가
        if (++eatenFood == sharkSize) {
            eatenFood = 0;
            sharkSize++;
        }
    }

    private static boolean isFood(Shark shark) { // * 3-2. 먹이가 있는 칸 : 1 <= 칸에 적힌 수 < 현재 상어의 크기 인 칸
        return water[shark.x][shark.y] > 0 && water[shark.x][shark.y] < sharkSize;
    }

    private static boolean isInRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}
