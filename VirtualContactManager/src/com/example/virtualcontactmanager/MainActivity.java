package com.example.virtualcontactmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author optimus158
 * 
 *         This class setup the tabs in action bar and functionalities
 */
public class MainActivity extends ActionBarActivity implements TabListener {

	private ActionBar actioBar;
	private ViewPager viewPager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupUI();
	}

	/**
	 * Method for setting up the UI components
	 */
	private void setupUI() {

		actioBar = getSupportActionBar();
		actioBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int arg0) {
				// syncs the actionBar with ViewPager
				actioBar.setSelectedNavigationItem(arg0);
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		Tab tab1 = actioBar.newTab();
		tab1.setText("Contact(s)");
		tab1.setTabListener(this);

		Tab tab2 = actioBar.newTab();
		tab2.setText("Group(s)");
		tab2.setTabListener(this);

		actioBar.addTab(tab1);
		actioBar.addTab(tab2);
	}

	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	}

	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// syncs viewpager with tabs
		viewPager.setCurrentItem(arg0.getPosition());

	}

	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}

	/**
	 * @author optimus158
	 * 
	 *         Adapter class for ViewPager
	 */
	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			if (arg0 == 0) {
				fragment = new Contacts();
			}
			if (arg0 == 1) {
				fragment = new Groups();
			}
			return fragment;
		}

		public int getCount() {
			return 2;
		}

	}
	
	/**
	 * Takes the user to launcher when pressed back from Main Screen
	 */
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
