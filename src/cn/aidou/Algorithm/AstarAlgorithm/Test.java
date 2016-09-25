package cn.aidou.Algorithm.AstarAlgorithm;
import java.util.*;
public class Test {
    
    public static void main(String[] args){
       int[][] map=new int[][]{		  //地图数组
    	   		{1,1,1,1,1,1,1,1,1,1},//map[0]
    	   		{1,1,1,1,0,1,1,1,1,1},//map[1]
                {1,1,1,1,0,1,1,1,1,1},//map[2]
                {1,1,1,1,0,1,1,1,1,1},//map[3]
                {1,1,1,1,0,1,1,1,1,1},//map[4]
                {1,1,1,1,0,1,1,1,1,1},//map[5]
                {1,1,1,1,0,1,1,1,1,1},//map[6]
                {1,1,1,1,0,1,1,1,1,1},//map[7]
                {1,1,1,1,1,1,1,1,1,1} //map[8]
       };
        AStar aStar=new AStar(map, 6, 10);
        int flag=aStar.search(5, 3, 3, 8);//int x1,int y1,int x2,int y2
        if(flag==-1){
            System.out.println("传输数据有误！");
        }else if(flag==0){
            System.out.println("没找到！");
        }else{
            for(int x=0;x< 6;x++){
                for(int y=0;y< 10;y++){
                    if(map[x][y]==1){
                        System.out.print("　");
                    }else if(map[x][y]==0){
                        System.out.print("〓");
                    }else if(map[x][y]==2){//输出搜索路径
                        System.out.print("※");
                    }
                }
                System.out.println();
            }
        }
    }
}
