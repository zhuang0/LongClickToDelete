package com.example.longclicktodelete;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {

	ExpandableListView mExpandableListView;

	ExpandableListAdapter mExpandableListAdapter;
	private List<String> groups = new ArrayList<String>();
	private List<City> cities = new ArrayList<City>();
	private List<List<Car>> cars = new ArrayList<List<Car>>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		groups.add("订阅地区");
		groups.add("订阅品牌A");
		groups.add("订阅品牌B");
		for (int i = 0; i < 3; i++) {

			String Name = groups.get(i).toString();

			if (i == 0) {

				for (int j = 0; j < 10; j++) {
					City city = new City();
					city.name = Name + "-city" + String.valueOf(j);
					cities.add(city);

				}

			} else {

				List<Car> list = new ArrayList<Car>();
				for (int j = 0; j < 10; j++) {
					Car car = new Car();
					car.name = Name + "-car" + String.valueOf(j);
					list.add(car);

				}
				cars.add(list);
			}

		}

		mExpandableListView = (ExpandableListView) this.findViewById(R.id.expandableListView);
		mExpandableListAdapter = new ExpandableListAdapter(this, groups, cities, cars);
		mExpandableListView.setAdapter(mExpandableListAdapter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return mExpandableListAdapter.onKey(null, keyCode, event);
	}

}
