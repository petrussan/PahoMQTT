package id.ac.petra.pahomqtt;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class ChartHelper implements OnChartValueSelectedListener {
    private LineChart mChart;

    public ChartHelper(LineChart chart) {
        mChart = chart;
        mChart.setOnChartValueSelectedListener(this);
        mChart.setTouchEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setBackgroundColor(Color.CYAN);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
    }

    public void setmChart(LineChart mChart) {
        this.mChart = mChart;
    }

    public void addEntry(float value) {
        LineData data = mChart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(),value),0);
            data.notifyDataChanged();
            mChart.notifyDataSetChanged();
        }
    }

    private ILineDataSet createSet() {
        LineDataSet set = new LineDataSet(null,"Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.rgb(0,0,200));
        set.setLineWidth(2f);
        return set;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
