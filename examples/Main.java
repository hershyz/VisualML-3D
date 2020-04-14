public class Main {

    public static void main(String[] args) {

        visualml3d vml3d = new visualml3d("c:/users/bagal/desktop/data.txt");
        vml3d.init();
        String testCategory = vml3d.predictCategory(20, 20, 20);
        System.out.println(testCategory);
    }
}
