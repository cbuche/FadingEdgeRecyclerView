package testing.fadingedge;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.recycler_view) RecyclerView mRecyclerView;

    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mAdapter = new Adapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        List<String> items = makeData(50);
        mAdapter.updateItems(items);
    }

    private List<String> makeData(int numItems) {
        if (numItems <= 0) return Collections.emptyList();

        List<String> data = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            int index = i + 1;
            data.add("test item " + index);
        }

        return data;
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater mInflater;
        private List<String> mItems;

        public Adapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mItems = new ArrayList<>();
        }

        public void updateItems(List<String> items) {
            mItems.clear();
            if (items == null) return;

            mItems.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String data = mItems.get(position);

            holder.mTextView.setText(data);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
