package cn.aidou.Algorithm.AstarAlgorithm;

import java.util.Comparator;

//节点比较类
class NodeFComparator implements Comparator< Node>{
  @Override
  public int compare(Node o1, Node o2) {
      return o1.getF()-o2.getF();
  }
  
}