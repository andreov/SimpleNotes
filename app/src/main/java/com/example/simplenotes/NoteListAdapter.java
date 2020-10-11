package com.example.simplenotes;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.example.simplenotes.MainActivity.mNoteViewModel;


public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    public static final String EXTRA_UPDATE_TITLE = "TITLE";
    public static final String EXTRA_UPDATE_DESC = "DESC";
    public static final String EXTRA_UPDATE_ID = "id";
    public static final String EXTRA_UPDATE_DEDLINE = "DEDLINE";
    public static final String EXTRA_UPDATE_CHECKBOX = "CHECKBOX";
    public static boolean checkSaveUpdate = false;
    private final LayoutInflater mInflater;
    private List<Note> mNotes;

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView titleItemView;
        private final TextView descItemView;
        private final TextView dateItemView;
        private final CardView cardView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.textViewTitle);
            descItemView = itemView.findViewById(R.id.textViewDesc);
            dateItemView= itemView.findViewById(R.id.date);
            cardView=itemView.findViewById(R.id.cardView);
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
            boolean checkBox=noteSelect.isCheckDead();

            checkSaveUpdate = true;
            intent.putExtra(EXTRA_UPDATE_TITLE, title);
            intent.putExtra(EXTRA_UPDATE_DESC, desc);
            intent.putExtra(EXTRA_UPDATE_ID, id);
            intent.putExtra(EXTRA_UPDATE_DEDLINE, millisecods);
            intent.putExtra(EXTRA_UPDATE_CHECKBOX, checkBox);
            v.getContext().startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
            alertDialogBuilder.setTitle("Удаление заметки");
            alertDialogBuilder
                    .setMessage("Вы хотите удалить заметку?")
                    .setCancelable(true)
                    .setPositiveButton("Да",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    mNoteViewModel.delete(mNotes.get(getAdapterPosition()));
                                    Toast.makeText(v.getContext(), "Заметка удалена", Toast.LENGTH_LONG).show();
                                }
                            })

                    .setNegativeButton("Нет",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    Toast.makeText(v.getContext(), "Отмена удаления", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            notifyDataSetChanged();
            return false;
        }
    }

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
            //long currentDate= System.currentTimeMillis();
            //TimeZone timeZone= TimeZone.getDefault();
            Calendar calendar=Calendar.getInstance();
            long currentDate= calendar.getTimeInMillis();
            final int DAY=1000*60*60*24;
            if((currentDate/DAY)>(dateDedline/DAY)) {
                holder.dateItemView.setBackgroundResource(R.color.dedlineDown);
            }else if((currentDate/DAY)==(dateDedline/DAY)){
                holder.dateItemView.setBackgroundResource(R.color.dedlineCurrent);
            }else {
                holder.dateItemView.setBackgroundResource(R.color.cardView);
            }
            if(dateDedline==0)holder.dateItemView.setMaxHeight(0);
            else {
                holder.dateItemView.setMaxHeight(100);
                holder.dateItemView.setText(getDate(dateDedline));
            }
        } else {
            holder.titleItemView.setText("Нет заголовка заметки");
            holder.descItemView.setText("Нет тела заметки");
        }
    }

    public void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

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


