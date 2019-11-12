package tw.org.iii.iiiand05;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;
    private LinkedList<HashMap<String,String>> data;
    private String[] from ={"title","mesg"};
    private int[] to ={ R.id.item_title, R.id.item_mesg}; //to的data type是整數, 利用id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        initListView();
    }

    private void initListView() {
        //TODO initListView
        data = new LinkedList<>();

        for( int i=0; i<20; i++){
            HashMap<String,String> dd = new HashMap<>();
            int rand = (int)(Math.random()*49+1);
            dd.put(from[0],"Title "+ rand); //key, value
            dd.put(from[1],"Content: "+ rand);
            dd.put("detail","Detail: "+ rand);//有些沒有用到沒關係
            data.add(dd);
        }//資料一次性的輸入; 可能sql撈出資料,或者雲端data

        adapter = new SimpleAdapter( this, data,
                                    R.layout.item_test1,
                                    from, to);
        listView.setAdapter(adapter);

        //listView不適用onClick,很怪; 因list 有item, 關心的是item有無被點按
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //只在乎第幾個位置
                Log.v("brad", "pos: " + position);
                //點入看詳細資料,不建議再寫進來override, 寫function
                StringBuffer sb = new StringBuffer();
                sb.append("Title: " + data.get(position).get("title") + "\n");
                sb.append("Mesg: " + data.get(position).get("mesg") + "\n");
                sb.append("Detail: " + data.get(position).get("detail") + "\n");
                displayDetial(sb.toString());

            }
        });
    }
    private void displayDetial(String mesg){
        new AlertDialog.Builder(this)
                .setMessage(mesg)
                .create()
                .show();
    }

    public void addItem(View view) {
        HashMap<String,String> dd = new HashMap<>();
        int rand = (int)(Math.random()*49+100);
        dd.put(from[0],"Title "+ rand); //key, value
        dd.put(from[1],"Content: "+ rand);
        dd.put("detail","Detail: "+ rand);//有些data沒有用到沒關係
        data.add(dd);
        adapter.notifyDataSetChanged();
        /*
        adapter發現資料有變動,立即通知listView
        若沒有加,會出現error:The content of the adapter has changed but ListView did not receive a notification.
        */


    }
}
