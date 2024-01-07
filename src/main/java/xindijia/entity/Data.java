package xindijia.entity;

/**
 * @author xia
 * @since 2024/1/7/21:24
 */
public class Data {
    private int degree;
    private String time;

    public Data(int degree, String time) {
        this.degree = degree;
        this.time = time;
    }

    public Data() {
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
