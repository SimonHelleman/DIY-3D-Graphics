package graphics;

public class Triangle implements Comparable<Triangle> {
    // I guess following best practices sometimes means stating the obvious
    public static final int NUM_VERTICES = 3;

    public final Vector4[] vertices;

    public float luminescence;

    public Triangle() {
        vertices = new Vector4[NUM_VERTICES];

        for (int i = 0; i < NUM_VERTICES; i++) {
            vertices[i] = new Vector4();
        }
    }

    public Triangle(Triangle other) {
        vertices = new Vector4[NUM_VERTICES];

        for (int i = 0; i < NUM_VERTICES; i++) {
            vertices[i] = new Vector4(other.vertices[i]);
        }
    }

    public Triangle(Vector4 a, Vector4 b, Vector4 c) {
        vertices = new Vector4[] { a, b, c };
    }

    public Triangle(float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz) {
        vertices = new Vector4[] { new Vector4(ax, ay, az), new Vector4(bx, by, bz), new Vector4(cx, cy, cz) };
    }

    // Compare based on the midpoint of the z value to give an approximation of
    // which triangle is farther than the player
    @Override
    public int compareTo(Triangle o) {
        float thisAvg = (vertices[0].z + vertices[1].z + vertices[2].z) / 3.0f;
        float otherAvg = (o.vertices[0].z + o.vertices[1].z + o.vertices[2].z) / 3.0f;

        if (thisAvg < otherAvg) {
            return 1;
        } else if (thisAvg > otherAvg) {
            return -1;
        }
        return 0;
    }
}
