package com.example.simplenotes;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.simplenotes.MainActivity.mNoteViewModel;


public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    public static final String EXTRA_UPDATE_TITLE = "TITLE";
    public static final String EXTRA_UPDATE_DESC = "DESC";
    public static final String EXTRA_UPDATE_ID = "id";
    public static final String EXTRA_UPDATE_DEDLINE = "DEDLINE";
    public static final String EXTRA_UPDATE_CHECKBOX = "CHECKBOX";
    public static boolean checkSaveUpdate = false;


    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView titleItemView;
        private final TextView descItemView;
        private final TextView dateItemView;
        private final CardView cardView;
       //private WordViewModel mWordViewModel;

        private NoteViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.textViewTitle);
            descItemView = itemView.findViewById(R.id.textViewDesc);
            dateItemView= itemView.findViewById(R.id.date);
            cardView=itemView.findViewById(R.id.cardView);

//            titleItemView.setOnClickListener(this);
//            descItemView.setOnClickListener(this);
//            dateItemView.setOnClickListener(this);
//            titleItemView.setOnLongClickListener(this);
//            descItemView.setOnLongClickListener(this);
//            dateItemView.setOnLongClickListener(this);
            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);

        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), NewNoteActivity.class);
            Note noteSelect = mNotes.get(getAdapterPosition());
            String title = noteSelect.getTitle();
            String desc = noteSelect.getDesc();
            int id= noteSelect.getId();
            long millisecods= noteSelect.getDeadline();
            //String dedline=getDate(millisecods);
            boolean checkBox=noteSelect.isCheckDead();

            checkSaveUpdate = true;
            intent.putExtra(EXTRA_UPDATE_TITLE, title);
            intent.putExtra(EXTRA_UPDATE_DESC, desc);
            intent.putExtra(EXTRA_UPDATE_ID, id);
            intent.putExtra(EXTRA_UPDATE_DEDLINE, millisecods);
            intent.putExtra(EXTRA_UPDATE_CHECKBOX, checkBox);

            //Toast.makeText(v.getContext(),"OnClick"+id,Toast.LENGTH_LONG).show();
            v.getContext().startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {
            mNoteViewModel.delete(mNotes.get(getAdapterPosition()));
            Toast.makeText(v.getContext(),"Delete Note",Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
            return false;
        }
    }

    private final LayoutInflater mInflater;
    private List<Note> mNotes; // Cached copy of notes

    NoteListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (mNotes != null) {
            Note current = mNotes.get(position);
            holder.titleItemView.setText(current.getTitle());
            holder.descItemView.setText(current.getDesc());
            long dateDedline= current.getDeadline();
            long currentDate=System.currentTimeMillis();
            //Resources res = getResources();
            //int redColorValue = ContextCompat.getColor(, R.color.colorPrimary);
            //int greyColorValue= Color.parseColor("@color/cardView");
            if((currentDate-dateDedline)>0 && dateDedline>0) {
                holder.cardView.setBackgroundResource(R.color.colorAccent);

            }else if(((currentDate+43200000)-dateDedline)>0 && dateDedline>0){
                holder.cardView.setBackgroundResource(R.color.dedlineCurrent);
            }else {
                holder.cardView.setBackgroundResource(R.color.cardView);
            }
            if(dateDedline==0)holder.dateItemView.setMaxHeight(0);
            else {
                holder.dateItemView.setMaxHeight(100);
                holder.dateItemView.setText(getDate(dateDedline));
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.titleItemView.setText("No Title");
            holder.descItemView.setText("No Desk");
        }

    }

    void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mNotes has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    private String getDate(long millisecods) {
        String textDeadline= DateUtils.formatDateTime(null, millisecods,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
        return textDeadline;

    }


}


