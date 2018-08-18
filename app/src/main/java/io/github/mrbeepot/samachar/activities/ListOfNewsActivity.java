package io.github.mrbeepot.samachar.activities;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mrbeepot.samachar.R;
import io.github.mrbeepot.samachar.fragments.NewsCategoryFragment;
import io.github.mrbeepot.samachar.newsapi.ApiClient;
import io.github.mrbeepot.samachar.newsapi.ApiInterface;

public class ListOfNewsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_list_of_news) Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    private SectionPageAdapter sectionPageAdapter;
    private List<String> categories;
    private List<NewsCategoryFragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_news);
        ButterKnife.bind(this);
        setToolbar();
        loadNews();
        setPager();
    }

    private void setPager() {
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionPageAdapter);
        viewPager.setOffscreenPageLimit(categories.size());
        tabLayout.setupWithViewPager(viewPager);

    }

    private void loadNews() {
        categories = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.api_news_categories)));
        fragments = new ArrayList<NewsCategoryFragment>();
        ApiInterface apiInterface = ApiClient.getApiClient(this).create(ApiInterface.class);
        String apiKey = getResources().getString(R.string.NEWS_API_KEY);
        for(String s : categories) {
            NewsCategoryFragment temp = new NewsCategoryFragment();
            temp.setParameters(s,apiKey,apiInterface);
            fragments.add(temp);
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

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


    private class SectionPageAdapter extends FragmentPagerAdapter {
        private FragmentManager fm;
        public SectionPageAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.news_categories)[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = getItem(position);
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(container.getId(),fragment);
            transaction.commit();
            return fragment;

        }
    }



    private void refreshNews() {
        Toast.makeText(this,R.string.refreshing_news,Toast.LENGTH_SHORT).show();
        for(NewsCategoryFragment f : fragments) {
            f.loadNews();
        }
    }
}
