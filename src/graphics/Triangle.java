package graphics;

public class Triangle implements Comparable<Triangle> {
    public static int NUM_VERTICIES = 3;
    public Vector3[] vertices;

    public float luminescence;

    public Triangle() {
        vertices = new Vector3[NUM_VERTICIES];

        for (int i = 0; i < NUM_VERTICIES; i++) {
            vertices[i] = new Vector3();
        }
    }

    public Triangle(Triangle other) {
        vertices = new Vector3[NUM_VERTICIES];

        for (int i = 0; i < NUM_VERTICIES; i++) {
            vertices[i] = new Vector3(other.vertices[i]);
        }
    }

    public Triangle(Vector3 a, Vector3 b, Vector3 c) {
        vertices = new Vector3[] {a, b, c};
    }

    public Triangle(float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz) {
        vertices = new Vector3[] {new Vector3(ax, ay, az), new Vector3(bx, by, bz), new Vector3(cx, cy, cz)};
    }

    // Compare based on the midpoint of the z value to give an approximation of which triangle is farther than the player
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
