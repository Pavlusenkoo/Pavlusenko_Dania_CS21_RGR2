import java.util.ArrayList;

public class DataSheet {
    private double sumX, sumY, sumX2, sumXY;
    private double k, b;
    private ArrayList <Data> dataArray;

    public void addData(Data data){
        dataArray.add(data);

        double x = data.getX();
        double y = data.getX();

        sumXY += (x*y);
        sumX += x;
        sumY += y;
        sumX2 += (x*x);
    }

    public void removeData(int pos){
        Data data = dataArray.get(pos);
        dataArray.remove(pos);
        double x = data.getX();
        double y = data.getX();

        sumXY -= (x*y);
        sumX -= x;
        sumY -= y;
        sumX2 -= (x*x);
    }

    public DataSheet(){
        sumX = 0;
        sumY = 0;
        sumX2 = 0;
        sumXY = 0;
        dataArray = new ArrayList();
    }

    public void calculateCoefficient(){
        int count = dataArray.size();
        k = ((sumXY - sumX*sumY / count) / (sumX2 - sumX*sumX / count));
        b = sumY / count - k*sumY/count;
    }

    public ArrayList <Data> getDataArray(){
        return dataArray;
    }

    public double getK(){
        return k;
    }

    public double getB(){
        return b;
    }

    public String toString(){
        return "k: " + k + "\t" + "b: " + b;
    }

    public int dataCount(){
        return dataArray.size();
    }

    public Data getDataItem(int index){
        return dataArray.get(index);
    }
}
