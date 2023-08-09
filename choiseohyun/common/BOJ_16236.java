package tree_graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 아기상어
 * NXN공간에 물고기 M마리와 아기상어 1마리 존재. 한칸당 물고기 최대 1마리 존재
 * 아기상어는 처음에 크기가 2이고, 1초에 상하좌우로 한칸 이동
 * 자기보다 큰 물고기 칸은 지나갈 수 없고, 나머지는 가능
 * 아기상어는 자기보다 작은 물고기만 먹을 수 있음. (크기 같으면 먹을순없지만 지나갈순있다)
 * 
 * 1. 더이상 먹을 물고기 없다면 아기상어는 엄마상어에 도움요청
 * 2. 먹을 수 있는 물고기가 1마리 - 먹으러간다
 * 3. 먹을 수 있는 물고기가 1마리 이상 -> 가까운곳으로 간다
 * 4. 거리는 아기상어가 있는 칸에서 물고기가 있는 칸으로 이동, 지나야하는 칸의 최솟값
 * 5. 거리가 가까운 물고기가 많다면 가장 위에있는 물고기, 그런 물고기가 여러마리면 가장 왼쪽 물고기 먹는다.
 * 
 * 물고기 먹으면 해당칸은 빈칸, 자신의 크기와 같은수의 물고기 먹을때마다 크기 1 증가
 * 0: 빈 칸
 * 1, 2, 3, 4, 5, 6: 칸에 있는 물고기의 크기
 * 9: 아기 상어의 위치
 * output : 몇초동안 엄마 안부르고 물고기 먹을까?
 */
public class BOJ_16236 {
    static int[][] map;
    static boolean[][] check;
    static int babySize=2;
    static int[] dx = {-1,0,0,1};
    static int[] dy = {0,-1,1,0};
    static class Node{
    	int x,y,len;
    	public Node(int x, int y, int len){
    		this.x=x;
    		this.y=y;
    		this.len = len;
    	}
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        boolean[][] check = new boolean[N][N];
        StringTokenizer st;
        int[] baby = new int[2];
        for(int i=0; i<N; i++) {
        	 st = new StringTokenizer(br.readLine());
        	 for(int j=0; j<N; j++) {
        		 map[i][j] = Integer.parseInt(st.nextToken());
        		 if(map[i][j]==9) {
        			 check[i][j] = true;
        			 baby[0] = i;
					 baby[1] = j;
        		 }
        	 }
        }
        
        //아기상어의 탐색 시작
        solution(baby);
        
    }
    
    /* BFS로 먹을수있는 물고기의 최단경로를 탐색한다.
     * 최단경로 물고기가 여러개라면 가장위의 물고기, 이것도 여러개라면 왼쪽 물고기를 선택 => 방향벡터 순서 주의
     * 자기와 같은수의 물고기 먹을떄마다 크기 1 증가 (크기2일때 2마리 먹으면 3됨 - 물고기 크기랑 상관X)
     * 크기작으면 먹을수있고, 같으면 지나갈수만있고, 크면 피해가야함
     * 더이상 먹을 수 있는 물고기 없을때까지 총 이동거리를 계산
     * 
     * 즉 결론적으로 물고기를 먹는 경로의 비용을 계싼...
     */
	private static void solution(int[] baby) { //x,y
		Queue<Node> q = new LinkedList<Node>();
		q.offer(new Node(baby[0],baby[1],0));
		
		while(!q.isEmpty()) {
			Node curr = q.poll();
			for(int i=0; i<4; i++) {
				int nx = curr.x+dx[i];
				int ny = curr.y+dx[i];
				
				if(nx>=0 && ny>=0 && nx<map.length && ny<map[0].length) {
					// 물고기>아기상어 : 못지나감
					
					// 물고기=아기상어 : 지나가기만함
					
					// 물고기<아기상어 : 가서 먹음
					
					//근데 지금은 지나가기만 했더라도 나중가서 먹을수있는거 아님..? 그럼 체크배열 안쓰나?
				}
			}
		}
		
	}

}
