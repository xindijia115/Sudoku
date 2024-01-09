package xindijia.entity;

/**
 * @author xia
 * @since 2024/1/7/21:24
 */
public class Data {
    private int degree;
    private String time;

    private int no = 1;

    public Data(int degree, String time, int no) {
        this.degree = degree;
        this.time = time;
        this.no = no;
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

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
