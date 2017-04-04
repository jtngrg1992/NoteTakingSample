package com.example.jatingarg.notes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdaper.NotesAdapterEventListener {
    private RecyclerView mRecycler;
    private List<Note> mNotes;
    private static final String TAG = "MainActivity";
    private NotesAdaper mAdapter;
    private final String NOTES_BUNDLE = "notes_bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycler = (RecyclerView) findViewById(R.id.mainRecycler);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);

        mNotes = new ArrayList<>();
        mNotes.add(new Note("1", "This is a test note", "listview_feature"));

        mAdapter = new NotesAdaper(mNotes, this, this);
        mRecycler.setAdapter(mAdapter);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void rowTapped(final int position) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.new_note_dialog, null);
        TextView dialogTitle = (TextView) dialogView.findViewById(R.id.noteDialogTitle);
        final EditText noteContent = (EditText) dialogView.findViewById(R.id.noteContent);

        noteContent.setText(mNotes.get(position).getContent());
        dialogTitle.setText("Edit note");

        alertBuilder.setView(dialogView);
        alertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mNotes.get(position).setContent(noteContent.getText().toString());
                mAdapter.notifyItemChanged(position);

            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addNote) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.new_note_dialog, null);
            final EditText noteContent = (EditText) dialogView.findViewById(R.id.noteContent);
            alertBuilder.setView(dialogView);
            alertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (noteContent.getText().length() > 0) {
                        mNotes.add(new Note("1", noteContent.getText().toString(), "listview_feature"));
                        mAdapter.notifyDataSetChanged();

                    }
                }
            });
            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = alertBuilder.create();
            dialog.show();
            return true;
        }
        return false;
    }
}
