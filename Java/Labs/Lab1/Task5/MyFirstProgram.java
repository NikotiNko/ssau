import java.util.Scanner;
import MyFirstPackage.MyFirstPackage;

class MyFirstClass{

	public static void main(String[] args){
		MyFirstPackage second = new MyFirstPackage(10);

		Scanner in = new Scanner(System.in);
		System.out.print("Введите значение 1 элемента:");
		second.setElement(0, in.nextInt());

		second.printArray();
	}
}