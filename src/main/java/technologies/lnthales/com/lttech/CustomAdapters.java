
package technologies.lnthales.com.lttech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CustomAdapters extends BaseAdapter {

    Context c;
    ArrayList<StudentInfo> studentInfos;


    LayoutInflater inflater;

    public CustomAdapters(Context c, ArrayList<StudentInfo> studentInfos) {
        this.c = c;
        this.studentInfos = studentInfos;
    }

    @Override
    public int getCount() {
        return studentInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return studentInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_item,parent,false);
        }


        TextView nameTxt= (TextView) convertView.findViewById(R.id.email);
        TextView nameTxt2= (TextView) convertView.findViewById(R.id.mobile);
        TextView nameTxt3= (TextView) convertView.findViewById(R.id.email2);
        TextView nameTxt4= (TextView) convertView.findViewById(R.id.mobile2);



             nameTxt.setText(studentInfos.get(position).getPs());
             nameTxt2.setText(studentInfos.get(position).getName());
             nameTxt3.setText(studentInfos.get(position).getEmail());
             nameTxt4.setText(studentInfos.get(position).getPh());



        final int pos=position;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,studentInfos.get(pos).getName(),Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}