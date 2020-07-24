package html_analysis;

import java.io.File;
import java.util.ArrayList;


public class readFromFile {
  ArrayList<String> fileNames = new ArrayList<String>();
  
  public readFromFile() {
    
  }
  File files = new File("F:/myFiletest/text3");
  //创建文件对象，url是目标目录
  if (files.exists()) { //目录是否存在
      File[] files2 = files.listFiles();// 读取文件夹下的所有文件
      int m = 0, n = 0;
      for (int i = 0; i < files2.length; i++) {
          if (files2[i].isFile()) { //判断是否是文件
              m++;
              System.out.println("第" + m + "个文件的名字是：" + files2[i].getName());
          } else {
              n++;
              System.out.println("第" + n + "个目录的名字是：" + files2[i].getName());
          }
      }
  } else {
      System.out.println("文件不存在");
  }

}
}
