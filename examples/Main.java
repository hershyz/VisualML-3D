public class Main {

    public static void main(String[] args) {

        visualml3d vml3d = new visualml3d("data.txt");
        vml3d.init();
        vml3d.printSummary();
        String predictedCategory = vml3d.predictCategory(20, 20, 20);
        System.out.println(testCategory);
    }
}
