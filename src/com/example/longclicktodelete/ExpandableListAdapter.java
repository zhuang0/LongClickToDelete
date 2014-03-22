package com.example.longclicktodelete;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements OnKeyListener {
	public static final int ItemHeight = 48;// item高度
	public static final int PaddingLeft = 36;// item左边距
	private List<String> headLines;
	private List<City> cities;
	private List<List<Car>> cars = new ArrayList<List<Car>>();
	private Context parentContext;
	private LayoutInflater layoutInflater;

	public ExpandableListAdapter(Context context, List<String> headLines, List<City> cities,
			List<List<Car>> cars) {
		this.parentContext = context;
		this.headLines = headLines;
		this.cities = cities;
		this.cars = cars;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void updateAdapter() {
		this.notifyDataSetChanged();

	}

	public Object getChild(int groupPosition, int childPosition) {
		if (groupPosition == 0) {
			return cities.get(groupPosition);
		} else {
			return cars.get(groupPosition - 1).get(childPosition);
		}

	}

	public int getChildrenCount(int groupPosition) {

		return 1;

	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public Object getGroup(int groupPosition) {
		return headLines.get(groupPosition);
	}

	public int getGroupCount() {
		return headLines.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	/**
	 * 自定义childView
	 */
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {

		View childView;
		ViewChildHolder vh = null;

		if (convertView == null) {
			childView = layoutInflater.inflate(R.layout.childview, null);
			vh = new ViewChildHolder(childView);
			childView.setTag(vh);
		} else {
			childView = convertView;
			vh = (ViewChildHolder) childView.getTag();
		}

		vh.gridview.setNumColumns(4);//设置列数
		vh.gridview.setGravity(Gravity.CENTER);// 居中
		vh.gridview.setHorizontalSpacing(10);// 列间距
		MyGridViewAdapter gridViewAdapter = new MyGridViewAdapter(parentContext, groupPosition);
		vh.gridview.setAdapter(gridViewAdapter);

		return childView;
	}

	class ViewChildHolder {

		MyGridView gridview;

		public ViewChildHolder(View view) {
			gridview = (MyGridView) view.findViewById(R.id.gridview);
		}

	}

	/**
	 * 自定义groupView
	 */
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
			ViewGroup parent) {

		View groupView;
		ViewGroupHolder vh = null;

		if (convertView == null) {
			groupView = layoutInflater.inflate(R.layout.group_bar, parent, false);
			vh = new ViewGroupHolder(groupView);
			groupView.setTag(vh);
		} else {
			groupView = convertView;
			vh = (ViewGroupHolder) groupView.getTag();
		}
		vh.title.setText(headLines.get(groupPosition));

		return groupView;
	}

	class ViewGroupHolder {
		TextView title = null;

		public ViewGroupHolder(View view) {
			title = (TextView) view.findViewById(R.id.group_title);

		}
	}

	public class MyGridViewAdapter extends BaseAdapter {

		private Context mContext;
		private int inParentPsition;

		public MyGridViewAdapter(Context Context, int inParentPsition) {
			this.mContext = Context;
			this.inParentPsition = inParentPsition;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (inParentPsition == 0) {
				return cities.size();
			} else {
				return cars.get(inParentPsition - 1).size();
			}

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			if (inParentPsition == 0) {
				return cities.get(position);
			} else {
				return cars.get(inParentPsition - 1).get(position);
			}

		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			View view;
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = layoutInflater.inflate(R.layout.grid_item, null);
				vh = new ViewHolder(view);
				view.setTag(vh);
			} else {
				view = convertView;
				vh = (ViewHolder) view.getTag();
			}

			if (inParentPsition == 0) {

				vh.textView.setText(cities.get(position).name);
				if (cities.get(position).isEditing) {
					vh.textView.setBackgroundColor(mContext.getResources().getColor(
							R.color.lightblue));
				} else {
					vh.textView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
				}
				vh.textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cities.get(position).isEditing) {

							cities.remove(position);
							Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();

						} else {

							Toast.makeText(
									mContext,
									"click " + String.valueOf(inParentPsition) + "-"
											+ String.valueOf(position), Toast.LENGTH_SHORT).show();
						}

						updateAdapter();//更新界面

					}
				});

			} else {

				vh.textView.setText(cars.get(inParentPsition - 1).get(position).name);
				if (cars.get(inParentPsition - 1).get(position).isEditing) {
					vh.textView.setBackgroundColor(mContext.getResources().getColor(
							R.color.lightblue));
				} else {
					vh.textView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
				}
				vh.textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cars.get(inParentPsition - 1).get(position).isEditing) {

							cars.get(inParentPsition - 1).remove(position);
							Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();

						} else {

							Toast.makeText(
									mContext,
									"click " + String.valueOf(inParentPsition) + "-"
											+ String.valueOf(position), Toast.LENGTH_SHORT).show();
						}

						updateAdapter();//更新界面

					}
				});
			}

			vh.textView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub

					for (int i = 0; i < cities.size(); i++) {
						if (cities.get(i).isEditing) {

						} else {
							cities.get(i).isEditing = true;
						}
					}
					for (int i = 0; i < cars.size(); i++) {

						for (int j = 0; j < cars.get(i).size(); j++) {

							if (cars.get(i).get(j).isEditing) {
							} else {
								cars.get(i).get(j).isEditing = true;
							}

						}

					}

					updateAdapter();//更新界面

					return true;
				}
			});

			return view;
		}

		class ViewHolder {

			TextView textView;

			public ViewHolder(View view) {
				textView = (TextView) view.findViewById(R.id.item_text);
			}

		}

	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for (int i = 0; i < cities.size(); i++) {
				if (cities.get(i).isEditing) {
					cities.get(i).isEditing = false;
				} else {

				}
			}
			for (int i = 0; i < cars.size(); i++) {

				for (int j = 0; j < cars.get(i).size(); j++) {

					if (cars.get(i).get(j).isEditing) {

						cars.get(i).get(j).isEditing = false;
					} else {

					}

				}

			}

			updateAdapter();//更新界面

			return true;
		}
		return false;
	}

}