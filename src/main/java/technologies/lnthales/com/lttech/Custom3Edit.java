package technologies.lnthales.com.lttech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class Custom3Edit extends BaseAdapter {

    Context context;
    List<StudentInfo> list;



    public Custom3Edit(MainActivity editActivity, List<StudentInfo> list) {
        this.context = editActivity;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        TextView tv1=convertView.findViewById(R.id.email);
        TextView tv2=convertView.findViewById(R.id.mobile);
        TextView tv3=convertView.findViewById(R.id.email2);
        TextView tv4=convertView.findViewById(R.id.mobile2);


        final StudentInfo studentInfo= list.get(position);
        tv1.setText("PS :"+" "+studentInfo.getPs());
        tv2.setText("name :"+" "+studentInfo.getName());
        tv3.setText("Email :"+" "+studentInfo.getEmail());
        tv4.setText("Contact :"+" "+studentInfo.getPh());

        return convertView;
    }
}
