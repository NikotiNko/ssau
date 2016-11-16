import java.lang.Math;
import java.util.Scanner;

class MyFirstClass{

	public static void main(String[] args){
		MySecondClass second = new MySecondClass(10);

		Scanner in = new Scanner(System.in);
		System.out.print("Введите значение 1 элемента:");
		second.setElement(0, in.nextInt());

		second.printArray();
	}
}

class MySecondClass{

	private int[] array;

	public MySecondClass(int size){
		array = new int[size];

		for (int i = 0; i < size; i++) {
			array[i] = (int)(Math.random()*100);
		}
	}

	public int getElement(int index){
		return array[index];
	}

	public void setElement(int index, int value){
		array[index] = value;
	}

	public void printArray(){
		for (int element: array) {
			System.out.print(element + ", ");
		}
	}

}