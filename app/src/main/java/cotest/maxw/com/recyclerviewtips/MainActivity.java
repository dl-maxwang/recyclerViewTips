package cotest.maxw.com.recyclerviewtips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView rv = findViewById(R.id.rv);
        List<String> data = initTestData();
        rv.setAdapter(new MyAdapter(data));

        // here is where magic happen
        rv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {

            GestureDetector gd = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
                int getItemPosition(MotionEvent e) {
                    View childViewUnder = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childViewUnder == null) {
                        return -1;
                    }
                    return rv.getChildAdapterPosition(childViewUnder);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    long startTime = System.currentTimeMillis();
                    int childAdapterPosition = getItemPosition(e);
                    long totalTime = System.currentTimeMillis() - startTime;
                    Toast.makeText(MainActivity.this, totalTime + " onLongPress " + childAdapterPosition, Toast.LENGTH_SHORT).show();
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    long startTime = System.currentTimeMillis();
                    int childAdapterPosition = getItemPosition(e);
                    long totalTime = System.currentTimeMillis() - startTime;
                    Toast.makeText(MainActivity.this, totalTime + " onDoubleTap " + childAdapterPosition, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    long startTime = System.currentTimeMillis();
                    int childAdapterPosition = getItemPosition(e);
                    long totalTime = System.currentTimeMillis() - startTime;
                    Toast.makeText(MainActivity.this, totalTime + " onSingleTapConfirmed " + childAdapterPosition, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return gd.onTouchEvent(e);
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                gd.onTouchEvent(e);
            }
        });

    }

    /**
     * feed some fake data
     *
     * @return
     */
    private List<String> initTestData() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            result.add("item " + i);
        }
        return result;
    }


    static class MyAdapter extends RecyclerView.Adapter<MyViewholder> {

        List<String> data;

        public MyAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tvRoot = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_text_item, parent, false);
            return new MyViewholder(tvRoot);
        }

        @Override
        public void onBindViewHolder(MyViewholder holder, int position) {
            String str = data.get(position);
            holder.tvItem.setText(str);
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }

    static class MyViewholder extends RecyclerView.ViewHolder {

        public TextView tvItem;

        public MyViewholder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView;
        }
    }


}
