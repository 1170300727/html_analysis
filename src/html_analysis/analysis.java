package html_analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class analysis {
  
  public static void main(String[] agrs)
  {       
    String fileName = "lib/index.html";
    String html = readFileByLines(fileName);
    System.out.println(html);
    
    String s1 = "公司";
    String cigar;
    int resultNum = 10;
    smith_waterman.smith sm = new smith_waterman.smith(html, s1, resultNum);
    sm.compute();
    sm.getMaxPositions();
    //sm.printScoreMatirx();
    System.out.println();
    //sm.printBack();
    int maxi = sm.getMaxI();
    int maxScore = sm.getMaxScore();
    sm.printResults();
  }
  
  public static String readFileByLines(String fileName) {
    File file = new File(fileName);
    BufferedReader reader = null;
    StringBuilder result=new StringBuilder();
    try {
        System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
        reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        int line = 1;
        
        // һ�ζ���һ�У�baiֱ������nullΪ�ļ�����
        while ((tempString = reader.readLine()) != null) {
            // ��ʾ�к�
            //System.out.println("line " + line + ": " + tempString);
            result.append(tempString + "\n");
            line++;
        }
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e1) {
            }
        }
    }
    return result.toString();
  }
}
