package io.github.mrbeepot.samachar.activities;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mrbeepot.samachar.Contingency;
import io.github.mrbeepot.samachar.R;
import io.github.mrbeepot.samachar.fragments.NewsCategoryFragment;
import io.github.mrbeepot.samachar.newsapi.ApiClient;
import io.github.mrbeepot.samachar.newsapi.ApiInterface;

public class ListOfNewsActivity extends AppCompatActivity {

    //Class Variables

    //Static or final

    private static final String TAG = "list of news activity";

    //Views

    @BindView(R.id.toolbar_list_of_news) Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    //Others

    private SectionPageAdapter sectionPageAdapter;
    private List<String> categories;
    private List<NewsCategoryFragment> fragments;

    //overridden methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_news);
        ButterKnife.bind(this);
        Log.d(TAG,"butter knife bind done");
        setToolbar();
        loadNews();
        setPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_of_news,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.refresh_icon_list_of_news: {
                refreshNews();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
        }
    }

    //Other methods

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    private void loadNews() {
        try {
            categories = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.api_news_categories)));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG,"news categories string array not found, Killing the app");
            Contingency.killTheApp(this);
        }
        fragments = new ArrayList<>();
        ApiInterface apiInterface = null;
        try {
            apiInterface = Objects.requireNonNull(ApiClient.getApiClient(this)).create(ApiInterface.class);
        } catch (NullPointerException e) {
            Log.e(TAG,"NPE while getting news api client.Killing the app");
            Contingency.killTheApp(this);
        }
        String apiKey = getResources().getString(R.string.NEWS_API_KEY);
        for(String s : categories) {
            NewsCategoryFragment temp = new NewsCategoryFragment();
            temp.setParameters(s,apiKey,apiInterface);
            Log.d(TAG,"setting parameters for : " + s);
            fragments.add(temp);
        }
    }

    private void setPager() {
        Log.d(TAG,"inside set pager");
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionPageAdapter);
        try {
            viewPager.setOffscreenPageLimit(categories.size());
        } catch (NullPointerException e) {
            Log.e(TAG,"NPE in setPager");
            Log.e(TAG,e.getMessage());
            Log.e(TAG,"killing the app");
            Contingency.killTheApp(this);
        }
        tabLayout.setupWithViewPager(viewPager);

    }

    private void refreshNews() {
        Toast.makeText(this,R.string.refreshing_news,Toast.LENGTH_SHORT).show();
        for(NewsCategoryFragment f : fragments) {
            f.loadNews();
        }
    }
    //Pager class for view pager

    private class SectionPageAdapter extends FragmentPagerAdapter {
        private static final String TAG = "Section page adapter";
        private FragmentManager fm;
        SectionPageAdapter(FragmentManager fm) {
            super(fm);
            Log.d(TAG,"constructor");
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG,"getting item position for : " + Integer.toString(position));
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            Log.d(TAG,"get count");
            return categories.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Log.d(TAG,"get page title");
            return getResources().getStringArray(R.array.news_categories)[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d(TAG,"instantiate item");
            Log.d(TAG,"position : " + Integer.toString(position));
            Fragment fragment = getItem(position);
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(container.getId(),fragment);
            transaction.commit();
            return fragment;
        }
    }
}
