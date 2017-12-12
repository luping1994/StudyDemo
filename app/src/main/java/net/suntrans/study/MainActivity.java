package net.suntrans.study;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.suntrans.study.data.PlanData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean scale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlanView2 view2 = findViewById(R.id.view2);
        List<PlanData> datas = new ArrayList<>();
        for (int i=10;i<1080;i+=200){
            PlanData data = new PlanData();
            data.x=i;
            data.y=i;
            data.width=70;
            data.height=70;
            data.resID = R.drawable.ic_payed;
            datas.add(data);

        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        view2.setContentBitmap(bitmap);
        view2.setmDatas(datas);
        view2.setElementsClickListener(new PlanView2.onElementsClickListener() {
            @Override
            public void onElementClick(PlanData data) {
                Toast.makeText(MainActivity.this.getApplicationContext(),data.x+","+data.y,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
