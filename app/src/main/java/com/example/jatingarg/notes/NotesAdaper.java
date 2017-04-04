package com.example.jatingarg.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jatingarg on 04/04/17.
 */

public class NotesAdaper extends RecyclerView.Adapter<NotesAdaper.ViewHolder> {

    private List<Note> mNotes;
    private Context mContext;
    private static final String TAG = "NotesAdaper";

    public interface NotesAdapterEventListener {
        void rowTapped(int position);
    }

    private NotesAdapterEventListener mListerner;

    public NotesAdaper(List<Note> mNotes, Context context, NotesAdapterEventListener listener) {
        this.mNotes = mNotes;
        this.mContext = context;
        this.mListerner = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.noteText.setText(note.getContent());
        holder.noteImage.setImageResource(note.getImageResourceID(mContext));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView noteImage;
        private TextView noteText;
        private LinearLayout rowHolder;

        public ViewHolder(View itemView) {
            super(itemView);
            noteImage = (ImageView) itemView.findViewById(R.id.noteImage);
            noteText = (TextView) itemView.findViewById(R.id.noteText);
            rowHolder = (LinearLayout) itemView.findViewById(R.id.rowHolder);

            rowHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListerner.rowTapped(getPosition());
                }
            });
        }
    }
}
