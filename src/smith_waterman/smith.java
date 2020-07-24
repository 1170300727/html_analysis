package smith_waterman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class smith {
  private String s1; // the reference string
  private String s2; // the read
  private int resultNum;
  private int penaltyInsertion = 1;
  private int penaltyDeletion = 1;
  private int penaltyMatch = 2;
  private int score[][];
  private int maxScore;
  private int maxI;
  ArrayList<Integer> maxScores = new ArrayList<Integer>();
  ArrayList<Integer> maxIs = new ArrayList<Integer>();
  ArrayList<position> maxPositions = new ArrayList<position>();
  // 0--end(not include),1--up,2--left,3--leftup,4--end(include)
  private int back[][];
  private HashMap<String , Integer> penaltyMatrix = new HashMap<String , Integer>();
  
  
  public smith(String s1, String s2, int resultNum) {
    this.resultNum = resultNum;
    this.s1 = s1;
    this.s2 = s2;
    penaltyMatrix.put("AA", -5);
    penaltyMatrix.put("CC", -5);
    penaltyMatrix.put("GG", -5);
    penaltyMatrix.put("TT", -5);
    penaltyMatrix.put("AC", 4);
    penaltyMatrix.put("AG", 4);
    penaltyMatrix.put("AT", 4);
    penaltyMatrix.put("CA", 4);
    penaltyMatrix.put("CG", 4);
    penaltyMatrix.put("CT", 4);
    penaltyMatrix.put("TA", 4);
    penaltyMatrix.put("TC", 4);
    penaltyMatrix.put("TG", 4);
    penaltyMatrix.put("GA", 4);
    penaltyMatrix.put("GC", 4);
    penaltyMatrix.put("GT", 4);
  }
  
  public void getMaxPositions() {
    int max = 0;
    int maxI = 0;
    int maxJ = 0;
    int MinInMax = 999;
    int MinInMaxPosition = 999;
    int m = s1.length() + 1;
    int n = s2.length() + 1;
    
    for (int i = 0;i < m;i++) { 
      for (int j = 0;j < n;j++) {
        if(score[i][j] > max) {
          max = score[i][j];
          maxI = i;
          maxJ = j;      
        }
        if (this.maxScores.size() < resultNum) {
          position p = new position(i, j);
          this.maxPositions.add(p);
          this.maxScores.add(score[i][j]);
          if (max < MinInMax) {
            MinInMax = max;
          }
        } else {
          MinInMax = Collections.min(maxScores);
          MinInMaxPosition = maxScores.indexOf(MinInMax);
          if (score[i][j] > MinInMax) {
            maxScores.remove(MinInMaxPosition);
            maxPositions.remove(MinInMaxPosition);
            maxScores.add(score[i][j]);
            position p = new position(i, j);
            maxPositions.add(p);
          }
        }
      }
    }
    
    for (position p: maxPositions) {
      maxIs.add(getMaxI(p.x, p.y));
    }
  }
  
  public int getMaxI(int maxI, int maxJ) {
    
    String result = "";
    while (back[maxI][maxJ] != 0 && back[maxI][maxJ] != 4) {
      if (back[maxI][maxJ] == 1) {
        result = result + "I";
        maxI--;
      } else if (back[maxI][maxJ] == 2) {
        result = result + "D";
        maxJ--;
      } else if (back[maxI][maxJ] == 3) {
        result = result + "M";
        maxI--;
        maxJ--;
      }
      this.maxI = maxI;
    }
    result = new StringBuffer(result).reverse().toString();
    return this.maxI;
  }
  
  public void compute() {
    s1 = s1.toLowerCase();
    s2 = s2.toLowerCase();
    int m = s1.length();
    int n = s2.length();
    score = new int[m + 1][n + 1];
    back = new int[m + 1][n + 1];
    int[] scores = new int[4];
    int[] notSortScores = new int[4];
    String connect;
    int tempScore;
    int max;
    for (int i = 0;i < n + 1;i++) { //column
      for (int j = 0;j < m + 1;j++) { //crow
//        connect = String.valueOf(s1.charAt(j)) + String.valueOf(s2.charAt(i));
//        tempScore = penaltyMatrix.get(connect);
        
        if (i == 0 && j == 0) { 
//          if (tempScore > 0) {
//            score[j][i] = tempScore;
//            back[j][i] = 4;
//          } else {
//            score[j][i] = 0;
//            back[j][i] = 0;
//          }
          score[j][i] = 0;
          back[j][i] = 0;
        } else if (i == 0 && j != 0) { //the first column
//          if (score[j - 1][i] - penaltyInsertion > 0) {
//            score[j][i] = score[j - 1][i] - penaltyInsertion;
//            back[j][i] = 4;
//          } else {
//            score[j][i] = 0;
//            back[j][i] = 0;
//          }
          score[j][i] = 0;
          back[j][i] = 0;
        } else if (i != 0 && j == 0) { //the first row
//          if (score[j][i - 1] - penaltyDeletion > 0) {
//            score[j][i] = score[j][i - 1] - penaltyDeletion;
//            back[j][i] = 4;
//          } else {
//            score[j][i] = 0;
//            back[j][i] = 0;
//          }
          score[j][i] = 0;
          back[j][i] = 0;
        } else { //normal situation
          char c1 = s1.charAt(j - 1);
          char c2 = s2.charAt(i - 1);
          if (c1 == c2) {
            tempScore = -penaltyMatch;
          } else {
            tempScore = penaltyMatch;
          }
          scores[0] = 0;
          scores[1] = score[j - 1][i] - penaltyInsertion;
          scores[2] = score[j][i - 1] - penaltyDeletion;
          scores[3] = score[j - 1][i - 1] - tempScore;
          notSortScores[0] = 0;
          notSortScores[1] = score[j - 1][i] - penaltyInsertion;
          notSortScores[2] = score[j][i - 1] - penaltyDeletion;
          notSortScores[3] = score[j - 1][i - 1] - tempScore;
          Arrays.sort(scores); // ascending sort
          max = scores[3];
          score[j][i] = max;
          if (max == notSortScores[0]) {
            back[j][i] = 0;
          } else if (max == notSortScores[1]) {
            back[j][i] = 1;
          } else if (max == notSortScores[2]) {
            back[j][i] = 2;
          } else if (max == notSortScores[3]) {
            back[j][i] = 3;
          }
        }
      }
    }
  }
  
  public void printScoreMatirx() {
    int m = s1.length() + 1;
    int n = s2.length() + 1;
    System.out.print("\t\t");
    for (int i = 0;i < n - 1;i++) { 
      System.out.print(s2.charAt(i) + "\t");
    }
    for (int i = 0;i < m;i++) { 
      if (i == 0) {
        System.out.print("\t");
      } else {
        System.out.print(s1.charAt(i - 1) + "\t");
      }
      for (int j = 0;j < n;j++) {
        System.out.print(score[i][j] + " " + back[i][j] + "\t");
      }
      System.out.println();
    }
  }
  
  public void printResults() {
    System.out.println("matcher:" + s2 + "\n");
    int maxScore = 0;
    int maxi = 0;
    for (int i = 0;i < resultNum;i++) {
      maxScore = maxScores.get(i);
      maxi = maxIs.get(i);
      System.out.println("bestMatch:" + s1.substring(maxi, maxi + 10));
      System.out.println("maxscore:" + maxScore);
      System.out.println("bestPosition:" + maxi);
      System.out.println();
    } 
    
  }
  
  public void printBack() {
    int m = s1.length() + 1;
    int n = s2.length() + 1;
    for (int i = 0;i < m;i++) { 
      for (int j = 0;j < n;j++) {
        System.out.print(back[i][j] + "\t");
      }
      System.out.println();
    }
  }
  
  public int getMaxScore() {
    return this.maxScore;
  }
  
  public int getMaxI() {
    return this.maxI;
  }
}

class position {
  int x;
  int y;
  
  public position(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
}