package cn.aidou.Algorithm.BubbleSort;

import java.util.Scanner;

/**
 * 冒泡排序 2016/1/10 15:15
 * 
 * @author aidou
 */
public class BubbleSort {
	/*
	 * 冒泡排序
	 */
	public static void main(String[] args) {
		Integer[] list = { 49, 38, 65, 97, 76, 13, 27, 14, 10 };
		// 冒泡排序
		bubble(list);
		for (int i = 0; i < list.length; i++) {
			System.out.print(list[i] + " ");
		}
		System.out.println();
	}

	/**
	 * 将最大的下沉到最后一位
	 * 
	 * @param data
	 *            49, 38, 65, 97, 76, 13, 27, 14, 10
	 */
	public static void bubble(Integer[] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length - 1 - i; j++) {
				if (data[j] > data[j + 1]) { // 如果后一个数小于前一个数交换
					int tmp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = tmp;
				}
			}
		}
	}
}
