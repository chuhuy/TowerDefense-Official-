package sample;
import java.io.*;

public class Helper {

    public String[][] getMapFromText(int level) throws IOException {
        String[][] result = new String[20][20];
        File file = new File("src/main/java/maps/map" + level + ".txt");
        InputStream in = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line;
        int i = 0;
        while ((line = br.readLine()) != null) {
            for(int j = 0; j < 20;  j++){
                result[i][j] = line.substring(j*4, j*4 + 3);
            }
            i++;
        }

        return result;
    }


    public double ijToX(int i, int j){
        return ((double)j - (double)i) * 32.0 / 1.55 + 600 - 80;
    }
    public double ijToY(int i, int j){
        return ((double)j + (double)i) * 32.0 / 3.1;
    }
    public int xyToI(double x, double y){
        return (int)((3.1 * y - 1.55 * (x - 520)) / 64 + 0.5);
    }
    public int xyToJ(double x, double y){
        return (int)((3.1 * y + 1.55 * (x - 520)) / 64 + 0.5);
    }

    //public double getDistanceInXY(GameEntity a, GameEntity b){
    //    return Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY()));
    //}

    public double getDistanceInIJ(GameEntity a, GameEntity b){
        int ia = xyToI(a.getX(), a.getY());
        int ib = xyToI(b.getX(), b.getY());
        int ja = xyToJ(a.getX(), a.getY());
        int jb = xyToJ(b.getX(), b.getY());

        return Math.sqrt((ia - ib) * (ia - ib) + (ja - jb) * (ja - jb));
    }
    public Helper(){

    }
}
