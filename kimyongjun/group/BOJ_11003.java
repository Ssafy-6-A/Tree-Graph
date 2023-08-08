package group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ_11003 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        // int[] {값, 인덱스}
        Deque<int[]> deque = new ArrayDeque<>();
        st = new StringTokenizer(br.readLine());
        // 슬라이딩 윈도우, Deque
        // 현재 값보다 Deque에 들어있는 이전 값들이 크기가 크다면?
        // 그 값들은 사용될 일이 없다 => deque에서 삭제
        // 자동적으로 Deque의 맨 앞에 들어있는 수가 최솟값
        // 하지만 맨 앞의 인덱스가 L보다 멀다면? => 사용 불가하므로 deque에서 삭제
        // 최종적으로 deque의 맨 앞에 있는 값이 현재 인덱스에서 최솟값
        // O(N) 복잡도
        for (int i = 0; i < N; i++) {
            int nowValue = Integer.parseInt(st.nextToken());
            while (!deque.isEmpty() && deque.peekLast()[0] >= nowValue) deque.pollLast();
            if (!deque.isEmpty() && deque.peekFirst()[1] <= i - L) deque.pollFirst();
            deque.offerLast(new int[] {nowValue, i});

            sb.append(deque.peekFirst()[0]).append(' ');
        }

        System.out.println(sb);
    }
}
