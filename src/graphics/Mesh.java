package graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Mesh implements Iterable<Triangle> {
    private ArrayList<Triangle> triangles;

    public Mesh() {
        triangles = new ArrayList<Triangle>();
    }

    private Mesh(int initSize) {
        triangles = new ArrayList<Triangle>(initSize);
    }

    public void addTriangle(Triangle t) {
        triangles.add(t);
    }

    public static Mesh generateFromObj(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        ArrayList<Vector3> vertices = new ArrayList<Vector3>();
        String line = reader.readLine();

        int linesRead = 1;

        Mesh ret = new Mesh();

        while (line != null) {
            // read a vertex
            if (line.length() != 0) {
                if (line.charAt(0) == 'v') {

                    // cut off the first two characters "v "
                    line = line.substring(2);

                    String tmp = line.substring(0, line.indexOf(' ')); // Get number
                    float x = Float.parseFloat(tmp);
                    line = line.substring(line.indexOf(' ') + 1); // Cut off number just read

                    tmp = line.substring(0, line.indexOf(' '));
                    float y = Float.parseFloat(tmp);
                    line = line.substring(line.indexOf(' ') + 1);

                    float z = Float.parseFloat(line);

                    vertices.add(new Vector3(x, y, z));
                }

                // read a triangle
                if (line.charAt(0) == 'f') {
                    int[] arr = new int[3];

                    // cut off the first two characters "v "
                    line = line.substring(2);

                    String tmp = line.substring(0, line.indexOf(' ')); // Get number
                    arr[0] = Integer.parseInt(tmp);
                    line = line.substring(line.indexOf(' ') + 1); // Cut off number just read

                    tmp = line.substring(0, line.indexOf(' '));
                    arr[1] = Integer.parseInt(tmp);
                    line = line.substring(line.indexOf(' ') + 1);

                    arr[2] = Integer.parseInt(line);

                    // subtract 1 since object files start counting at 1 and not 0
                    Triangle t = new Triangle(new Vector4(vertices.get(arr[0] - 1), 1.0f),
                            new Vector4(vertices.get(arr[1] - 1), 1.0f), new Vector4(vertices.get(arr[2] - 1), 1.0f));
                    ret.triangles.add(t);
                }
            }

            line = reader.readLine();
            linesRead++;
        }

        System.out.printf("read %d lines from %s\n", linesRead, filePath);
        reader.close();

        return ret;
    }

    public static Mesh getCube() {
        Mesh cube = new Mesh(12);

        // south
        cube.addTriangle(new Triangle(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f));
        cube.addTriangle(new Triangle(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f));

        // east
        cube.addTriangle(new Triangle(1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        cube.addTriangle(new Triangle(1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f));

        // north
        cube.addTriangle(new Triangle(1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f));
        cube.addTriangle(new Triangle(1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f));

        // west
        cube.addTriangle(new Triangle(0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f));
        cube.addTriangle(new Triangle(0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        // top
        cube.addTriangle(new Triangle(0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f));
        cube.addTriangle(new Triangle(0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f));

        // bottom
        cube.addTriangle(new Triangle(1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f));
        cube.addTriangle(new Triangle(1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));

        return cube;
    }

    @Override
    public Iterator<Triangle> iterator() {
        return triangles.iterator();
    }
}
