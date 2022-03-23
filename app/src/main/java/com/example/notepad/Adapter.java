package com.example.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.zip.DataFormatException;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    Context context;
    RealmResults<Note> notesList;

    public Adapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Note note=notesList.get(position);
    holder.titleOutput.setText(note.getTitle());
        holder.descOutput.setText(note.getDescription());
        String formatedTime= DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.timeOutput.setText(formatedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu=new PopupMenu(context,view);
               menu.getMenu().add("DELETE");
               menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem menuItem) {
                       if(menuItem.getTitle().equals("DELETE"))
                       {
                           Realm realm=Realm.getDefaultInstance();
                           realm.beginTransaction();
                           note.deleteFromRealm();
                           realm.commitTransaction();
                           Toast.makeText(context, "Note Deleted", Toast.LENGTH_LONG).show();
                       }
                       return true;
                   }
               });
               menu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView titleOutput;
        TextView descOutput;
        TextView timeOutput;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput=itemView.findViewById(R.id.titleoutput);
            descOutput=itemView.findViewById(R.id.descoutput);
            timeOutput=itemView.findViewById(R.id.timeoutput);

        }
    }
}
