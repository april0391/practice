package edu.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAddressExample {

    public static void main(String[] args) throws Exception {
        // 1. InetAddress를 사용해 naver.com의 IP 주소를 조회
        /*InetAddress[] addresses = InetAddress.getAllByName("www.naver.com");
        for (InetAddress address : addresses) {
            System.out.println("address = " + address);
        }*/

        // 2. 실제 HTTP 요청을 보내기 위한 URL 설정
        URL url = new URL("https://www.pianodevlab.com/api/policies");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 3. 응답을 읽음
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        InputStream inputStream = connection.getInputStream();
        InputStreamReader in1 = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(in1);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // 4. 결과 출력
        System.out.println("Response content: " + content);
    }
}
