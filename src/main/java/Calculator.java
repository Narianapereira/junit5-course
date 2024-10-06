public class Calculator {

    public int soma(int a, int b){
        return a + b;
    }

    public float division(int a, int b){
        return (float) a / b;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.division(10, 0));
    }
}
