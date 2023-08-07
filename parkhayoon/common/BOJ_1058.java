package algostudy.week3.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * info.
 * 어떤 사람 A가 또다른 사람 B의 2-친구가 되기 위해선,
 * 두 사람이 친구이거나,
 * A와 친구이고, B와 친구인 C가 존재
 * 가장 유명한 사람은 2-친구의 수가 가장 많은 사람
 * A와 B가 친구면, B와 A도 친구이고, A와 A는 친구가 아님
 *
 * input
 * 첫째 줄에 사람의 수 N, N은 50보다 작거나 같은 자연수
 * 둘째 줄부터 N개의 줄에 각 사람이 친구이면 Y, 아니면 N
 *
 * return
 * 첫째 줄에 가장 유명한 사람의 2-친구의 수를 출력(최대 2-친구의 수)
 *
 * idea
 * 알고리즘: 플로이드-워셜
 * 모든 지점에서 다른 모든 지점까지의 최단 경로를 모두 구해야 하는 경우
 * 친구인 경우 2가지
 * 1) 직접 친구인 경우: arr[i][j] = 'Y'
 * 2) 한 명을 거쳐 친구인 경우: arr[i][k]=='Y' && arr[k][j]=='Y'
 */
public class BOJ_1058 {

    static int N;
    static char arr[][], checkFr[];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        arr = new char[N][N];


        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            char[] charArray = st.nextToken().toCharArray();
            for(int j=0; j<N; j++)
                arr[i][j] = charArray[j];
        }

        floydwarshall();

    }

    static void floydwarshall() {
        int max = 0;
        for(int i=0; i<N; i++) {
            int count = 0;
            checkFr = new char[N]; // i에서 출발할 때 다른 사람이 2-친구인지 저장
            for(int j=0; j<N; j++) {
                for(int k=0; k<N; k++) { // i->j, i->k->j가 되는지 비교
                    if (arr[i][j]=='Y') // 1) 직접 친구인 경우
                        checkFr[j] = 'Y'; // 2-친구 여부를 저장
                    else if(i!=k && k!=j && i!=j) { // k가 i, j와 다른 사람일 때만 확인
                        if(arr[i][k]=='Y' && arr[k][j]=='Y') // 2) 한 명을 거친 친구인 경우
                            checkFr[j] = 'Y'; // 2-친구 여부를 저장
                    }
                }
                if(checkFr[j] == 'Y') // 탐색 후 2-친구 여부 확인 후
                    count++; // count 증가
            }
            if(max < count) // 최대 2-친구 수 저장
                max = count;
        }
        System.out.println(max);
    }

}