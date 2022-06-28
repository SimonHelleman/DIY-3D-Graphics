package graphics;

public class Matrix4 {
    public final float[][] elements;

    public Matrix4() {
        elements = new float[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                elements[i][j] = 0.0f;
            }
        }
    }

    public Matrix4(Matrix4 other) {
        elements = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                elements[i][j] = other.elements[i][j];
            }
        }
    }

    // // This is not technically correct but it can make for some cleaner code
    // public static Vector3 multiply(Vector3 vec, Matrix4 mat) {
    // Vector3 ret = new Vector3();
    // ret.x = vec.x * mat.elements[0][0] + vec.y * mat.elements[1][0] + vec.z *
    // mat.elements[2][0] + mat.elements[3][0];
    // ret.y = vec.x * mat.elements[0][1] + vec.y * mat.elements[1][1] + vec.z *
    // mat.elements[2][1] + mat.elements[3][1];
    // ret.z = vec.x * mat.elements[0][2] + vec.y * mat.elements[1][2] + vec.z *
    // mat.elements[2][2] + mat.elements[3][2];
    // float w = vec.x * mat.elements[0][3] + vec.y * mat.elements[1][3] + vec.z *
    // mat.elements[2][3] + mat.elements[3][3];

    // if (w != 0.0f) {
    // ret.x /= w;
    // ret.y /= w;
    // ret.z /= w;
    // }

    // return ret;
    // }

    public static Vector4 multiply(Vector4 vec, Matrix4 mat) {
        Vector4 ret = new Vector4();

        ret.x = vec.x * mat.elements[0][0] + vec.y * mat.elements[1][0] + vec.z * mat.elements[2][0]
                + mat.elements[3][0];
        ret.y = vec.x * mat.elements[0][1] + vec.y * mat.elements[1][1] + vec.z * mat.elements[2][1]
                + mat.elements[3][1];
        ret.z = vec.x * mat.elements[0][2] + vec.y * mat.elements[1][2] + vec.z * mat.elements[2][2]
                + mat.elements[3][2];
        ret.w = vec.x * mat.elements[0][3] + vec.y * mat.elements[1][3] + vec.z * mat.elements[2][3]
                + mat.elements[3][3];

        return ret;
    }

    public Matrix4 multiply(Matrix4 other) {
        Matrix4 ret = new Matrix4();
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                ret.elements[row][col] = elements[row][0] * other.elements[0][col]
                        + elements[row][1] * other.elements[1][col] + elements[row][2] * other.elements[2][col]
                        + elements[row][3] * other.elements[3][col];
            }
        }

        return ret;
    }

    public static Matrix4 identity() {
        Matrix4 mat = new Matrix4();
        mat.elements[0][0] = 1.0f;
        mat.elements[1][1] = 1.0f;
        mat.elements[2][2] = 1.0f;
        mat.elements[3][3] = 1.0f;

        return mat;
    }

    public static Matrix4 projection(float near, float far, float fov, float aspectRatio) {
        Matrix4 ret = new Matrix4();

        float fovScale = 1.0f / MathUtil.tanf(MathUtil.radiansf(0.5f * fov));

        ret.elements[0][0] = aspectRatio * fovScale;
        ret.elements[1][1] = fovScale;
        ret.elements[2][2] = far / (far - near);
        ret.elements[3][2] = (-far * near) / (far - near);
        ret.elements[2][3] = 1.0f;
        ret.elements[3][3] = 0.0f;

        return ret;
    }

    public static Matrix4 rotationX(float theta) {
        Matrix4 ret = identity();

        // float radians = MathUtil.radiansf(theta);
        float cos = MathUtil.cosf(theta);
        float sin = MathUtil.sinf(theta);

        ret.elements[1][1] = cos;
        ret.elements[1][2] = sin;
        ret.elements[2][1] = -sin;
        ret.elements[2][2] = cos;
        return ret;
    }

    public static Matrix4 rotationY(float theta) {
        Matrix4 ret = identity();

        float radians = MathUtil.radiansf(theta);
        float cos = MathUtil.cosf(radians);
        float sin = MathUtil.sinf(radians);

        ret.elements[0][0] = cos;
        ret.elements[0][2] = -sin;
        ret.elements[2][0] = sin;
        ret.elements[2][2] = cos;
        return ret;
    }

    public static Matrix4 rotationZ(float theta) {
        Matrix4 ret = identity();

        // float radians = MathUtil.radiansf(theta);
        float cos = MathUtil.cosf(theta);
        float sin = MathUtil.sinf(theta);

        ret.elements[0][0] = cos;
        ret.elements[0][1] = sin;
        ret.elements[1][0] = -sin;
        ret.elements[1][1] = cos;
        return ret;
    }

    public static Matrix4 translation(float x, float y, float z) {
        Matrix4 ret = new Matrix4();

        ret.elements[0][0] = 1.0f;
        ret.elements[1][1] = 1.0f;
        ret.elements[2][2] = 1.0f;
        ret.elements[3][3] = 1.0f;

        ret.elements[3][0] = x;
        ret.elements[3][1] = y;
        ret.elements[3][2] = z;

        return ret;
    }
}
