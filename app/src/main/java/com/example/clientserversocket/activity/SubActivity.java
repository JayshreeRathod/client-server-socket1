package com.example.clientserversocket.activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.clientserversocket.R;
import com.example.clientserversocket.client.ClientConnection;
import com.example.clientserversocket.common.Constants;
import com.example.clientserversocket.server.ServerConnection;

public class SubActivity extends AppCompatActivity {

    private ViewPager _viewPager = null;
    private ClientConnection _clientConnection = null;
    private ServerConnection _serverConnection = null;

    int[] mResources = {
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fourth,
            R.drawable.fifth,
            R.drawable.sixth
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        _viewPager = (ViewPager) findViewById(R.id.view_pager_id);
        _viewPager.setAdapter(new CustomPagerAdapter(this));
        _viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(Constants.APP_TAG, "Page changed :: " + position);
                if(_serverConnection != null) {
                    _serverConnection.broadcastMessage(""+position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        String mode = getIntent().getExtras().getString(Constants.KEY_APPLICATION_MODE);
        if(mode.equals(Constants.USER_MODE)) {
            _clientConnection = new ClientConnection(this);
            _clientConnection.start();
        }
        else {
            _serverConnection  = new ServerConnection(this);
            setTitle("Host : " + _serverConnection.getIpAddress());
            _serverConnection.start();
        }
    }

    private class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public void scrollPage(int pos) {
        if(_viewPager != null) {
            _viewPager.setCurrentItem(pos, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(_clientConnection != null) {
            _clientConnection.stop();
            _clientConnection = null;
        }

        if(_serverConnection != null) {
            _serverConnection.stop();
            _serverConnection = null;
        }
    }
}
