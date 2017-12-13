package net.suntrans.study;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import net.suntrans.study.data.Data;
import net.suntrans.study.data.PlanData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean scale = false;
    private CoordinateLayout view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view2 = findViewById(R.id.view2);
        view2.setElementsClickListener(new CoordinateLayout.onElementsClickListener() {
            @Override
            public void onElementClick(Data.ElementsBean data) {
                Toast.makeText(MainActivity.this.getApplicationContext(),data.title,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Data data = new Data();
        Data.ContainerBean containerBean = new Data.ContainerBean();
        containerBean.bgColor="#ffffff";
        containerBean.bgImage ="http://tit.suntrans-cloud.com//app/building/floor_1.png";
        containerBean.height = 1920;
        containerBean.width = 1080;
        containerBean.title="test";
        data.container = containerBean;
        List<Data.ElementsBean> lists = new ArrayList<>();
        for (int i=0;i<13;i++){
            Data.ElementsBean elementsBean = new Data.ElementsBean();
            elementsBean.status = i%2==0?true:false;
            elementsBean.width=46;
            elementsBean.height =46;
            elementsBean.title ="第"+i+"个";
            elementsBean.openUrl="http://tit.suntrans-cloud.com//app/building/light_4_on.png";
            elementsBean.closeUrl = "http://tit.suntrans-cloud.com//app/building/light_4_off.png";
            elementsBean.x = 50*i;
            elementsBean.y = 50*i;
            lists.add(elementsBean);
        }
        data.elements = lists;
        data.result = true;
        view2.setDatas(JSON.toJSONString(data));
    }

    public void buttonOnclick(View view) {
        Data data = new Data();
        Data.ContainerBean containerBean = new Data.ContainerBean();
        containerBean.bgColor="#ffffff";
        containerBean.bgImage ="http://tit.suntrans-cloud.com//app/building/floor_1.png";
        containerBean.height = 1920;
        containerBean.width = 1080;
        containerBean.title="test";
        data.container = containerBean;
        List<Data.ElementsBean> lists = new ArrayList<>();
        for (int i=0;i<20;i++){
            Data.ElementsBean elementsBean = new Data.ElementsBean();
            elementsBean.status = i%2==0?true:false;
            elementsBean.width=46;
            elementsBean.height =46;
            elementsBean.title ="第"+i+"个";
            elementsBean.openUrl="http://tit.suntrans-cloud.com//app/building/light_4_on.png";
            elementsBean.closeUrl = "http://tit.suntrans-cloud.com//app/building/light_4_off.png";
            elementsBean.x = 150*i;
            elementsBean.y = 150*i;
            lists.add(elementsBean);
        }
        data.elements = lists;
        data.result = true;
        view2.setDatas(JSON.toJSONString(data));

        view2.setElementsClickListener(new CoordinateLayout.onElementsClickListener() {
            @Override
            public void onElementClick(Data.ElementsBean data) {
                Toast.makeText(MainActivity.this.getApplicationContext(),data.title,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buttonOnclick2(View view) {

    }
}
