package MyFirstPackage;

public class MyFirstPackage{

	private int[] array;

	public MyFirstPackage(int size){
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